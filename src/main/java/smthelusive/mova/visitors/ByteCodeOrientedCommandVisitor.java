package smthelusive.mova.visitors;

import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

import java.util.Optional;

public class ByteCodeOrientedCommandVisitor extends MovaParserBaseVisitor<Void> {
    private final ByteCodeOrientedExpressionVisitor expressionVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private static final String HEROYAM_SLAVA = "Героям Слава!";

    public ByteCodeOrientedCommandVisitor(SmartByteCodeGenerator smartByteCodeGenerator) {
        this.smartByteCodeGenerator = smartByteCodeGenerator;
        expressionVisitor = new ByteCodeOrientedExpressionVisitor(smartByteCodeGenerator);
    }

    @Override
    public Void visitAssignment(MovaParser.AssignmentContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        visitAllKindsExpression(allKindsExpression);
        smartByteCodeGenerator.addVariableAssignment(identifier);
        return super.visitAssignment(ctx);
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        visitAllKindsExpression(allKindsExpression);
        smartByteCodeGenerator.printlnValueOnTopOfOpStack();
        return null;
    }

    @Override
    public Void visitAllKindsExpression(MovaParser.AllKindsExpressionContext ctx) {
        Optional.ofNullable(ctx.expression()).ifPresent(expressionVisitor::visitExpression);
        Optional.ofNullable(ctx.increment()).ifPresent(this::visitIncrementForReuse);
        Optional.ofNullable(ctx.decrement()).ifPresent(this::visitDecrementForReuse);
        return null;
    }

    // todo refactor
    public void visitDecrementForReuse(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartByteCodeGenerator.decrementVariable(identifier);
        smartByteCodeGenerator.loadVariableToOpStack(identifier);
    }

    public void visitIncrementForReuse(MovaParser.IncrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartByteCodeGenerator.incrementVariable(identifier);
        smartByteCodeGenerator.loadVariableToOpStack(identifier);
    }

    @Override
    public Void visitDecrement(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartByteCodeGenerator.decrementVariable(identifier);
        return null;
    }

    @Override
    public Void visitIncrement(MovaParser.IncrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        smartByteCodeGenerator.incrementVariable(identifier);
        return null;
    }

    @Override
    public Void visitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx) {
        smartByteCodeGenerator.pushValueToOpStack(new MovaValue(MovaType.STRING, HEROYAM_SLAVA));
        smartByteCodeGenerator.printlnValueOnTopOfOpStack();
        return super.visitSlavaUkraini(ctx);
    }
}
