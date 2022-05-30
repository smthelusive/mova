package smthelusive.mova;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import smthelusive.mova.domain.ByteCodeVariable;
import smthelusive.mova.domain.MovaValue;

import java.util.HashMap;
import java.util.Map;

public class ByteCodeGenerator {

    private final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    private int variableIndex = 1;
    private MethodVisitor mv;
    private final Map<String, ByteCodeVariable> byteCodeVariables = new HashMap<>();

    /**
     * start the class definition with the given class name
     * @param name the name of the program
     */
    public void init(String name) {
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
    }

    /***
     * Create a bytecode piece that stores the variable
     * @param identifier to be later found at invoking other commands, like printing etc.
     * @param movaValue which stores the mova type and value of the variable
     */
    public void addVariableAssignment(String identifier, MovaValue movaValue) {
        ByteCodeVariable byteCodeVariable = new ByteCodeVariable();
        int opcode = 0;
        switch (movaValue.getMovaType()) {
            case STRING:
                byteCodeVariable.setValue(movaValue.getStringValue());
                byteCodeVariable.setDescriptor("Ljava/lang/String;");
                opcode = Opcodes.ASTORE;
                break;
            case INTEGER:
                byteCodeVariable.setValue(movaValue.getIntegerValue());
                byteCodeVariable.setDescriptor("I");
                opcode = Opcodes.ISTORE;
                break;
            case DECIMAL:
                byteCodeVariable.setValue(movaValue.getDoubleValue());
                byteCodeVariable.setDescriptor("D");
                opcode = Opcodes.DSTORE;
                break;
        }
        byteCodeVariable.setId(variableIndex);

        mv.visitLdcInsn(byteCodeVariable.getValue());
        mv.visitVarInsn(opcode, variableIndex);

        byteCodeVariables.put(identifier, byteCodeVariable);
        variableIndex++;
    }

    /***
     * Reference the variable inside the bytecode and print its value
     * @param identifier the name of the variable in the mova code
     */
    public void addPrintlnByIdentifier(String identifier) {
        ByteCodeVariable byteCodeVariable = byteCodeVariables.get(identifier);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ALOAD, byteCodeVariable.getId());
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                String.format("(%s)V", byteCodeVariable.getDescriptor()), false);
    }

    /***
     * Print (already pre-evaluated) string
     * @param string to be printed
     */
    public void addPrintlnByString(String string) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(string);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
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
