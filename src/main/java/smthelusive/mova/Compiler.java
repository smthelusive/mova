package smthelusive.mova;

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
    private HashMap<String, String> variables = new HashMap<>();

    public void registerVariable(String identifier, String value) {
        variables.put(identifier, value);
    }

    public Optional<String> getVariableValue(String identifier) {
        return Optional.ofNullable(variables.get(identifier));
    }

}
