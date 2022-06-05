package smthelusive.mova.visitors;

import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class MovaProgramVisitor extends MovaParserBaseVisitor<Void> {

    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;
    private final ExpressionBasedConditionsVisitor conditionalLoopVisitor;

    public MovaProgramVisitor(SmartByteCodeGenerator smartByteCodeGenerator) {
        expressionVisitor = new ExpressionVisitor(smartByteCodeGenerator);
        commandVisitor = new CommandVisitor(smartByteCodeGenerator, expressionVisitor);
        conditionalLoopVisitor = new ExpressionBasedConditionsVisitor(smartByteCodeGenerator, commandVisitor, expressionVisitor);
    }

    @Override
    public Void visitValidStructure(MovaParser.ValidStructureContext ctx) {
        if (ctx.conditional() != null) conditionalLoopVisitor.visitConditional(ctx.conditional());
        if (ctx.command() != null) commandVisitor.visitCommand(ctx.command());
        if (ctx.slavaUkraini() != null) commandVisitor.visitSlavaUkraini(ctx.slavaUkraini());
        return null;
    }
}
