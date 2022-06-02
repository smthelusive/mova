package smthelusive.mova;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.domain.RegistryVariable;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

// todo overwriting variables creates new variable instead of updating
public class SmartByteCodeGenerator {

    private MovaType currentContext;
    private boolean contextLocked = false;
    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
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
            if (movaType == MovaType.DECIMAL && currentContext != MovaType.STRING) {
                if (currentContext == MovaType.INTEGER) {
                    // convert previous value to be able to perform double operation
                    mv.visitInsn(Opcodes.I2D);
                }
                currentContext = MovaType.DECIMAL;
            }
            else if (movaType == MovaType.STRING)
                currentContext = MovaType.STRING;
        } else currentContext = movaType;
    }

    public void lockContext() {
        contextLocked = true;
    }

    private String getContextDescriptor() {
        switch (currentContext) {
            case INTEGER: return Type.INT_TYPE.getDescriptor();
            case DECIMAL: return Type.DOUBLE_TYPE.getDescriptor();
            case STRING:
            default:
                return Type.getType(String.class).getDescriptor();
        }
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

        // bring the value we want to print back on the top of stack.
        // depending on type it is 1 or 2 slots.
        if (currentContext == MovaType.DECIMAL) {
            mv.visitInsn(Opcodes.DUP_X2);
            mv.visitInsn(Opcodes.POP);
        } else {
            mv.visitInsn(Opcodes.SWAP);
        }

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", getContextDescriptor()), false);
    }

    public void printVariable(String identifier) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", Type.getType(PrintStream.class).getDescriptor());
        loadVariableToOpStack(identifier);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", getContextDescriptor()), false);
    }

    public void loadVariableToOpStack(String identifier) {
        RegistryVariable registryVariable = byteCodeVariableRegistry.get(identifier);
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
        // todo if current context is string, store the string in opstack
    }

    public void pushValueToOpstack(MovaValue value) {
        processContext(value.getMovaType());
        switch (currentContext) {
            case STRING: mv.visitLdcInsn(value.getStringValue());
            break;
            case DECIMAL: mv.visitLdcInsn(value.getDoubleValue());
            break;
            case INTEGER: mv.visitLdcInsn(value.getIntegerValue());
        }
    }

    // integer increment todo rewrite
    public void incrementVariable(String identifier) {
        mv.visitIincInsn(byteCodeVariableRegistry.get(identifier).getId(), 1);
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
            default:
        }
        // todo perform actions for strings
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
