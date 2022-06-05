package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class ConditionalLoopVisitor extends MovaParserBaseVisitor<Void> {

    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;

    public ConditionalLoopVisitor(SmartByteCodeGenerator smartByteCodeGenerator, CommandVisitor commandVisitor,
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
            smartByteCodeGenerator.createNewLabel();
            smartByteCodeGenerator.jumpIFGT();
        } else if (ctx.getChildCount() == 3) {
            if (ctx.LPAREN() != null) { // (condition)
                visitCondition(ctx.condition(0));
            } else if (ctx.OR() != null) { // condition OR condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
            } else if (ctx.AND() != null) { // condition AND condition
                visitCondition(ctx.condition(0));
                smartByteCodeGenerator.writeByteCodeLabel();
                visitCondition(ctx.condition(1));
            } else if (ctx.allKindsExpression().size() == 2) { // allKindsExpression ? allkindsExpression
                conditionJump(ctx);
            }
        }
        return null;
    }

    private void conditionJump(MovaParser.ConditionContext ctx) {
        TerminalNode comparison = (TerminalNode)ctx.getChild(1);
        String comparisonKeyword = MovaParser.VOCABULARY.getSymbolicName(comparison.getSymbol().getType());
        expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
        expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(1));
        smartByteCodeGenerator.createNewLabel();
        smartByteCodeGenerator.performConditionalJump(comparisonKeyword, ctx.NOT(0) != null);
    }

    @Override
    public Void visitConditional(MovaParser.ConditionalContext ctx) {
        smartByteCodeGenerator.createEndLabel();
        visitCondition(ctx.condition(0));
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
