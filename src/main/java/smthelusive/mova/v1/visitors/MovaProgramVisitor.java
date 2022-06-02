package smthelusive.mova.v1.visitors;

import smthelusive.mova.v1.ByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class MovaProgramVisitor extends MovaParserBaseVisitor<Void> {

    private final CommandVisitor commandVisitor;

    public MovaProgramVisitor(ByteCodeGenerator bytecodeGenerator) {
        commandVisitor = new CommandVisitor(bytecodeGenerator);
    }

    @Override
    public Void visitValidStructure(MovaParser.ValidStructureContext ctx) {
        if (ctx.command() != null) commandVisitor.visitCommand(ctx.command());
        if (ctx.slavaUkraini() != null) commandVisitor.visitSlavaUkraini(ctx.slavaUkraini());
        // todo add more options
        return super.visitValidStructure(ctx);
    }

    @Override
    public Void visitValidProgram(MovaParser.ValidProgramContext ctx) {
        return super.visitValidProgram(ctx);
    }
}
