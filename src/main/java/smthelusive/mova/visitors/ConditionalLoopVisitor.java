package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.objectweb.asm.Opcodes;
import smthelusive.mova.ByteCodeGenerator;
import smthelusive.mova.Compiler;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

import java.util.Optional;

public class ConditionalLoopVisitor extends MovaParserBaseVisitor<Void> {

    private final MovaProgramVisitor movaProgramVisitor;
    private final ByteCodeGenerator byteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;

    public ConditionalLoopVisitor(MovaProgramVisitor movaProgramVisitor) {
        this.movaProgramVisitor = movaProgramVisitor;
        this.byteCodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
        this.expressionVisitor = movaProgramVisitor.getExpressionVisitor();
    }

    @Override
    public Void visitCondition(MovaParser.ConditionContext ctx) {
        if (ctx.getChildCount() == 1) { // just some expression that can be greater than 0
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
            byteCodeGenerator.storeBooleanOnCondition(Opcodes.IFGT);
        } else {
            if (ctx.LPAREN() != null) { // (condition)
                visitCondition(ctx.condition(0));
            } else if (ctx.OR() != null) { // condition OR condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                byteCodeGenerator.bitwiseOr();
            } else if (ctx.AND() != null) { // condition AND condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                byteCodeGenerator.bitwiseAnd();
            } else if (ctx.allKindsExpression().size() == 2) { // allKindsExpression ? allkindsExpression
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(1));

                int amountOfNegations = ctx.NOT().size();
                boolean negated = (amountOfNegations > 0) && (amountOfNegations % 2 != 0);
                int comparingSymbolIndex = ctx.NOT().size() + 1;
                TerminalNode comparison = (TerminalNode)ctx.getChild(comparingSymbolIndex);
                String comparisonKeyword = MovaParser.VOCABULARY.getSymbolicName(comparison.getSymbol().getType());
                byteCodeGenerator.compareTwoThings(comparisonKeyword, negated);
            }
        }
        return null;
    }

    @Override
    public Void visitConditional(MovaParser.ConditionalContext ctx) {
        visitCondition(ctx.condition());
        MovaParser.ValidStructureContext ifTrueContext = ctx.validStructure(0);
        Optional<MovaParser.ValidStructureContext> ifFalseContext = Optional.ofNullable(ctx.validStructure(1));
        byteCodeGenerator.doOrElse(movaProgramVisitor::visitValidStructure, ifTrueContext, ifFalseContext);
        return null;
    }

    @Override
    public Void visitLoop(MovaParser.LoopContext ctx) {
        if (ctx.allKindsExpression() != null) {
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression());
            byteCodeGenerator.convertLastTypeToInteger();
            String movaInternalVarIdentifier = Compiler.getNewMovaInternalVariableIdentifier();
            byteCodeGenerator.addVariableAssignment(movaInternalVarIdentifier);
            byteCodeGenerator.expressionBasedLoop(movaProgramVisitor::visitValidStructure,
                    ctx.validStructure(), movaInternalVarIdentifier);
        } else if (ctx.condition() != null) {
            byteCodeGenerator.conditionBasedLoop(
                    this::visitCondition,
                    ctx.condition(),
                    movaProgramVisitor::visitValidStructure,
                    ctx.validStructure());
        }
        return null;
    }
}
