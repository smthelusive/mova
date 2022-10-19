package smthelusive.mova;

import org.objectweb.asm.*;
import smthelusive.mova.domain.*;
import smthelusive.mova.gen.MovaParser;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class ByteCodeGenerator {

    private final static String ONE = "1";
    private final static String ZERO = "0";
    private final static Stack<MovaType> typeStack = new Stack<>();
    private final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    private int variableIndex = 1; // inside main method 0 is taken by String[]
    private MethodVisitor methodVisitor;
    private final Map<String, RegistryVariable> byteCodeVariableRegistry = new HashMap<>();

    /**
     * start the class definition with the given class name
     * start the main method
     * @param name the name of the program
     */
    public void init(String name) {
        classWriter.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, name, null,
                "java/lang/Object", null);
        methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                "main", "([" + Type.getType(String.class).getDescriptor() + ")V",
                null, null);
        methodVisitor.visitCode();
    }

    public void convertToDecimal(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.STRING.equals(fromType) && !MovaType.INTEGER.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();
        switch (fromType) {
            case INTEGER -> {
                methodVisitor.visitInsn(Opcodes.I2D);
                changeLastTypeOnLocalStackTo(MovaType.DECIMAL);
            }
            case STRING -> {
                methodVisitor.visitLdcInsn(",");
                methodVisitor.visitLdcInsn(".");
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/String",
                        "replace",
                        "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;",
                        false);
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double",
                        "parseDouble", "(" + Type.getType(String.class) + ")" +
                                Type.DOUBLE_TYPE.getDescriptor(), false);
                changeLastTypeOnLocalStackTo(MovaType.DECIMAL);
            }
        }
        // swap back in the original order:
        if (left) swap();
    }

    private void popNValuesFromLocalStack(int n) {
        for (int i = 0; i < n; i++) {
            typeStack.pop();
        }
    }

    /***
     * converts last or before last value at the top
     * of operand stack both in bytecode and locally
     * @param left false if the last value should be converted
     */
    public void convertToInteger(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.DECIMAL.equals(fromType) && !MovaType.STRING.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();

        // if it's a string, it's safer and easier to first convert it to decimal
        // before converting to integer
        if (MovaType.STRING.equals(fromType)) {
            convertToDecimal(false);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                "ceil", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                        Type.DOUBLE_TYPE.getDescriptor(), false);
        methodVisitor.visitInsn(Opcodes.D2I);
        changeLastTypeOnLocalStackTo(MovaType.INTEGER);
        // swap to be able to convert the element before the last one
        if (left) swap();
    }

    /***
     * only used internally for processing conditions
     */
    private void signum() {
        if (!MovaType.DECIMAL.equals(typeStack.lastElement())) return;
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                "signum", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                        Type.DOUBLE_TYPE.getDescriptor(), false);
        methodVisitor.visitInsn(Opcodes.D2I);
        changeLastTypeOnLocalStackTo(MovaType.INTEGER);
    }

    public void convertToString(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.INTEGER.equals(fromType) && !MovaType.DECIMAL.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String",
                "valueOf", "(" + (MovaType.INTEGER.equals(fromType) ?
                                Type.INT_TYPE.getDescriptor() :
                                Type.DOUBLE_TYPE.getDescriptor()) + ")" +
                        Type.getDescriptor(String.class), false);
        changeLastTypeOnLocalStackTo(MovaType.STRING);
        // swap to be able to convert the element before the last one
        if (left) swap();
    }

    private MovaType getLeftElementOfStack() {
        return typeStack.size() > 1 ? typeStack.get(typeStack.size() - 2) : null;
    }

    private boolean someOperandIsString() {
        return MovaType.STRING.equals(getLeftElementOfStack()) ||
                MovaType.STRING.equals(typeStack.lastElement());
    }

    private String getLastTypeDescriptor() {
        return Optional.ofNullable(typeStack.lastElement()).map(context -> switch (context) {
                case INTEGER -> Type.INT_TYPE.getDescriptor();
                case DECIMAL -> Type.DOUBLE_TYPE.getDescriptor();
                default -> Type.getType(String.class).getDescriptor();
            }
        ).orElse(Type.getType(String.class).getDescriptor());
    }

    /***
     * - put variable with index 0 on top of stack (String[])
     * - put index value on top of stack
     * - AALOAD: load element of array, array reference and index are last elements on top of opstack
     * - currently there is a single string reference on top of stack,
     *      so update local stack to reflect that
     */
    public void loadArgumentByIndex(int argIndex) {
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitLdcInsn(argIndex);
        methodVisitor.visitInsn(Opcodes.AALOAD);
        typeStack.add(MovaType.STRING);
    }

    private void changeLastTypeOnLocalStackTo(MovaType type) {
        typeStack.pop();
        typeStack.add(type);
    }

    /***
     * Saves the value from the opstack to local variable
     * @param identifier of a variable stored in the compiler's registry
     */
    public void addVariableAssignment(String identifier) {
        int opcode = switch (typeStack.lastElement()) {
            case DECIMAL -> Opcodes.DSTORE;
            case INTEGER -> Opcodes.ISTORE;
            default -> Opcodes.ASTORE;
        };
        Optional<RegistryVariable> existingVariableOption =
                Optional.ofNullable(byteCodeVariableRegistry.get(identifier));
        existingVariableOption.ifPresentOrElse(
                existingVariable -> overrideExistingVariable(opcode, identifier, existingVariable),
                () -> storeVariableThatDoesNotExist(opcode, identifier)
        );
        typeStack.pop();
    }

    private void overrideExistingVariable(int opcode, String identifier, RegistryVariable existingVariable) {
        methodVisitor.visitVarInsn(opcode, existingVariable.id());
        // if the type has changed, we should update it in the registry:
        if (!typeStack.lastElement().equals(existingVariable.type())) {
            byteCodeVariableRegistry.replace(identifier, existingVariable,
                    new RegistryVariable(existingVariable.id(), typeStack.lastElement()));
        }
    }

    private void storeVariableThatDoesNotExist(int opcode, String identifier) {
        methodVisitor.visitVarInsn(opcode, variableIndex);
        byteCodeVariableRegistry.put(identifier,
                new RegistryVariable(variableIndex, typeStack.lastElement()));
        variableIndex++;
        // double takes 2 slots:
        if (typeStack.lastElement() == MovaType.DECIMAL) {
            variableIndex++;
        }
    }

    public void printlnValueOnTopOfOpStack() {
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System","out",
                Type.getType(PrintStream.class).getDescriptor());
        typeStack.add(MovaType.OTHER);
        // bring the value we want to print back on the top of stack:
        swap();
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream", "println",
                String.format("(%s)V", getLastTypeDescriptor()), false);
        popNValuesFromLocalStack(2);
    }

    private void swap() {
        MovaType lastType = typeStack.lastElement();
        MovaType secondType = getLeftElementOfStack();

        if (MovaType.DECIMAL.equals(secondType) && MovaType.DECIMAL.equals(lastType)) {
            swapTwoSlotsWithTwoSlots();
        } else if (MovaType.DECIMAL.equals(secondType)) {
            swapOneSlotWithTwoSlots();
        } else if (MovaType.DECIMAL.equals(lastType)) {
            swapTwoSlotsWithOneSlot();
        } else {
            methodVisitor.visitInsn(Opcodes.SWAP);
        }
        swapLocalStack();
    }

    /***
     * input: a, b1, b2
     * output: b1, b2, a
     */
    private void swapOneSlotWithTwoSlots() {
        methodVisitor.visitInsn(Opcodes.DUP_X2);
        methodVisitor.visitInsn(Opcodes.POP);
    }

    /***
     * input: a1, a2, b1, b2
     * output: b1, b2, a1, a2
     */
    private void swapTwoSlotsWithTwoSlots() {
        methodVisitor.visitInsn(Opcodes.DUP2_X2);
        methodVisitor.visitInsn(Opcodes.POP2);
    }

    /***
     * input: a1, a2, b
     * output: b, a1, a2
     */
    private void swapTwoSlotsWithOneSlot() {
        methodVisitor.visitInsn(Opcodes.DUP2_X1);
        methodVisitor.visitInsn(Opcodes.POP2);
    }

    private void swapLocalStack() {
        MovaType first = typeStack.pop();
        MovaType second = typeStack.pop();
        typeStack.add(first);
        typeStack.add(second);
    }

    public void loadVariableToOpStack(String identifier) {
        Optional<RegistryVariable> registryVariableOption =
                Optional.ofNullable(byteCodeVariableRegistry.get(identifier));
        registryVariableOption.ifPresent(registryVariable -> {
            typeStack.add(registryVariable.type());
            int opcodes = switch (registryVariable.type()) {
                case INTEGER -> Opcodes.ILOAD;
                case DECIMAL -> Opcodes.DLOAD;
                default -> Opcodes.ALOAD;
            };
            methodVisitor.visitVarInsn(opcodes, registryVariable.id());
        });
        if (registryVariableOption.isEmpty()) {
            // if there is no such variable then load the identifier itself in the stack:
            typeStack.add(MovaType.STRING);
            methodVisitor.visitLdcInsn(identifier);
        }
    }

    public void pushValueToOpStack(MovaValue value) {
        typeStack.add(value.getMovaType());
        switch (typeStack.lastElement()) {
            case STRING -> methodVisitor.visitLdcInsn(value.getStringValue());
            case DECIMAL -> methodVisitor.visitLdcInsn(value.getDoubleValue());
            case INTEGER -> methodVisitor.visitLdcInsn(value.getIntegerValue());
        }
    }

    public void reverseLastStackValue() {
        convertToString(false);
        String stringBuilderPath = "java/lang/StringBuilder";

        methodVisitor.visitTypeInsn(Opcodes.NEW, stringBuilderPath);
        methodVisitor.visitInsn(Opcodes.DUP);
        swapTwoSlotsWithOneSlot();

        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, stringBuilderPath,"<init>",
                "(" + Type.getDescriptor(String.class) + ")V",false);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder","reverse", "()" +
                Type.getDescriptor(StringBuilder.class),false);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath,
                "toString","()" +
            Type.getDescriptor(String.class),false);
    }

    public void reverseVariableValue(String identifier) {
        loadVariableToOpStack(identifier);
        reverseLastStackValue();
        addVariableAssignment(identifier);
    }

    /***
     * pushes 1 onto the stack, and applies the sum operation
     *    then it stores the value in the new variable with the same identifier
     * @param identifier (name) of the variable to be incremented
     */
    public void incrementVariable(String identifier) {
        loadVariableToOpStack(identifier);
        incrementLastStackValue();
        addVariableAssignment(identifier);
    }

    /***
     * put 1 on top of opstack and add that to value under it
     */
    public void incrementLastStackValue() {
        if (typeStack.lastElement().equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(typeStack.lastElement(), ONE));
            methodVisitor.visitInsn(Opcodes.IADD);
        } else if (typeStack.lastElement().equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(typeStack.lastElement(), ONE));
            methodVisitor.visitInsn(Opcodes.DADD);
        }
    }

    /***
     * put 1 on top of opstack and subtract that from value under it
     */
    public void decrementLastStackValue() {
        if (typeStack.lastElement().equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(typeStack.lastElement(), ONE));
            methodVisitor.visitInsn(Opcodes.ISUB);
        } else if (typeStack.lastElement().equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(typeStack.lastElement(), ONE));
            methodVisitor.visitInsn(Opcodes.DSUB);
        }
    }

    public void decrementVariable(String identifier) {
        loadVariableToOpStack(identifier);
        decrementLastStackValue();
        addVariableAssignment(identifier);
    }

    public void concatenate(boolean prefix) {
        String stringBuilderPath = "java/lang/StringBuilder";
        if (!prefix) swap();
        methodVisitor.visitTypeInsn(Opcodes.NEW, stringBuilderPath);
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, stringBuilderPath,
                "<init>","()V",false);
        // call append method twice to append two parts (initially it's empty):
        for (int i = 0; i < 2; i++) {
            swap();
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath,
                    "append", "(" + Type.getDescriptor(String.class) + ")" +
                            Type.getDescriptor(StringBuilder.class),false);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath,
                "toString","()" + Type.getDescriptor(String.class),false);
    }

    public void performBytecodeOperation(MovaAction action) {
        MovaType rightType = typeStack.lastElement();
        MovaType leftType = getLeftElementOfStack();

        // string operation
        if (MovaAction.PREFIX.equals(action) ||
                MovaAction.SUFFIX.equals(action) ||
                MovaAction.WITH.equals(action)) {
            if (!MovaType.STRING.equals(leftType)) {
                convertToString(true);
            }
            if (!MovaType.STRING.equals(rightType)) {
                convertToString(false);
            }
            concatenate(action == MovaAction.PREFIX);
            popNValuesFromLocalStack(2);
            typeStack.add(MovaType.STRING);
        } else { // integer or decimal
            MovaType actionType;

            if (MovaType.DECIMAL.equals(rightType) && !MovaType.DECIMAL.equals(leftType)) {
                convertToDecimal(true);
                actionType = MovaType.DECIMAL;
            } else if (MovaType.DECIMAL.equals(leftType) && !MovaType.DECIMAL.equals(rightType)) {
                convertToDecimal(false);
                actionType = MovaType.DECIMAL;
            } else if ((MovaType.STRING.equals(rightType) && MovaType.INTEGER.equals(leftType)) ||
                    (MovaType.STRING.equals(leftType) && MovaType.INTEGER.equals(rightType))) {
                convertToDecimal(false);
                convertToDecimal(true);
                actionType = MovaType.DECIMAL;
            } else if (MovaType.STRING.equals(leftType) && MovaType.STRING.equals(rightType)) {
                convertToDecimal(true);
                convertToDecimal(false);
                actionType = MovaType.DECIMAL;
            } else actionType = leftType;

            doArithmeticOperation(actionType, action);
            popNValuesFromLocalStack(2);
            typeStack.add(actionType);
        }
    }

    private void doArithmeticOperation(MovaType context, MovaAction action) {
        switch (action) {
            case PLUS:
                if (context == MovaType.INTEGER) methodVisitor.visitInsn(Opcodes.IADD);
                else if (context == MovaType.DECIMAL) methodVisitor.visitInsn(Opcodes.DADD);
                break;
            case MINUS:
                if (context == MovaType.INTEGER) methodVisitor.visitInsn(Opcodes.ISUB);
                else if (context == MovaType.DECIMAL) methodVisitor.visitInsn(Opcodes.DSUB);
                break;
            case MULTIPLY:
                if (context == MovaType.INTEGER) methodVisitor.visitInsn(Opcodes.IMUL);
                else if (context == MovaType.DECIMAL) methodVisitor.visitInsn(Opcodes.DMUL);
                break;
            case DIVIDE:
                if (context == MovaType.INTEGER) methodVisitor.visitInsn(Opcodes.IDIV);
                else if (context == MovaType.DECIMAL) methodVisitor.visitInsn(Opcodes.DDIV);
                break;
        }
    }

    /***
     * In order to compare two last values on the stack, it performs a subtracting operation
     * taking into account the context being integer or decimal.
     * If the difference is resulted to be in the decimal context, it is rounded up into integer
     * because all the conditional jumps can be compared against integer values only:
     * IFGE, IFLT, IFGT, IFLE, IFNE, IFEQ
     * As the result, we are storing either 0 or 1 at the operand stack.
     * @param comparisonWay can be one of:
     *                          LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL, NOTEQUAL, EQUALS
     * @param negated if true, the condition is the opposite
     */
    public void compareTwoThings(MovaComparison comparisonWay, boolean negated) {
        if (MovaComparison.CONTAINS.equals(comparisonWay)) {
            storeBooleanStringContainsSubstring(negated);
        } else if (someOperandIsString() && MovaComparison.EQUALS.equals(comparisonWay)) {
            storeBooleanOnEqualStrings(negated);
        } else {
            int opcodeCondition;
            performBytecodeOperation(MovaAction.MINUS);
            signum();
            opcodeCondition = switch (comparisonWay) {
                case LESSTHAN -> negated ? Opcodes.IFGE : Opcodes.IFLT;
                case LESSOREQUAL -> negated ? Opcodes.IFGT : Opcodes.IFLE;
                case GREATERTHAN -> negated ? Opcodes.IFLE : Opcodes.IFGT;
                case GREATEROREQUAL -> negated ? Opcodes.IFLT : Opcodes.IFGE;
                case NOTEQUAL -> negated ? Opcodes.IFEQ : Opcodes.IFNE;
                default -> negated ? Opcodes.IFNE : Opcodes.IFEQ;
            };
            storeBooleanOnCondition(opcodeCondition);
        }
    }

    /***
     * Last value is compared to zero, and if the value is
     * greater than, less than, equals etc. (based on the opcodeCondition),
     * then 1 is stored on top of the operand stack,
     * otherwise 0 is stored instead.
     * This implementation helps support complex nested conditional expressions.
     * @param opcodeCondition can be one of:
     *                        156 (IFGE), 155 (IFLT), 157 (IFGT), 158 (IFLE), 154 (IFNE), 153 (IFEQ)
     */
    public void storeBooleanOnCondition(int opcodeCondition) {
        Label trueBranch = new Label();
        Label falseBranch = new Label();
        Label conditionEnd = new Label();
        methodVisitor.visitJumpInsn(opcodeCondition, trueBranch);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, falseBranch);
        methodVisitor.visitLabel(trueBranch);
        pushValueToOpStack(new MovaValue(MovaType.INTEGER, ONE));
        methodVisitor.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        methodVisitor.visitLabel(falseBranch);
        pushValueToOpStack(new MovaValue(MovaType.INTEGER, ZERO));
        methodVisitor.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        methodVisitor.visitLabel(conditionEnd);
    }

    public void storeBooleanStringContainsSubstring(boolean negated) {
        Stream.of(true, false).forEach(this::convertToString);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/String","contains",
                "(" + Type.getDescriptor(CharSequence.class) + ")" +
                        Type.BOOLEAN_TYPE.getDescriptor(),false);
        storeBooleanOnCondition(negated ? Opcodes.IFLE : Opcodes.IFGT);
    }

    public void storeBooleanOnEqualStrings(boolean negated) {
        Stream.of(true, false).forEach(this::convertToString);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String",
                "equals", "(" + Type.getDescriptor(Object.class) + ")" +
                        Type.BOOLEAN_TYPE.getDescriptor(),false);
        storeBooleanOnCondition(negated ? Opcodes.IFLE : Opcodes.IFGT);
    }

    /***
     * todo good illustration for J-Fall
     * run block of code if the last value on stack is greater than 0.
     * if it's zero, optionally run different block of code
     * the last value on stack must be integer
     * @param function to perform on a code block (valid structure context)
     * @param ifTrueContext valid structure (block) to run if condition is true
     * @param ifFalseContextOption optional valid structure (block) to run if condition is false
     */
    public void doOrElse(Function<MovaParser.ValidStructureContext, Void> function,
                         MovaParser.ValidStructureContext ifTrueContext,
                         Optional<MovaParser.ValidStructureContext> ifFalseContextOption) {
        Label trueBranch = new Label();
        Label falseBranch = new Label();
        Label conditionEnd = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IFGT, trueBranch);
        methodVisitor.visitJumpInsn(Opcodes.GOTO,
                ifFalseContextOption.isPresent() ? falseBranch : conditionEnd);
        methodVisitor.visitLabel(trueBranch);
        function.apply(ifTrueContext);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        ifFalseContextOption.ifPresent(ifFalseContext -> {
            methodVisitor.visitLabel(falseBranch);
            function.apply(ifFalseContext);
            methodVisitor.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        });
        methodVisitor.visitLabel(conditionEnd);
    }

    /***
     * - beforeConditionLabel
     * - put a value of movaInternalVarIdentifier on top of operand stack
     *      (it already contains the result of expression calculation
     *      or decremented on previous iteration)
     * - if value on top of operand stack > 0, go to startLabel
     * - go to endLabel
     * - startLabel
     * - do something
     * - decrement movaInternalVarIdentifier
     * - go to beforeConditionLabel
     * - endLabel
     */
    public void expressionBasedLoop(Function<MovaParser.ValidStructureContext, Void> function,
                     MovaParser.ValidStructureContext ctx,
                     String movaInternalVarIdentifier) {
        Label beforeCondition = new Label();
        Label start = new Label();
        Label end = new Label();
        methodVisitor.visitLabel(beforeCondition);
        loadVariableToOpStack(movaInternalVarIdentifier);
        methodVisitor.visitJumpInsn(Opcodes.IFGT, start);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, end);
        methodVisitor.visitLabel(start);
        function.apply(ctx);
        decrementVariable(movaInternalVarIdentifier);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, beforeCondition);
        methodVisitor.visitLabel(end);
    }

    /***
     * - beforeConditionLabel
     * - run a condition
     * - if value on top of operand stack is 0, go to startLabel
     * - go to endLabel
     * - startLabel
     * - do something
     * - go to beforeConditionLabel
     * - endLabel
     */
    public void conditionBasedLoop(
            Function<MovaParser.ConditionContext, Void> conditionVisitorFunction,
            MovaParser.ConditionContext condition,
            Function<MovaParser.ValidStructureContext, Void> validStructureVisitorFunction,
                                   MovaParser.ValidStructureContext validStructure) {
        Label beforeCondition = new Label();
        Label start = new Label();
        Label end = new Label();
        methodVisitor.visitLabel(beforeCondition);
        conditionVisitorFunction.apply(condition);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, start);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, end);
        methodVisitor.visitLabel(start);
        validStructureVisitorFunction.apply(validStructure);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, beforeCondition);
        methodVisitor.visitLabel(end);
    }

    public void bitwiseOr() {
        methodVisitor.visitInsn(Opcodes.IOR);
    }

    public void bitwiseAnd() {
        methodVisitor.visitInsn(Opcodes.IAND);
    }

    /***
     * cleanly finish the class definition
     * @return the byte array of the program
     */
    public byte[] cleanCloseProgram() {
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }
}