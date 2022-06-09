package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class ConditionalLoopVisitor extends MovaParserBaseVisitor<Void> {

    private final MovaProgramVisitor movaProgramVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;

    public ConditionalLoopVisitor(MovaProgramVisitor movaProgramVisitor) {
        this.movaProgramVisitor = movaProgramVisitor;
        this.smartByteCodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
        this.commandVisitor = movaProgramVisitor.getCommandVisitor();
        this.expressionVisitor = movaProgramVisitor.getExpressionVisitor();
    }

    @Override
    public Void visitCondition(MovaParser.ConditionContext ctx) {
        if (ctx.getChildCount() == 1) { // just some expression that can be greater than 0
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
            smartByteCodeGenerator.writeByteCodeLabel();
            smartByteCodeGenerator.createNewLabel();
            smartByteCodeGenerator.jumpIFGT();
        } else {
            if (ctx.LPAREN() != null) { // (condition)
                visitCondition(ctx.condition(0));
            } else if (ctx.OR() != null) { // condition OR condition
                visitCondition(ctx.condition(0));
                smartByteCodeGenerator.writeByteCodeLabel();
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.writeByteCodeLabel();
                smartByteCodeGenerator.createNewLabel();
                smartByteCodeGenerator.gotoEnd();
            } else if (ctx.AND() != null) { // condition AND condition
                visitCondition(ctx.condition(0));
                smartByteCodeGenerator.writeByteCodeLabel();
                smartByteCodeGenerator.gotoEnd();
                smartByteCodeGenerator.createNewLabel();
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.writeByteCodeLabel();
                smartByteCodeGenerator.createNewLabel();
            } else if (ctx.allKindsExpression().size() == 2) { // allKindsExpression ? allkindsExpression
                smartByteCodeGenerator.createNewLabel();
                conditionJump(ctx);
            }
        }
        return null;
    }

    private void conditionJump(MovaParser.ConditionContext ctx) {
        TerminalNode comparison = (TerminalNode)ctx.getChild(1);
        String comparisonKeyword = MovaParser.VOCABULARY.getSymbolicName(comparison.getSymbol().getType());
        expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(1));
        expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
        smartByteCodeGenerator.createNewLabel();
        smartByteCodeGenerator.performConditionalJump(comparisonKeyword, ctx.NOT(0) != null);
    }

    @Override
    public Void visitConditional(MovaParser.ConditionalContext ctx) {
        smartByteCodeGenerator.createNewLabel();
        smartByteCodeGenerator.createEndLabel();
        visitCondition(ctx.condition());
        smartByteCodeGenerator.gotoEnd();
        smartByteCodeGenerator.writeByteCodeLabel();
        movaProgramVisitor.visitValidStructure(ctx.validStructure(0));
        smartByteCodeGenerator.writeByteCodeEndLabel();
        return null;
    }

    @Override
    public Void visitLoop(MovaParser.LoopContext ctx) {
        // todo
        return null;
    }
}
