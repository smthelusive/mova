package smthelusive.mova.util;

import smthelusive.mova.domain.MovaAction;
import smthelusive.mova.domain.MovaType;
import smthelusive.mova.domain.MovaValue;
import java.util.Optional;

public class OperationsUtil {

    public static MovaValue calculate(MovaValue left, MovaValue right, MovaAction action) {
        MovaValue movaValue = new MovaValue();
        if (isStringContext(left, right, action)) {
            movaValue.setMovaType(MovaType.STRING);
            movaValue.setRawValue(performStringAction(left, right, action));
        } else if (isDoubleContext(left, right)) {
            movaValue.setMovaType(MovaType.DECIMAL);
            movaValue.setRawValue(performDoubleAction(left, right, action));
        } else {
            movaValue.setMovaType(MovaType.INTEGER);
            movaValue.setRawValue(performIntegerAction(left, right, action));
        }
        return movaValue;
    }

    public static boolean isStringContext(MovaValue left, MovaValue right, MovaAction action) {
        return (action.equals(MovaAction.PREFIX) || action.equals(MovaAction.SUFFIX) || action.equals(MovaAction.WITH) ||
                Optional.ofNullable(left.getMovaType()).stream().anyMatch(type -> type.equals(MovaType.STRING)) ||
                        Optional.ofNullable(right.getMovaType()).stream().anyMatch(type -> type.equals(MovaType.STRING)));
    }

    public static boolean isDoubleContext(MovaValue left, MovaValue right) {
        return Optional.ofNullable(left.getMovaType()).stream().anyMatch(type -> type.equals(MovaType.DECIMAL)) ||
                Optional.ofNullable(right.getMovaType()).stream().anyMatch(type -> type.equals(MovaType.DECIMAL));
    }

    public static String performStringAction(MovaValue left, MovaValue right, MovaAction action) {
        switch (action) {
            case PREFIX:
            case WITH: return left.getStringValue() + right.getStringValue();
            case SUFFIX: return right.getStringValue() + left.getStringValue();
        }
        // todo log warning
        return "";
    }

    public static String performDoubleAction(MovaValue left, MovaValue right, MovaAction action) {
        double leftD = left.getDoubleValue();
        double rightD = right.getDoubleValue();
        return String.valueOf(getResultOfCalculation(leftD, rightD, action));
    }

    public static String performIntegerAction(MovaValue left, MovaValue right, MovaAction action) {
        int leftI = left.getIntegerValue();
        int rightI = right.getIntegerValue();
        Double result = getResultOfCalculation(leftI, rightI, action);
        return String.valueOf(result.intValue());
    }

    public static Double getResultOfCalculation(double left, double right, MovaAction action) {
        switch (action) {
            case PLUS: return left + right;
            case MINUS: return left - right;
            case MULTIPLY: return left * right;
            case DIVIDE: return left / right;
            default: return left;
        }
    }

    public static MovaAction convertedAction(String rawAction) {
        switch (rawAction) {
            case "PLUS": return MovaAction.PLUS;
            case "MINUS": return MovaAction.MINUS;
            case "MULTIPLY": return MovaAction.MULTIPLY;
            case "DIVIDE": return MovaAction.DIVIDE;
            case "PREFIX": return MovaAction.PREFIX;
            case "SUFFIX": return MovaAction.SUFFIX;
            case "WITH":
            default: return MovaAction.WITH;
        }
    }
}
