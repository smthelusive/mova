package smthelusive.mova.visitor;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.util.OperationsUtil;
import smthelusive.mova.Compiler;

import java.util.Optional;

public class ExpressionVisitor extends MovaParserBaseVisitor<MovaValue> {

    private final Compiler compiler;
    public ExpressionVisitor(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public MovaValue visitExpression(MovaParser.ExpressionContext ctx) {
        int childrenCount = ctx.getChildCount();
        // just a single value
        if (childrenCount == 1) {
            return visit(ctx.getChild(0));
        }
        else if (childrenCount == 3) {
            // visiting expression inside the parentheses. 0 = '(', 1 = expression, 2 = ')'
            if (ctx.getChild(1).getChildCount() == 3) {
                return visit(ctx.getChild(1));
            }
            // normal expression 0 = value, 1 = action, 2 = value:
            else {
                TerminalNode actionNode = (TerminalNode)ctx.getChild(1);
                MovaValue left = visit(ctx.getChild(0));
                MovaAction action = OperationsUtil.convertedAction(
                        MovaParser.VOCABULARY.getSymbolicName((actionNode.getSymbol().getType())));
                MovaValue right = visit(ctx.getChild(2));
                return OperationsUtil.calculate(left, right, action);
            }
        }
        // todo exception or something
        return null;
    }

    @Override
    public MovaValue visitValue(MovaParser.ValueContext ctx) {
        MovaValue movaValue = new MovaValue();
        if (ctx.DECIMAL() != null) movaValue.setMovaType(MovaType.DECIMAL);
        if (ctx.INTEGER() != null) movaValue.setMovaType(MovaType.INTEGER);
        if (ctx.STRING() != null) movaValue.setMovaType(MovaType.STRING);
        movaValue.setRawValue(ctx.getText());

        if (ctx.IDENTIFIER() != null) {
            Optional<String> maybeVariableValue = compiler.getVariableValue(ctx.getText());
            if (maybeVariableValue.isPresent()) {
                movaValue.setRawValue(maybeVariableValue.get());
            } else {
                movaValue.setMovaType(MovaType.STRING);
                movaValue.setRawValue(ctx.getText());
            }
        }
        return movaValue;
    }
}
