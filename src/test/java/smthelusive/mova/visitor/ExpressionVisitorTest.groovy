package smthelusive.mova.visitor

import smthelusive.mova.Compiler
import smthelusive.mova.gen.MovaLexer
import smthelusive.mova.gen.MovaParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import smthelusive.mova.domain.MovaValue
import spock.lang.Specification

// todo fix failures on dots at decimals
class ExpressionVisitorTest extends Specification {
    Compiler compiler = new Compiler()
    ExpressionVisitor expressionVisitor = new ExpressionVisitor(compiler)

    def "expression visitor calculates one level expression with double context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("4 + 1,0"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("5.0")
    }

    def "expression visitor calculates 2-level expression with double context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("1 + 4,0 - 1,0"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("4.0")
    }

    def "expression visitor calculates 2-level expression with parentheses double context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("2 * (4,0 - 3)"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("2.0")
    }

    def "expression visitor calculates complicated nested expression double context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams
                .fromString("(1,5 + ((((2 * 186 + 2) - 42) + ((11 + 1)))) + ((5 + 66 * 3) + 1) + (1 + ((150 - 10) - 1)) + 1)"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("690.5")
    }

    def "expression visitor calculates complicated expression with integer context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams
                .fromString("5 * (2 + 3) - 16 / (8 + 152) * 467"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("25")
    }

    def "expression visitor does the suffixing in string context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("\"hello \" with \"world\""))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("hello world")
    }

    def "expression visitor does the prefixing in string context"() {
        when:
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("world prefixed with \"hello \" prefixed with \"wow, \""))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("wow, hello world")
    }

    def "expression visitor evaluates the registered variable to process expression"() {
        when:
        compiler.registerVariable("world", "universe")
        MovaLexer lexer = new MovaLexer(CharStreams.fromString("world prefixed with \"hello \" prefixed with \"wow, \""))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("wow, hello universe")
    }
}
