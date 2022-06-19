package smthelusive.mova;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import smthelusive.mova.gen.MovaLexer;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.visitors.MovaProgramVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/***
 *  Compiler does the following:
 *  - reads the file
 *  - builds the parse tree using the existing antlr parser
 *  - visits the main program node which through the tree of visitors generates the bytecode representation of a program
 *  - writes the bytecode to a class file
 */
public class SmartCompiler {
    public static final SmartByteCodeGenerator smartByteCodeGenerator = new SmartByteCodeGenerator();
    private final static String MOVA_VAR_RESERVED = "mova_var";
    private static int movaInternalVariableNumber = 0;

    public static String getNewMovaInternalVariableIdentifier() {
        return MOVA_VAR_RESERVED + movaInternalVariableNumber++;
    }

    public static void main(String[] args) {
        Arrays.stream(args).forEach(inputFile -> {
            try {
                MovaLexer lexer = new MovaLexer(CharStreams.fromFileName(inputFile));
                MovaParser parser = new MovaParser(new CommonTokenStream(lexer));
                String[] filename = inputFile.split("/");
                String programName = filename[filename.length - 1].replace(".mova", "");
                smartByteCodeGenerator.init(programName);
                MovaProgramVisitor movaProgramVisitor = new MovaProgramVisitor(smartByteCodeGenerator);
                movaProgramVisitor.visit(parser.validProgram());
                byte[] bytes = smartByteCodeGenerator.cleanCloseProgram();
                FileOutputStream stream = new FileOutputStream(programName + ".class");
                stream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
