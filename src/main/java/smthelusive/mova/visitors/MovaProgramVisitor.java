package smthelusive.mova.visitors;

import smthelusive.mova.ByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class MovaProgramVisitor extends MovaParserBaseVisitor<Void> {

    private final ExpressionVisitor expressionVisitor;
    private final CommandVisitor commandVisitor;
    private final ConditionalLoopVisitor conditionalLoopVisitor;
    private final ByteCodeGenerator byteCodeGenerator;

    public ExpressionVisitor getExpressionVisitor() {
        return expressionVisitor;
    }

    public ByteCodeGenerator getSmartByteCodeGenerator() {
        return byteCodeGenerator;
    }

    public MovaProgramVisitor(ByteCodeGenerator byteCodeGenerator) {
        this.byteCodeGenerator = byteCodeGenerator;
        expressionVisitor = new ExpressionVisitor(this);
        commandVisitor = new CommandVisitor(this);
        conditionalLoopVisitor = new ConditionalLoopVisitor(this);
    }

    @Override
    public Void visitValidStructure(MovaParser.ValidStructureContext ctx) {
        if (ctx.conditional() != null) conditionalLoopVisitor.visitConditional(ctx.conditional());
        if (ctx.loop() != null) conditionalLoopVisitor.visitLoop(ctx.loop());
        if (ctx.command() != null) commandVisitor.visitCommand(ctx.command());
        ctx.validStructure().forEach(this::visitValidStructure);
        return null;
    }
}
