package smthelusive.mova.visitors;

import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class MovaProgramVisitor extends MovaParserBaseVisitor<Void> {

    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;
    private final ExpressionBasedConditionsVisitor conditionalLoopVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;

    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }

    public CommandVisitor getCommandVisitor() {
        return commandVisitor;
    }

    public ExpressionBasedConditionsVisitor getConditionalLoopVisitor() {
        return conditionalLoopVisitor;
    }

    public SmartByteCodeGenerator getSmartByteCodeGenerator() {
        return smartByteCodeGenerator;
    }

    public MovaProgramVisitor(SmartByteCodeGenerator smartByteCodeGenerator) {
        this.smartByteCodeGenerator = smartByteCodeGenerator;
        expressionVisitor = new ExpressionVisitor(this);
        commandVisitor = new CommandVisitor(this);
        conditionalLoopVisitor = new ExpressionBasedConditionsVisitor(this);
    }

    @Override
    public Void visitValidStructure(MovaParser.ValidStructureContext ctx) {
        if (ctx.conditional() != null) conditionalLoopVisitor.visitConditional(ctx.conditional());
        if (ctx.command() != null) commandVisitor.visitCommand(ctx.command());
//        if (ctx.slavaUkraini() != null) commandVisitor.visitSlavaUkraini(ctx.slavaUkraini());
        ctx.validStructure().forEach(this::visitValidStructure);
        return null;
    }
}
