package smthelusive.mova.v1.visitors;

import smthelusive.mova.v1.ByteCodeGenerator;
import smthelusive.mova.v1.Compiler;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.util.OperationsUtil;

import java.util.Optional;

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
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        MovaValue movaValue = expressionVisitor.visitAllKindsExpression(allKindsExpression);
        Compiler.registerVariable(identifier, movaValue);
        bytecodeGenerator.addVariableAssignment(identifier, movaValue);
        return super.visitAssignment(ctx);
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        MovaParser.AllKindsExpressionContext allKindsExpression = ctx.allKindsExpression();
        MovaValue movaValue = expressionVisitor.visitAllKindsExpression(allKindsExpression);
        bytecodeGenerator.addPrintlnByString(movaValue.getStringValue());
        return super.visitOutput(ctx);
    }

    @Override
    public Void visitAllKindsExpression(MovaParser.AllKindsExpressionContext ctx) {
        Optional<MovaParser.ExpressionContext> expressionContext = Optional.ofNullable(ctx.expression());
        expressionContext.ifPresent(expressionVisitor::visitExpression);
        Optional<MovaParser.IncrementContext> incrementContext = Optional.ofNullable(ctx.increment());
        incrementContext.ifPresent(this::visitIncrement);
        Optional<MovaParser.DecrementContext> decrementContext = Optional.ofNullable(ctx.decrement());
        decrementContext.ifPresent(this::visitDecrement);
        return null;
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
