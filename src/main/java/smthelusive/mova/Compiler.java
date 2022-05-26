package smthelusive.mova;

import smthelusive.mova.domain.MovaValue;

import java.util.HashMap;
import java.util.Optional;

/***
 *  Compiler will do the following:
 *  - read the file
 *  - build the parse tree using the existing antlr parser
 *  - visit the main program node to get the queue of instructions
 *  - write the bytecode to a class file
 */
public class Compiler {
    private static final HashMap<String, MovaValue> variables = new HashMap<>();

    public static void registerVariable(String identifier, MovaValue value) {
        variables.put(identifier, value);
    }

    public static Optional<MovaValue> getVariableValue(String identifier) {
        return Optional.ofNullable(variables.get(identifier));
    }

}
