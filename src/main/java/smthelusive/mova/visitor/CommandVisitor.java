package smthelusive.mova.visitor;

import smthelusive.mova.ByteCodeGenerator;
import smthelusive.mova.Compiler;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.util.OperationsUtil;

public class CommandVisitor extends MovaParserBaseVisitor<Void> {
    private final ExpressionVisitor expressionVisitor = new ExpressionVisitor();
    private final ByteCodeGenerator bytecodeGenerator;
    private static final String HEROYAM_SLAVA = "Героям Слава!";

    public CommandVisitor(ByteCodeGenerator bytecodeGenerator) {
        this.bytecodeGenerator = bytecodeGenerator;
    }

    @Override
    public Void visitAssignment(MovaParser.AssignmentContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        MovaParser.ExpressionContext expressionContext = ctx.expression();
        MovaValue movaValue = expressionVisitor.visitExpression(expressionContext);
        Compiler.registerVariable(identifier, movaValue);
        bytecodeGenerator.addVariableAssignment(identifier, movaValue);
        return super.visitAssignment(ctx);
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        MovaParser.ExpressionContext expressionContext = ctx.expression();
        MovaValue movaValue = expressionVisitor.visitExpression(expressionContext);
        bytecodeGenerator.addPrintlnByString(movaValue.getStringValue());
        return super.visitOutput(ctx);
    }

    @Override
    public Void visitDecrement(MovaParser.DecrementContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        Compiler.getVariableValue(identifier)
                .ifPresent(movaValue -> Compiler.registerVariable(identifier, OperationsUtil.decrement(movaValue)));
        // todo add to the instructions queue
        return super.visitDecrement(ctx);
    }

    @Override
    public Void visitIncrement(MovaParser.IncrementContext ctx) {
        String identifier = ctx.getChild(1).getText();
        Compiler.getVariableValue(identifier)
                .ifPresent(movaValue -> Compiler.registerVariable(identifier, OperationsUtil.increment(movaValue)));
        // todo add to the instructions queue
        return super.visitIncrement(ctx);
    }

    @Override
    public Void visitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx) {
        bytecodeGenerator.addPrintlnByString(HEROYAM_SLAVA);
        return super.visitSlavaUkraini(ctx);
    }
}
