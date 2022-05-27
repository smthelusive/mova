package smthelusive.mova.visitor

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import smthelusive.mova.Compiler
import smthelusive.mova.domain.MovaType
import smthelusive.mova.domain.MovaValue
import smthelusive.mova.gen.MovaLexer
import smthelusive.mova.gen.MovaParser
import spock.lang.Specification

class CommandVisitorTest extends Specification {
    CommandVisitor commandVisitor = new CommandVisitor()

    def "command visitor should assign value to identifier"() {
        when:
        String identifier = "test"
        MovaLexer lexer = new MovaLexer(CharStreams.fromString(identifier + " is 4 plus 1,0"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.assignment())
        Optional<MovaValue> result = Compiler.getVariableValue(identifier)

        then:
        result.get().getStringValue() equals("5.0")
    }

    def "command visitor should increment the integer and decimal value"() {
        when:
        String intIdentifier = "iTest"
        Compiler.registerVariable(intIdentifier, new MovaValue(MovaType.INTEGER, "4"))
        MovaLexer lexer = new MovaLexer(CharStreams.fromString( "increment " + intIdentifier))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.increment())
        Optional<MovaValue> intResult = Compiler.getVariableValue(intIdentifier)

        then:
        intResult.get().getStringValue() equals("5")

        when:
        String decimalIdentifier = "dTest"
        Compiler.registerVariable(decimalIdentifier, new MovaValue(MovaType.DECIMAL, "4,2"))
        lexer = new MovaLexer(CharStreams.fromString( "increment " + decimalIdentifier))
        parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.increment())
        Optional<MovaValue> decResult = Compiler.getVariableValue(decimalIdentifier)

        then:
        decResult.get().getStringValue() equals("5.2")
    }

    def "command visitor should decrement the integer and decimal value"() {
        when:
        String intIdentifier = "iTest"
        Compiler.registerVariable(intIdentifier, new MovaValue(MovaType.INTEGER, "4"))
        MovaLexer lexer = new MovaLexer(CharStreams.fromString( "decrement " + intIdentifier))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.decrement())
        Optional<MovaValue> intResult = Compiler.getVariableValue(intIdentifier)

        then:
        intResult.get().getStringValue() equals("3")

        when:
        String decimalIdentifier = "dTest"
        Compiler.registerVariable(decimalIdentifier, new MovaValue(MovaType.DECIMAL, "4,2"))
        lexer = new MovaLexer(CharStreams.fromString( "decrement " + decimalIdentifier))
        parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.decrement())
        Optional<MovaValue> decResult = Compiler.getVariableValue(decimalIdentifier)

        then:
        decResult.get().getStringValue() equals("3.2")
    }
}
