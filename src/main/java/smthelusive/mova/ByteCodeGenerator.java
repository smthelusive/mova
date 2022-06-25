package smthelusive.mova;

import org.objectweb.asm.*;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.domain.RegistryVariable;
import smthelusive.mova.gen.MovaParser;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class ByteCodeGenerator {

    private final static String ONE = "1";
    private final static String ZERO = "0";
    private final static Stack<MovaType> typeStack = new Stack<>();
    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    private int variableIndex = 1;
    private MethodVisitor mv;
    private final Map<String, RegistryVariable> byteCodeVariableRegistry = new HashMap<>();

    /**
     * start the class definition with the given class name
     * start the main method
     * @param name the name of the program
     */
    public void init(String name) {
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                "([" + Type.getType(String.class).getDescriptor() + ")V", null, null);
        mv.visitCode();
    }

    public void convertToDecimal(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.STRING.equals(fromType) && !MovaType.INTEGER.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();
        switch (fromType) {
            case INTEGER -> {
                mv.visitInsn(Opcodes.I2D);
                changeLastTypeOnLocalStackTo(MovaType.DECIMAL);
            }
            case STRING -> {
                mv.visitLdcInsn(",");
                mv.visitLdcInsn(".");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "replace",
                        "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", false);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double",
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

    public void convertToInteger(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.DECIMAL.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                "ceil", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                        Type.DOUBLE_TYPE.getDescriptor(), false);
        mv.visitInsn(Opcodes.D2I);
        changeLastTypeOnLocalStackTo(MovaType.INTEGER);
        // swap to be able to convert the element before the last one
        if (left) swap();
    }

    /***
     * only used internally for processing conditions
     */
    private void signum() {
        if (!MovaType.DECIMAL.equals(typeStack.lastElement())) return;
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                "signum", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                        Type.DOUBLE_TYPE.getDescriptor(), false);
        mv.visitInsn(Opcodes.D2I);
        changeLastTypeOnLocalStackTo(MovaType.INTEGER);
    }

    public void convertToString(boolean left) {
        MovaType fromType = left ? getLeftElementOfStack() : typeStack.lastElement();
        if (!MovaType.INTEGER.equals(fromType) && !MovaType.DECIMAL.equals(fromType)) return;
        // swap to be able to convert the element before the last one
        if (left) swap();
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String",
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

    public void loadArgumentByIndex(int argIndex) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLdcInsn(argIndex);
        mv.visitInsn(Opcodes.AALOAD);
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

        Optional<RegistryVariable> existingVariable = Optional.ofNullable(byteCodeVariableRegistry.get(identifier));
        if (existingVariable.isPresent()) {
            mv.visitVarInsn(opcode, existingVariable.get().getId());
            // if the type has changed, we should update it in the registry:
            if (!typeStack.lastElement().equals(existingVariable.get().getType())) {
                byteCodeVariableRegistry.replace(identifier, existingVariable.get(),
                        new RegistryVariable(existingVariable.get().getId(), typeStack.lastElement()));
            }
        } else {
            mv.visitVarInsn(opcode, variableIndex);
            byteCodeVariableRegistry.put(identifier, new RegistryVariable(variableIndex, typeStack.lastElement()));
            variableIndex++;
            // double takes 2 slots:
            if (typeStack.lastElement() == MovaType.DECIMAL) {
                variableIndex++;
            }
        }
        typeStack.pop();
    }

    public void printlnValueOnTopOfOpStack() {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", Type.getType(PrintStream.class).getDescriptor());
        typeStack.add(MovaType.OTHER);
        // bring the value we want to print back on the top of stack:
        swap();
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
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
            mv.visitInsn(Opcodes.SWAP);
        }
        swapLocalStack();
    }

    /***
     * input: a1, a2, b
     * output: b, a1, a2
     */
    private void swapOneSlotWithTwoSlots() {
        mv.visitInsn(Opcodes.DUP_X2);
        mv.visitInsn(Opcodes.POP);
    }

    /***
     * input: a1, a2, b1, b2
     * output: b1, b2, a1, a2
     */
    private void swapTwoSlotsWithTwoSlots() {
        mv.visitInsn(Opcodes.DUP2_X2);
        mv.visitInsn(Opcodes.POP2);
    }

    /***
     * input: a, b1, b2
     * output: b1, b2, a
     */
    private void swapTwoSlotsWithOneSlot() {
        mv.visitInsn(Opcodes.DUP2_X1);
        mv.visitInsn(Opcodes.POP2);
    }

    private void swapLocalStack() {
        MovaType first = typeStack.pop();
        MovaType second = typeStack.pop();
        typeStack.add(first);
        typeStack.add(second);
    }

    public void loadVariableToOpStack(String identifier) {
        Optional<RegistryVariable> registryVariableOption = Optional.ofNullable(byteCodeVariableRegistry.get(identifier));
        registryVariableOption.ifPresent(registryVariable -> {
            typeStack.add(registryVariable.getType());
            int opcodes = switch (registryVariable.getType()) {
                case INTEGER -> Opcodes.ILOAD;
                case DECIMAL -> Opcodes.DLOAD;
                default -> Opcodes.ALOAD;
            };
            mv.visitVarInsn(opcodes, registryVariable.getId());
        });
        if (registryVariableOption.isEmpty()) {
            // if there is no such variable then load the identifier itself in the stack:
            typeStack.add(MovaType.STRING);
            mv.visitLdcInsn(identifier);
        }
    }

    public void pushValueToOpStack(MovaValue value) {
        typeStack.add(value.getMovaType());
        switch (typeStack.lastElement()) {
            case STRING -> mv.visitLdcInsn(value.getStringValue());
            case DECIMAL -> mv.visitLdcInsn(value.getDoubleValue());
            case INTEGER -> mv.visitLdcInsn(value.getIntegerValue());
        }
    }

    // todo javadocs
    public void reverseLastStackValue() {
        convertToString(false);
        String stringBuilderPath = "java/lang/StringBuilder";

        mv.visitTypeInsn(Opcodes.NEW, stringBuilderPath);
        mv.visitInsn(Opcodes.DUP);
        swapTwoSlotsWithOneSlot();

        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, stringBuilderPath,"<init>",
                "(" + Type.getDescriptor(String.class) + ")V",false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder","reverse", "()" +
                Type.getDescriptor(StringBuilder.class),false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath, "toString","()" +
            Type.getDescriptor(String.class),false);
    }

    // todo javadocs
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
        RegistryVariable variable = byteCodeVariableRegistry.get(identifier);
        incrementLastStackValue(variable.getType());
        addVariableAssignment(identifier);
    }

    public void incrementLastStackValue(MovaType type) {
        if (type.equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(type, ONE));
            mv.visitInsn(Opcodes.IADD);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(type, ONE));
            mv.visitInsn(Opcodes.DADD);
        }
    }

    public void decrementLastStackValue(MovaType type) {
        if (type.equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(type, ONE));
            mv.visitInsn(Opcodes.ISUB);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(type, ONE));
            mv.visitInsn(Opcodes.DSUB);
        }
    }

    public void decrementVariable(String identifier) {
        loadVariableToOpStack(identifier);
        RegistryVariable variable = byteCodeVariableRegistry.get(identifier);
        decrementLastStackValue(variable.getType());
        addVariableAssignment(identifier);
    }

    public void concatenate(boolean prefix) {
        String stringBuilderPath = "java/lang/StringBuilder";
        if (!prefix) swap();
        mv.visitTypeInsn(Opcodes.NEW, stringBuilderPath);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, stringBuilderPath,"<init>","()V",false);
        // call append method twice:
        for (int i = 0; i < 2; i++) {
            swap();
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath,"append", "(" +
                    Type.getDescriptor(String.class) + ")" + Type.getDescriptor(StringBuilder.class),false);
        }
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, stringBuilderPath, "toString","()" +
                Type.getDescriptor(String.class),false);
    }

    public void performBytecodeOperation(MovaAction action) {
        MovaType rightType = typeStack.lastElement();
        MovaType leftType = getLeftElementOfStack();

        if (MovaAction.PREFIX.equals(action) || MovaAction.SUFFIX.equals(action) || MovaAction.WITH.equals(action)) {
            if (!MovaType.STRING.equals(leftType)) {
                convertToString(true);
            }
            if (!MovaType.STRING.equals(rightType)) {
                convertToString(false);
            }
            concatenate(action == MovaAction.PREFIX);
            popNValuesFromLocalStack(2);
            typeStack.add(MovaType.STRING);
        } else {
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
                if (context == MovaType.INTEGER) mv.visitInsn(Opcodes.IADD);
                else if (context == MovaType.DECIMAL) mv.visitInsn(Opcodes.DADD);
                break;
            case MINUS:
                if (context == MovaType.INTEGER) mv.visitInsn(Opcodes.ISUB);
                else if (context == MovaType.DECIMAL) mv.visitInsn(Opcodes.DSUB);
                break;
            case MULTIPLY:
                if (context == MovaType.INTEGER) mv.visitInsn(Opcodes.IMUL);
                else if (context == MovaType.DECIMAL) mv.visitInsn(Opcodes.DMUL);
                break;
            case DIVIDE:
                if (context == MovaType.INTEGER) mv.visitInsn(Opcodes.IDIV);
                else if (context == MovaType.DECIMAL) mv.visitInsn(Opcodes.DDIV);
                break;
        }
    }

    /***
     * todo update for contains
     * In order to compare two last values on the stack, it performs a subtracting operation
     * taking into account the context being integer or decimal.
     * If the difference is resulted to be in the decimal context, it is rounded up into integer
     * because all the conditional jumps can be compared against integer values only:
     * IFGE, IFLT, IFGT, IFLE, IFNE, IFEQ
     * As the result, we are storing either 0 or 1 at the operand stack.
     * @param comparisonKeyword can be one of:
     *                          LESSTHAN, LESSOREQUAL, GREATERTHAN, GREATEROREQUAL, NOTEQUAL, EQUALS
     * @param negated if true, the condition is the opposite
     */
    public void compareTwoThings(String comparisonKeyword, boolean negated) {
        if ("CONTAINS".equals(comparisonKeyword)) {
            storeBooleanStringContainsSubstring(negated);
        } else if (someOperandIsString() && "EQUALS".equals(comparisonKeyword)) {
            storeBooleanOnEqualStrings(negated);
        } else {
            int opcodeCondition;
            performBytecodeOperation(MovaAction.MINUS);
            signum();
            switch (comparisonKeyword) {
                case "LESSTHAN":
                    opcodeCondition = negated ? Opcodes.IFGE : Opcodes.IFLT;
                    break;
                case "LESSOREQUAL":
                    opcodeCondition = negated ? Opcodes.IFGT : Opcodes.IFLE;
                    break;
                case "GREATERTHAN":
                    opcodeCondition = negated ? Opcodes.IFLE : Opcodes.IFGT;
                    break;
                case "GREATEROREQUAL":
                    opcodeCondition = negated ? Opcodes.IFLT : Opcodes.IFGE;
                    break;
                case "NOTEQUAL":
                    opcodeCondition = negated ? Opcodes.IFEQ : Opcodes.IFNE;
                    break;
                case "EQUALS":
                default:
                    opcodeCondition = negated ? Opcodes.IFNE : Opcodes.IFEQ;
            }
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
        mv.visitJumpInsn(opcodeCondition, trueBranch);
        mv.visitJumpInsn(Opcodes.GOTO, falseBranch);
        mv.visitLabel(trueBranch);
        pushValueToOpStack(new MovaValue(MovaType.INTEGER, ONE));
        mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        mv.visitLabel(falseBranch);
        pushValueToOpStack(new MovaValue(MovaType.INTEGER, ZERO));
        mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        mv.visitLabel(conditionEnd);
    }

    public void storeBooleanStringContainsSubstring(boolean negated) {
        Stream.of(true, false).forEach(this::convertToString);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String","contains", "(" +
                Type.getDescriptor(CharSequence.class) + ")" + Type.BOOLEAN_TYPE.getDescriptor(),false);
        storeBooleanOnCondition(negated ? Opcodes.IFLE : Opcodes.IFGT);
    }

    public void storeBooleanOnEqualStrings(boolean negated) {
        Stream.of(true, false).forEach(this::convertToString);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String","equals", "(" +
                Type.getDescriptor(Object.class) + ")" + Type.BOOLEAN_TYPE.getDescriptor(),false);
        storeBooleanOnCondition(negated ? Opcodes.IFLE : Opcodes.IFGT);
    }

    /***
     * run block of code if the last value on stack is greater than 0.
     * if it's zero, optionally run different block of code
     * the last value on stack must be integer
     * @param function to perform on a code block (valid structure context)
     * @param ifTrueContext valid structure (block) to run if condition is true
     * @param ifFalseContext optional valid structure (block) to run if condition is false
     */
    public void doOrElse(Function<MovaParser.ValidStructureContext, Void> function,
                         MovaParser.ValidStructureContext ifTrueContext,
                         Optional<MovaParser.ValidStructureContext> ifFalseContext) {
        Label trueBranch = new Label();
        Label falseBranch = new Label();
        Label conditionEnd = new Label();
        mv.visitJumpInsn(Opcodes.IFGT, trueBranch);
        if (ifFalseContext.isPresent()) {
            mv.visitJumpInsn(Opcodes.GOTO, falseBranch);
        } else {
            mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        }
        mv.visitLabel(trueBranch);
        function.apply(ifTrueContext);
        mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        if (ifFalseContext.isPresent()) {
            mv.visitLabel(falseBranch);
            function.apply(ifFalseContext.get());
            mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        }
        mv.visitLabel(conditionEnd);
    }

    public void expressionBasedLoop(Function<MovaParser.ValidStructureContext, Void> function,
                     MovaParser.ValidStructureContext ctx,
                     String movaInternalVarIdentifier) {
        Label beforeCondition = new Label();
        Label start = new Label();
        Label end = new Label();
        mv.visitLabel(beforeCondition);
        loadVariableToOpStack(movaInternalVarIdentifier);
        mv.visitJumpInsn(Opcodes.IFGT, start);
        mv.visitJumpInsn(Opcodes.GOTO, end);
        mv.visitLabel(start);
        function.apply(ctx);
        decrementVariable(movaInternalVarIdentifier);
        mv.visitJumpInsn(Opcodes.GOTO, beforeCondition);
        mv.visitLabel(end);
    }

    public void conditionBasedLoop(
            Function<MovaParser.ConditionContext, Void> conditionVisitorFunction,
            MovaParser.ConditionContext condition,
            Function<MovaParser.ValidStructureContext, Void> validStructureVisitorFunction,
                                   MovaParser.ValidStructureContext validStructure) {
        Label beforeCondition = new Label();
        Label start = new Label();
        Label end = new Label();
        mv.visitLabel(beforeCondition);
        conditionVisitorFunction.apply(condition);
        mv.visitJumpInsn(Opcodes.IFEQ, start);
        mv.visitJumpInsn(Opcodes.GOTO, end);
        mv.visitLabel(start);
        validStructureVisitorFunction.apply(validStructure);
        mv.visitJumpInsn(Opcodes.GOTO, beforeCondition);
        mv.visitLabel(end);
    }

    public void bitwiseOr() {
        mv.visitInsn(Opcodes.IOR);
    }

    public void bitwiseAnd() {
        mv.visitInsn(Opcodes.IAND);
    }

    /***
     * cleanly finish the class definition
     * @return the byte array of the program
     */
    public byte[] cleanCloseProgram() {
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }
}