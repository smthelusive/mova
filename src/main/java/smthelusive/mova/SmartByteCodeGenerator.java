package smthelusive.mova;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.objectweb.asm.*;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.domain.RegistryVariable;
import smthelusive.mova.gen.MovaParser;

import java.io.PrintStream;
import java.util.*;

// todo overwriting variables creates new variable instead of updating
public class SmartByteCodeGenerator {

    private final static String INCREMENT_DECREMENT_VALUE = "1";
    private MovaType currentContext;
    private boolean contextLocked = false;
    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    private int variableIndex = 1;
    private MethodVisitor mv;
    private final Map<String, RegistryVariable> byteCodeVariableRegistry = new HashMap<>();

    private final Stack<Label> labelRegistry = new Stack<>();
    private final Stack<Label> endLabelRegistry = new Stack<>();

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

    public void printVariable(String identifier) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", Type.getType(PrintStream.class).getDescriptor());
        loadVariableToOpStack(identifier);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", getContextDescriptor()), false);
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
     * - for integers it uses the bytecode built in integer increment
     * - for decimal it pushes 1 onto the stack, and applies the sum operation
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
            pushValueToOpStack(new MovaValue(MovaType.INTEGER, INCREMENT_DECREMENT_VALUE));
            mv.visitInsn(Opcodes.IADD);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(MovaType.DECIMAL, INCREMENT_DECREMENT_VALUE));
            mv.visitInsn(Opcodes.DADD);
        }
    }

    public void decrementLastStackValue(MovaType type) {
        if (type.equals(MovaType.INTEGER)) {
            pushValueToOpStack(new MovaValue(MovaType.INTEGER, INCREMENT_DECREMENT_VALUE));
            mv.visitInsn(Opcodes.ISUB);
        } else if (type.equals(MovaType.DECIMAL)) {
            pushValueToOpStack(new MovaValue(MovaType.DECIMAL, INCREMENT_DECREMENT_VALUE));
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

    private void negateLastValueOnStack() {
        pushValueToOpStack(new MovaValue(currentContext, "-1"));
        performBytecodeOperation(MovaAction.MULTIPLY);
    }

    public void calculateConditionalExpression(MovaParser.ConditionContext ctx) {
        currentContext = MovaType.INTEGER;
        int amountOfNegations = ctx.NOT().size();
        boolean negated = (amountOfNegations > 0) && (amountOfNegations % 2 != 0);
        int comparingSymbolIndex = ctx.NOT().size() + 1;
        TerminalNode comparison = (TerminalNode)ctx.getChild(comparingSymbolIndex);
        String comparisonKeyword = MovaParser.VOCABULARY.getSymbolicName(comparison.getSymbol().getType());
        switch (comparisonKeyword) {
            case "LESSTHAN":
                calculateLessThan();
                break;
            case "LESSOREQUAL":
                calculateLessOrEqual();
                break;
            case "GREATERTHAN":
                calculateGreaterThan();
                break;
            case "GREATEROREQUAL":
                calculateGreaterOrEqual();
                break;
            case "NOTEQUAL":
                calculateEquals();
                negateLastValueOnStack();
                break;
            case "EQUALS":
            default:
                calculateEquals();
        }
        if (negated) negateLastValueOnStack();
    }

    public void convertStackTopToBooleanValue() {
        // trick to convert negatives to 0 and positive values don't matter as long as it's positive
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math",
                "abs", "(" + Type.INT_TYPE.getDescriptor() + ")" +
                        Type.INT_TYPE.getDescriptor(), false);
        performBytecodeOperation(MovaAction.DIVIDE);
    }

    private void calculateEquals() {
        // a == b: a - b + 1
        performBytecodeOperation(MovaAction.MINUS);
        incrementLastStackValue(currentContext);
    }

    private void calculateLessThan() {
        // a < b: (a - b) * (-1)
        performBytecodeOperation(MovaAction.MINUS);
        negateLastValueOnStack();
    }

    private void calculateLessOrEqual() {
        // a <= b: ((a - b) - 1) * (-1)
        performBytecodeOperation(MovaAction.MINUS);
        decrementLastStackValue(currentContext);
        negateLastValueOnStack();
    }

    private void calculateGreaterThan() {
        // a > b: a - b
        performBytecodeOperation(MovaAction.MINUS);
    }

    private void calculateGreaterOrEqual() {
        // a >= b: (a - (b - 1))
        decrementLastStackValue(currentContext);
        performBytecodeOperation(MovaAction.MINUS);
    }

    public void performConditionalJump(String comparisonKeyword, boolean negated) {
        mv.visitJumpInsn(getComparisonOpcodes(comparisonKeyword, negated),
                labelRegistry.lastElement());
    }

    private int getComparisonOpcodes(String comparisonKeyword, boolean negated) {
        int opcodes;
        switch (comparisonKeyword) {
            case "LESSTHAN":
                opcodes = negated ? Opcodes.IF_ICMPGE : Opcodes.IF_ICMPLT;
                break;
            case "LESSOREQUAL":
                opcodes = negated ? Opcodes.IF_ICMPGT : Opcodes.IF_ICMPLE;
                break;
            case "GREATERTHAN":
                opcodes = negated ? Opcodes.IF_ICMPLE : Opcodes.IF_ICMPGT;
                break;
            case "GREATEROREQUAL":
                opcodes = negated ? Opcodes.IF_ICMPLT : Opcodes.IF_ICMPGE;
                break;
            case "NOTEQUAL":
                opcodes = negated ? Opcodes.IF_ACMPEQ : Opcodes.IF_ICMPNE;
                break;
            case "EQUALS":
            default:
                opcodes = negated ? Opcodes.IF_ICMPNE : Opcodes.IF_ICMPEQ;
        }
        return opcodes;
    }

    // if top of the stack is greater than 0, go to current label
    public void jumpIFGT() {
        mv.visitJumpInsn(Opcodes.IFGT, labelRegistry.lastElement());
    }

    public void createNewLabel() {
        labelRegistry.add(new Label());
    }

    public void writeByteCodeLabel() {
        mv.visitLabel(labelRegistry.lastElement());
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    }

    public void writeByteCodeEndLabel() {
        mv.visitLabel(endLabelRegistry.pop());
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
    }

    public void createEndLabel() {
        endLabelRegistry.add(new Label());
    }

    public void gotoEnd() {
        mv.visitJumpInsn(Opcodes.GOTO, endLabelRegistry.lastElement());
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
