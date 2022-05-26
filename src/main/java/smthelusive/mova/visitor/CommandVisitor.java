package smthelusive.mova.visitor;

import smthelusive.mova.Compiler;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.util.OperationsUtil;

public class CommandVisitor extends MovaParserBaseVisitor<Void> {

    @Override
    public Void visitAssignment(MovaParser.AssignmentContext ctx) {

        return super.visitAssignment(ctx);
    }

    @Override
    public Void visitOutput(MovaParser.OutputContext ctx) {
        return super.visitOutput(ctx);
    }

// todo fix DRY

    @Override
    public Void visitDecrement(MovaParser.DecrementContext ctx) {
        String identifier = ctx.getChild(1).getText();
        Compiler.getVariableValue(identifier)
                .ifPresent(movaValue -> Compiler.registerVariable(identifier, OperationsUtil.decrement(movaValue)));
        return super.visitDecrement(ctx);
    }

    @Override
    public Void visitIncrement(MovaParser.IncrementContext ctx) {
        String identifier = ctx.getChild(1).getText();
        Compiler.getVariableValue(identifier)
                .ifPresent(movaValue -> Compiler.registerVariable(identifier, OperationsUtil.increment(movaValue)));
        return super.visitIncrement(ctx);
    }

    @Override
    public Void visitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx) {
        return super.visitSlavaUkraini(ctx);
    }
}
