package smthelusive.mova.visitors;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.objectweb.asm.Opcodes;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;

import java.util.Optional;
import java.util.stream.Stream;

public class ConditionalLoopVisitor extends MovaParserBaseVisitor<Void> {

    private final MovaProgramVisitor movaProgramVisitor;
    private final SmartByteCodeGenerator smartByteCodeGenerator;
    private final ExpressionVisitor expressionVisitor;

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
        /*
        option 1:
        allKindsExpression - validStructure

        visit expression - make sure integer context
        assign to variable mova_index
        L_beforeCondition.
        visit condition mova_index > 0
        jump IFGT to L_start
        goto L_end.
        L_start
        visit validStructure
        decrement mova_index
        jump to L_beforeCondition
        L_end

        option 2:
        condition - validStructure

        L_beforeCondition.
        visit condition
        jump IFGT to L_start
        goto L_end.
        L_start
        visit validStructure
        jump to L_beforeCondition
        L_end
         */
        return null;
    }
}
