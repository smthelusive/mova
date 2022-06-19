package smthelusive.mova.visitors;

import smthelusive.mova.ByteCodeGenerator;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class CommandVisitor extends MovaParserBaseVisitor<Void> {
    private final ExpressionVisitor expressionVisitor;
    private final ByteCodeGenerator byteCodeGenerator;
    private static final String HEROYAM_SLAVA = "Героям Слава!";

    public CommandVisitor(MovaProgramVisitor movaProgramVisitor) {
        byteCodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
        expressionVisitor = movaProgramVisitor.getExpressionVisitor();
    }

    @Override
    public Void visitAssignment(MovaParser.AssignmentContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        expressionVisitor.visitAllKindsExpression(allKindsExpression);
        byteCodeGenerator.addVariableAssignment(identifier);
        return null;
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        expressionVisitor.visitAllKindsExpression(allKindsExpression);
        byteCodeGenerator.printlnValueOnTopOfOpStack();
        return null;
    }

    @Override
    public Void visitDecrement(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        byteCodeGenerator.decrementVariable(identifier);
        return null;
    }

    @Override
    public Void visitIncrement(MovaParser.IncrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        byteCodeGenerator.incrementVariable(identifier);
        return null;
    }

    @Override
    public Void visitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx) {
        byteCodeGenerator.pushValueToOpStack(new MovaValue(MovaType.STRING, HEROYAM_SLAVA));
        byteCodeGenerator.printlnValueOnTopOfOpStack();
        return null;
    }
}
