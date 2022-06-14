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

// todo overwriting variables creates new variable instead of updating
public class SmartByteCodeGenerator {

    private final static String ONE = "1";
    private final static String ZERO = "0";
    private MovaType currentContext;
    private boolean contextLocked = false;
    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    private int variableIndex = 1;
    private MethodVisitor mv;
    private final Map<String, RegistryVariable> byteCodeVariableRegistry = new HashMap<>();

    /***
     * the strongest context is String, then goes Decimal, the last is Integer
     * context might be locked for expressions.
     * first incoming value locks the context and the second one relies on it. when it's done, it releases the context
     * @param movaType the incoming type context to process
     */
    public void processContext(MovaType movaType) {
        if (contextLocked) {
            if (movaType == MovaType.DECIMAL) {
                switchContextToDecimal();
            } else if (movaType == MovaType.STRING) {
                switchContextToString();
            }
        } else currentContext = movaType;
    }

    private void switchContextToDecimal() {
        if (currentContext == MovaType.INTEGER) {
            // convert previous value to be able to perform double operation
            mv.visitInsn(Opcodes.I2D);
            currentContext = MovaType.DECIMAL;
        }
    }

    // todo refactor a little
    private void switchContextToString() {
        if (currentContext == MovaType.INTEGER) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String",
                    "valueOf", "(" + Type.INT_TYPE.getDescriptor() + ")" +
                            Type.getDescriptor(String.class), false);
        } else if (currentContext == MovaType.DECIMAL) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String",
                    "valueOf", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                            Type.getDescriptor(String.class), false);
        }
        currentContext = MovaType.STRING;
    }

    public void lockContext() {
        contextLocked = true;
    }

    private String getContextDescriptor() {
        return Optional.ofNullable(currentContext).map(context -> {
            switch (context) {
                case INTEGER: return Type.INT_TYPE.getDescriptor();
                case DECIMAL: return Type.DOUBLE_TYPE.getDescriptor();
                case STRING:
                default:
                    return Type.getType(String.class).getDescriptor();
            }
        }).orElse(Type.getType(String.class).getDescriptor());
    }

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

    /***
     * Saves the value from the opstack to local variable
     * @param identifier of a variable stored in the compiler's registry
     */
    public void addVariableAssignment(String identifier) {
        int opcode;
        switch (currentContext) {
            case STRING: opcode = Opcodes.ASTORE;
            break;
            case DECIMAL: opcode = Opcodes.DSTORE;
            break;
            default: opcode = Opcodes.ISTORE;
        }

        mv.visitVarInsn(opcode, variableIndex);
        byteCodeVariableRegistry.put(identifier, new RegistryVariable(variableIndex, currentContext));
        variableIndex++;
        // double takes 2 slots:
        if (currentContext == MovaType.DECIMAL) {
            variableIndex++;
        }
    }

    /***
     * todo
     */
    public void printlnValueOnTopOfOpStack() {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", Type.getType(PrintStream.class).getDescriptor());
        // bring the value we want to print back on the top of stack:
        swap();
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", getContextDescriptor()), false);
    }

    private void swap() {
        // depending on type it is 1 or 2 slots.
        if (currentContext == MovaType.DECIMAL) {
            mv.visitInsn(Opcodes.DUP_X2);
            mv.visitInsn(Opcodes.POP);
        } else {
            mv.visitInsn(Opcodes.SWAP);
        }
    }

    public void loadVariableToOpStack(String identifier) {
        Optional<RegistryVariable> registryVariableOption = Optional.ofNullable(byteCodeVariableRegistry.get(identifier));
        registryVariableOption.ifPresent(registryVariable -> {
            processContext(registryVariable.getType());
            int opcodes;
            switch (registryVariable.getType()) {
                case INTEGER: opcodes = Opcodes.ILOAD;
                    break;
                case DECIMAL: opcodes = Opcodes.DLOAD;
                    break;
                case STRING:
                default: opcodes = Opcodes.ALOAD;
            }
            mv.visitVarInsn(opcodes, registryVariable.getId());
        });
        if (registryVariableOption.isEmpty()) {
            // if there is no such variable then load the identifier itself in the stack:
            processContext(MovaType.STRING);
            mv.visitLdcInsn(identifier);
        }
    }

    public void pushValueToOpStack(MovaValue value) {
        processContext(value.getMovaType());
        switch (currentContext) {
            case STRING: mv.visitLdcInsn(value.getStringValue());
            break;
            case DECIMAL: mv.visitLdcInsn(value.getDoubleValue());
            break;
            case INTEGER: mv.visitLdcInsn(value.getIntegerValue());
        }
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
            pushValueToOpStack(new MovaValue(MovaType.INTEGER, ONE));
            mv.visitInsn(Opcodes.IADD);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(MovaType.DECIMAL, ONE));
            mv.visitInsn(Opcodes.DADD);
        }
    }

    public void decrementLastStackValue(MovaType type) {
        if (type.equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(MovaType.INTEGER, ONE));
            mv.visitInsn(Opcodes.ISUB);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(MovaType.DECIMAL, ONE));
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

    /***
     * Perform mathematical operation against two last values on top of operand stack.
     * To support decimal and string operations without unexpected issues:
     * <li>between storing first and second value on top of the operand stack, method {@link #lockContext()} must be called;</li>
     * <li>the values should be stored to the operand stack only using {@link SmartByteCodeGenerator} API.</li>
     * @param action mathematical or string operation to be performed against two last values on the stack.
     */
    public void performBytecodeOperation(MovaAction action) {
        contextLocked = false;
        switch (action) {
            case PLUS:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IADD);
                else if (currentContext == MovaType.DECIMAL) mv.visitInsn(Opcodes.DADD);
                break;
            case MINUS:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.ISUB);
                else if (currentContext == MovaType.DECIMAL) mv.visitInsn(Opcodes.DSUB);
                break;
            case MULTIPLY:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IMUL);
                else if (currentContext == MovaType.DECIMAL) mv.visitInsn(Opcodes.DMUL);
                break;
            case DIVIDE:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IDIV);
                else if (currentContext == MovaType.DECIMAL) mv.visitInsn(Opcodes.DDIV);
                break;
            case PREFIX: concatenate(true);
                break;
            case SUFFIX:
            case WITH:
            default: concatenate(false);
        }
    }

    /***
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
        int opcodeCondition;
        performBytecodeOperation(MovaAction.MINUS);
        if (currentContext == MovaType.DECIMAL) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                    "ceil", "(" + Type.DOUBLE_TYPE.getDescriptor() + ")" +
                            Type.DOUBLE_TYPE.getDescriptor(), false);
            mv.visitInsn(Opcodes.D2I);
        }
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


    public void doIfTrue(Function<MovaParser.ValidStructureContext, Void> function, MovaParser.ValidStructureContext ctx) {
        Label trueBranch = new Label();
        Label conditionEnd = new Label();
        mv.visitJumpInsn(Opcodes.IFGT, trueBranch);
        mv.visitJumpInsn(Opcodes.GOTO, conditionEnd);
        mv.visitLabel(trueBranch);
        function.apply(ctx);
        mv.visitLabel(conditionEnd);
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

    // todo refactor
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
        mv.visitJumpInsn(Opcodes.IFGT, start);
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