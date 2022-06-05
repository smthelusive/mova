package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class ExpressionBasedConditionsVisitor extends MovaParserBaseVisitor<Void> {

    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;

    public ExpressionBasedConditionsVisitor(SmartByteCodeGenerator smartByteCodeGenerator, CommandVisitor commandVisitor,
                                  ExpressionVisitor expressionVisitor) {
        this.smartByteCodeGenerator = smartByteCodeGenerator;
        this.commandVisitor = commandVisitor;
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Void visitBlock(MovaParser.BlockContext ctx) {
        ctx.command().forEach(commandVisitor::visitCommand);
        return null;
    }

    @Override
    public Void visitCondition(MovaParser.ConditionContext ctx) {
        if (ctx.getChildCount() == 1) { // just some expression that can be greater than 0
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
            smartByteCodeGenerator.convertStackTopToBooleanValue();
        } else if (ctx.getChildCount() == 3) {
            if (ctx.LPAREN() != null) { // (condition)
                visitCondition(ctx.condition(0));
            } else if (ctx.OR() != null) { // condition OR condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.performBytecodeOperation(MovaAction.PLUS);
                smartByteCodeGenerator.incrementLastStackValue(MovaType.INTEGER);
            } else if (ctx.AND() != null) { // condition AND condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.performBytecodeOperation(MovaAction.PLUS);
            } else if (ctx.allKindsExpression().size() == 2) { // allKindsExpression ? allkindsExpression
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(1));
                smartByteCodeGenerator.calculateConditionalExpression(ctx);
                smartByteCodeGenerator.convertStackTopToBooleanValue();
            }
        }
        return null;
    }

    @Override
    public Void visitConditional(MovaParser.ConditionalContext ctx) {
        smartByteCodeGenerator.createNewLabel();
        smartByteCodeGenerator.createEndLabel();
        visitCondition(ctx.condition(0));
        smartByteCodeGenerator.jumpIFGT();
        smartByteCodeGenerator.gotoEnd();
        smartByteCodeGenerator.writeByteCodeLabel();
        visitBlock(ctx.block(0));
        smartByteCodeGenerator.writeByteCodeEndLabel();
        return null;
    }

    @Override
    public Void visitLoop(MovaParser.LoopContext ctx) {
        // todo
        return null;
    }
}
