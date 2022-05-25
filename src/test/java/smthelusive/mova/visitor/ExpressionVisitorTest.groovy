package smthelusive.mova.visitor

import smthelusive.mova.gen.MovaLexer
import smthelusive.mova.gen.MovaParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import smthelusive.mova.domain.MovaValue
import spock.lang.Specification

class ExpressionVisitorTest extends Specification {
    ExpressionVisitor expressionVisitor = new ExpressionVisitor();

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
        // todo fix give priority to multiplication and division
        when:
        MovaLexer lexer = new MovaLexer(CharStreams
                .fromString("(1,5 + ((((2 * 186 + 2) - 42) + ((11 + 1)))) + ((5 + 66 * 3) + 1) + (1 + ((150 - 10) - 1)) + 1)"))
        MovaParser parser = new MovaParser(new CommonTokenStream(lexer))
        MovaValue result = expressionVisitor.visit(parser.expression())

        then:
        result.getStringValue() equals("690.5")
    }

}
