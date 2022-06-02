package smthelusive.mova.visitors;

import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

public class ByteCodeOrientedMovaProgramVisitor extends MovaParserBaseVisitor<Void> {

    private final ByteCodeOrientedCommandVisitor commandVisitor;

    public ByteCodeOrientedMovaProgramVisitor(SmartByteCodeGenerator smartByteCodeGenerator) {
        commandVisitor = new ByteCodeOrientedCommandVisitor(smartByteCodeGenerator);
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
