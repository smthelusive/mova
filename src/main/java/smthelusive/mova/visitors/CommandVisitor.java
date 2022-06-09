package smthelusive.mova.visitors;

import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class CommandVisitor extends MovaParserBaseVisitor<Void> {
    private final ExpressionVisitor expressionVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private static final String HEROYAM_SLAVA = "Героям Слава!";

    public CommandVisitor(MovaProgramVisitor movaProgramVisitor) {
        smartByteCodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
        expressionVisitor = movaProgramVisitor.getExpressionVisitor();
    }

    @Override
    public Void visitAssignment(MovaParser.AssignmentContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        expressionVisitor.visitAllKindsExpression(allKindsExpression);
        smartByteCodeGenerator.addVariableAssignment(identifier);
        return null;
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        expressionVisitor.visitAllKindsExpression(allKindsExpression);
        smartByteCodeGenerator.printlnValueOnTopOfOpStack();
        return null;
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
        return null;
    }
}
