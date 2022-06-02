package smthelusive.mova.v1.visitors

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import smthelusive.mova.v1.Compiler
import smthelusive.mova.domain.MovaType
import smthelusive.mova.domain.MovaValue
import smthelusive.mova.gen.MovaLexer
import smthelusive.mova.gen.MovaParser
import smthelusive.mova.v1.visitors.CommandVisitor
import spock.lang.Specification

class CommandVisitorTest extends Specification {
    CommandVisitor commandVisitor = new CommandVisitor()
    String intIdentifier = "iTest"
    String decimalIdentifier = "dTest"
    String incrementCommand = "increment"
    String decrementCommand = "decrement"

    def "command visitor should assign value to identifier"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString(decimalIdentifier + " is 4 plus 1,0"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.assignment())
        Optional<MovaValue> result = Compiler.getVariableValue(decimalIdentifier)

        then:
        result.get().getStringValue() equals("5.0")
    }

    def "command visitor should increment the integer and decimal value"() {
        when:
        Compiler.registerVariable(intIdentifier, new MovaValue(MovaType.INTEGER, "4"))
        MovaLexer lexer = new MovaLexer(CharStreams.fromString(incrementCommand + " " + intIdentifier))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.increment())
        Optional<MovaValue> intResult = Compiler.getVariableValue(intIdentifier)

        then:
        intResult.get().getStringValue() equals("5")

        when:
        Compiler.registerVariable(decimalIdentifier, new MovaValue(MovaType.DECIMAL, "4,2"))
        lexer = new MovaLexer(CharStreams.fromString(incrementCommand + " " + decimalIdentifier))
        parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.increment())
        Optional<MovaValue> decResult = Compiler.getVariableValue(decimalIdentifier)

        then:
        decResult.get().getStringValue() equals("5.2")
    }

    def "command visitor should decrement the integer and decimal value"() {
        when:
        Compiler.registerVariable(intIdentifier, new MovaValue(MovaType.INTEGER, "4"))
        MovaLexer lexer = new MovaLexer(CharStreams.fromString(decrementCommand + " " + intIdentifier))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.decrement())
        Optional<MovaValue> intResult = Compiler.getVariableValue(intIdentifier)

        then:
        intResult.get().getStringValue() equals("3")

        when:
        Compiler.registerVariable(decimalIdentifier, new MovaValue(MovaType.DECIMAL, "4,2"))
        lexer = new MovaLexer(CharStreams.fromString(decrementCommand + " " + decimalIdentifier))
        parser = new MovaParser(new CommonTokenStream(lexer))
        commandVisitor.visit(parser.decrement())
        Optional<MovaValue> decResult = Compiler.getVariableValue(decimalIdentifier)

        then:
        decResult.get().getStringValue() equals("3.2")
    }
}
