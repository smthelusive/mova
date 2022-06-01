package smthelusive.mova;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;

import java.util.HashMap;
import java.util.Map;

public class SmartByteCodeGenerator {

    private MovaType currentContext;
    private boolean contextLocked = false;
    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    private int variableIndex = 1;
    private MethodVisitor mv;
    private final Map<String, Integer> byteCodeVariableRegistry = new HashMap<>();

    /***
     * the strongest context is String, then goes Decimal, the last is Integer
     * context might be locked for expressions.
     * first incoming value locks the context and the second one relies on it. when it's done, it releases the context
     * @param movaType the incoming type context to process
     */
    public void processContext(MovaType movaType) {
        if (contextLocked) {
            contextLocked = false;
            if (movaType == MovaType.INTEGER && currentContext != MovaType.DECIMAL && currentContext != MovaType.STRING)
                currentContext = MovaType.INTEGER;
            else if (movaType == MovaType.DECIMAL && currentContext != MovaType.STRING)
                currentContext = MovaType.DECIMAL;
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
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, name, null, Type.getType(Object.class).getClassName(), null);
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
        byteCodeVariableRegistry.put(identifier, variableIndex);
        variableIndex++;
    }

    public void printlnValueOnTopOfOpStack() {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", "Ljava/io/PrintStream;");
        mv.visitInsn(Opcodes.SWAP);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", getContextDescriptor()), false);
    }

    public void loadVariableToOpStack(String identifier) {
        int varId = byteCodeVariableRegistry.get(identifier);
        mv.visitVarInsn(Opcodes.ALOAD, varId); // todo is it really ALOAD? how do I get exact type?
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

    public void performBytecodeOperation(MovaAction action) {
        switch (action) {
            case PLUS:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IADD);
                else if (currentContext == MovaType.DECIMAL) mv.visitLdcInsn(Opcodes.DADD);
                break;
            case MINUS:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.ISUB);
                else if (currentContext == MovaType.DECIMAL) mv.visitLdcInsn(Opcodes.DSUB);
                break;
            case MULTIPLY:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IMUL);
                else if (currentContext == MovaType.DECIMAL) mv.visitLdcInsn(Opcodes.DMUL);
                break;
            case DIVIDE:
                if (currentContext == MovaType.INTEGER) mv.visitInsn(Opcodes.IDIV);
                else if (currentContext == MovaType.DECIMAL) mv.visitLdcInsn(Opcodes.DDIV);
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
