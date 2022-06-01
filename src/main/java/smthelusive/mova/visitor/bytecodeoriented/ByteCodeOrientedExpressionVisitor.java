package smthelusive.mova.visitor.bytecodeoriented;

import org.antlr.v4.runtime.tree.TerminalNode;
import smthelusive.mova.SmartByteCodeGenerator;
import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import smthelusive.mova.gen.MovaParser;
import smthelusive.mova.gen.MovaParserBaseVisitor;
import smthelusive.mova.util.OperationsUtil;

public class ByteCodeOrientedExpressionVisitor extends MovaParserBaseVisitor<Void> {

    private final SmartByteCodeGenerator smartBytecodeGenerator;

    public ByteCodeOrientedExpressionVisitor(SmartByteCodeGenerator smartByteCodeGenerator) {
        this.smartBytecodeGenerator = smartByteCodeGenerator;
    }

    @Override
    public Void visitExpression(MovaParser.ExpressionContext ctx) {
        int childrenCount = ctx.getChildCount();
        // just a single value
        if (childrenCount == 1) {
            visit(ctx.getChild(0));
        }
        else if (childrenCount == 3) {
            // visiting expression inside the parentheses. 0 = '(', 1 = expression, 2 = ')'
            if (ctx.getChild(1).getChildCount() == 3) {
                visit(ctx.getChild(1));
            }
            // normal expression 0 = value, 1 = action, 2 = value:
            else {
                visit(ctx.getChild(0));
                smartBytecodeGenerator.lockContext();
                visit(ctx.getChild(2));
                TerminalNode actionNode = (TerminalNode)ctx.getChild(1);
                MovaAction action = OperationsUtil.convertedAction(
                        MovaParser.VOCABULARY.getSymbolicName((actionNode.getSymbol().getType())));
                smartBytecodeGenerator.performBytecodeOperation(action);
            }
        }
        return null;
    }

    @Override
    public Void visitValue(MovaParser.ValueContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            smartBytecodeGenerator.loadVariableToOpStack(ctx.getText());
            return super.visitValue(ctx);
        }

        MovaValue movaValue = new MovaValue();
        if (ctx.INTEGER() != null) movaValue.setMovaType(MovaType.INTEGER);
        else if (ctx.DECIMAL() != null) movaValue.setMovaType(MovaType.DECIMAL);
        else if (ctx.STRING() != null) movaValue.setMovaType(MovaType.STRING);
        movaValue.setRawValue(ctx.getText());
        smartBytecodeGenerator.pushValueToOpstack(movaValue);
        return null;
    }
}
