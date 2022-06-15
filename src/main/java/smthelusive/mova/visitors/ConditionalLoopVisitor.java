package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.objectweb.asm.Opcodes;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

import java.util.Optional;

public class ConditionalLoopVisitor extends MovaParserBaseVisitor<Void> {

    private final MovaProgramVisitor movaProgramVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;
    private final static String MOVA_INDEX_RESERVED = "mova_index";
    private int movaInternalVariableNumber = 0;

    private String getNewMovaInternalVariableIdentifier() {
        return MOVA_INDEX_RESERVED + movaInternalVariableNumber++;
    }

    public ConditionalLoopVisitor(MovaProgramVisitor movaProgramVisitor) {
        this.movaProgramVisitor = movaProgramVisitor;
        this.smartByteCodeGenerator = movaProgramVisitor.getSmartByteCodeGenerator();
        this.expressionVisitor = movaProgramVisitor.getExpressionVisitor();
    }

    @Override
    public Void visitCondition(MovaParser.ConditionContext ctx) {
        if (ctx.getChildCount() == 1) { // just some expression that can be greater than 0
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
            smartByteCodeGenerator.storeBooleanOnCondition(Opcodes.IFGT);
        } else {
            if (ctx.LPAREN() != null) { // (condition)
                visitCondition(ctx.condition(0));
            } else if (ctx.OR() != null) { // condition OR condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.bitwiseOr();
            } else if (ctx.AND() != null) { // condition AND condition
                visitCondition(ctx.condition(0));
                visitCondition(ctx.condition(1));
                smartByteCodeGenerator.bitwiseAnd();
            } else if (ctx.allKindsExpression().size() == 2) { // allKindsExpression ? allkindsExpression
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(0));
                // lock context because for comparison we are subtracting one value from the other
                // for any expression the context should be locked so the types are correctly derived
                smartByteCodeGenerator.lockContext();
                expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression(1));

                int amountOfNegations = ctx.NOT().size();
                boolean negated = (amountOfNegations > 0) && (amountOfNegations % 2 != 0);
                int comparingSymbolIndex = ctx.NOT().size() + 1;
                TerminalNode comparison = (TerminalNode)ctx.getChild(comparingSymbolIndex);
                String comparisonKeyword = MovaParser.VOCABULARY.getSymbolicName(comparison.getSymbol().getType());
                smartByteCodeGenerator.compareTwoThings(comparisonKeyword, negated);
            }
        }
        return null;
    }

    @Override
    public Void visitConditional(MovaParser.ConditionalContext ctx) {
        visitCondition(ctx.condition());
        MovaParser.ValidStructureContext ifTrueContext = ctx.validStructure(0);
        Optional<MovaParser.ValidStructureContext> ifFalseContext = Optional.ofNullable(ctx.validStructure(1));
        smartByteCodeGenerator.doOrElse(movaProgramVisitor::visitValidStructure, ifTrueContext, ifFalseContext);
        return null;
    }

    @Override
    public Void visitLoop(MovaParser.LoopContext ctx) {
        if (ctx.allKindsExpression() != null) {
            expressionVisitor.visitAllKindsExpression(ctx.allKindsExpression());
            smartByteCodeGenerator.switchContextToInteger();
            String movaInternalVarIdentifier = getNewMovaInternalVariableIdentifier();
            smartByteCodeGenerator.addVariableAssignment(movaInternalVarIdentifier);
            smartByteCodeGenerator.expressionBasedLoop(movaProgramVisitor::visitValidStructure,
                    ctx.validStructure(), movaInternalVarIdentifier);
        } else if (ctx.condition() != null) {
            smartByteCodeGenerator.conditionBasedLoop(
                    this::visitCondition,
                    ctx.condition(),
                    movaProgramVisitor::visitValidStructure,
                    ctx.validStructure());
        }
        return null;
    }
}
