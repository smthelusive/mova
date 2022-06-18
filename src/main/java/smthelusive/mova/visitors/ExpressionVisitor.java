package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.util.OperationsUtil;

import java.util.Optional;

public class ExpressionVisitor extends MovaParserBaseVisitor<Void> {

    private final SmartByteCodeGenerator smartBytecodeGenerator;
    private static final String ARGUMENT_PREFIX = "arg";

    public ExpressionVisitor(MovaProgramVisitor movaProgramVisitor) {
        this.smartBytecodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
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
                TerminalNode actionNode = (TerminalNode)ctx.getChild(1);
                MovaAction action = OperationsUtil.convertedAction(
                        MovaParser.VOCABULARY.getSymbolicName((actionNode.getSymbol().getType())));
                boolean isArithmeticAction = isArithmeticAction(action);
                if (isArithmeticAction) smartBytecodeGenerator.switchContextToDecimal();
                smartBytecodeGenerator.lockContext();
                visit(ctx.getChild(2));
//                if (isArithmeticAction) smartBytecodeGenerator.switchContextToDecimal();
                smartBytecodeGenerator.performBytecodeOperation(action);
            }
        }
        return null;
    }

    private boolean isArithmeticAction(MovaAction action) {
        return action.equals(MovaAction.MINUS) ||
                action.equals(MovaAction.PLUS) ||
                action.equals(MovaAction.MULTIPLY) ||
                action.equals(MovaAction.DIVIDE);
    }

    @Override
    public Void visitValue(MovaParser.ValueContext ctx) {
        if (ctx.ARGUMENT() != null) {
            String arg = ctx.ARGUMENT().getText();
            int argIndex = Integer.parseInt(arg.replace(ARGUMENT_PREFIX, ""));
            smartBytecodeGenerator.loadArgumentByIndex(argIndex);
            return null;
        }

        if (ctx.IDENTIFIER() != null) {
            smartBytecodeGenerator.loadVariableToOpStack(ctx.getText());
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
            movaValue.setRawValue(OperationsUtil.clean(ctx.getText()));
        }
        smartBytecodeGenerator.pushValueToOpStack(movaValue);
        return null;
    }

    @Override
    public Void visitAllKindsExpression(MovaParser.AllKindsExpressionContext ctx) {
        Optional.ofNullable(ctx.expression()).ifPresent(this::visitExpression);
        Optional.ofNullable(ctx.increment()).ifPresent(this::visitIncrementForReuse);
        Optional.ofNullable(ctx.decrement()).ifPresent(this::visitDecrementForReuse);
        return null;
    }

    public void visitDecrementForReuse(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartBytecodeGenerator.decrementVariable(identifier);
        smartBytecodeGenerator.loadVariableToOpStack(identifier);
    }

    public void visitIncrementForReuse(MovaParser.IncrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartBytecodeGenerator.incrementVariable(identifier);
        smartBytecodeGenerator.loadVariableToOpStack(identifier);
    }
}
