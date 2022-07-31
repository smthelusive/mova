package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.ByteCodeGenerator;
import smthelusive.mova.Compiler;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

import javax.swing.text.html.Option;
import java.util.Optional;

public class ExpressionVisitor extends MovaParserBaseVisitor<Void> {

    private final ByteCodeGenerator bytecodeGenerator;
    private static final String ARGUMENT_PREFIX = "arg";

    public ExpressionVisitor(MovaProgramVisitor movaProgramVisitor) {
        this.bytecodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
    }

    @Override
    public Void visitExpression(MovaParser.ExpressionContext ctx) {
        int childrenCount = ctx.getChildCount();
        // just a single value
        if (childrenCount == 1) {
            visit(ctx.getChild(0));
        }
        else if (childrenCount == 3) {
            // visiting expression inside the parentheses. 0 = '(', 1 = expression, 2 = ')'
            if (ctx.getChild(1).getChildCount() == 3) {
                visit(ctx.getChild(1));
            }
            // normal expression 0 = value, 1 = action, 2 = value:
            else {
                visit(ctx.getChild(0));
                visit(ctx.getChild(2));
                TerminalNode actionNode = (TerminalNode)ctx.getChild(1);
                MovaAction action = convertedAction(
                        MovaParser.VOCABULARY.getSymbolicName((actionNode.getSymbol().getType())));
                bytecodeGenerator.performBytecodeOperation(action);
            }
        }
        return null;
    }

    @Override
    public Void visitValue(MovaParser.ValueContext ctx) {
        if (ctx.ARGUMENT() != null) {
            String arg = ctx.ARGUMENT().getText();
            int argIndex = Integer.parseInt(arg.replace(ARGUMENT_PREFIX, ""));
            bytecodeGenerator.loadArgumentByIndex(argIndex);
            return null;
        }

        if (ctx.IDENTIFIER() != null) {
            bytecodeGenerator.loadVariableToOpStack(ctx.getText());
            return null;
        }

        MovaValue movaValue = new MovaValue();
        if (ctx.INTEGER() != null) {
            movaValue.setMovaType(MovaType.INTEGER);
            movaValue.setRawValue(ctx.getText());
        }
        else if (ctx.DECIMAL() != null) {
            movaValue.setMovaType(MovaType.DECIMAL);
            movaValue.setRawValue(ctx.getText());
        }
        else if (ctx.STRING() != null) {
            movaValue.setMovaType(MovaType.STRING);
            movaValue.setRawValue(clean(ctx.getText()));
        }
        bytecodeGenerator.pushValueToOpStack(movaValue);
        return null;
    }

    private String clean(String quotedString) {
        return quotedString.replaceAll("^\"|\"$", "");
    }

    private MovaAction convertedAction(String rawAction) {
        return switch (rawAction) {
            case "PLUS" -> MovaAction.PLUS;
            case "MINUS" -> MovaAction.MINUS;
            case "MULTIPLY" -> MovaAction.MULTIPLY;
            case "DIVIDE" -> MovaAction.DIVIDE;
            case "PREFIX" -> MovaAction.PREFIX;
            case "SUFFIX" -> MovaAction.SUFFIX;
            default -> MovaAction.WITH;
        };
    }

    @Override
    public Void visitAllKindsExpression(MovaParser.AllKindsExpressionContext ctx) {
        Optional.ofNullable(ctx.expression()).ifPresent(this::visitExpression);
        Optional.ofNullable(ctx.increment()).ifPresent(this::visitIncrementForReuse);
        Optional.ofNullable(ctx.decrement()).ifPresent(this::visitDecrementForReuse);
        Optional.ofNullable(ctx.reverse()).ifPresent(this::visitReverseForReuse);
        return null;
    }

    /***
     * decrements the value of a variable and puts
     * the result on top of the stack for further use
     * @param ctx decrement context
     */
    public void visitDecrementForReuse(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        bytecodeGenerator.decrementVariable(identifier);
        bytecodeGenerator.loadVariableToOpStack(identifier);
    }

    /***
     * increments the value of a variable and puts
     * the result on top of the stack for further use
     * @param ctx increment context
     */
    public void visitIncrementForReuse(MovaParser.IncrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        bytecodeGenerator.incrementVariable(identifier);
        bytecodeGenerator.loadVariableToOpStack(identifier);
    }

    public void visitReverseForReuse(MovaParser.ReverseContext ctx) {
        if (Optional.ofNullable(ctx.IDENTIFIER()).isPresent()) {
            String identifier = ctx.IDENTIFIER().getText();
            bytecodeGenerator.loadVariableToOpStack(identifier);
            bytecodeGenerator.reverseLastStackValue();
        } else if (Optional.ofNullable(ctx.STRING()).isPresent()) {
            MovaValue movaValue = new MovaValue(MovaType.STRING, clean(ctx.STRING().getText()));
            bytecodeGenerator.pushValueToOpStack(movaValue);
            bytecodeGenerator.reverseLastStackValue();
        }
    }
}
