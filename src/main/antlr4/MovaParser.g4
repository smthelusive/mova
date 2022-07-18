parser grammar MovaParser;

options { tokenVocab = MovaLexer; }

value : ARGUMENT | IDENTIFIER | INTEGER | DECIMAL | STRING ;

expression : expression (MULTIPLY | DIVIDE) expression
            | expression (PLUS | MINUS) expression
            | expression (PREFIX | SUFFIX) expression
            | LPAREN expression RPAREN
            | value;
decrement: DECREMENT IDENTIFIER | IDENTIFIER DECREMENT;
increment: INCREMENT IDENTIFIER | IDENTIFIER INCREMENT;
reverse: REVERSE (IDENTIFIER | STRING) | (IDENTIFIER | STRING) REVERSE;
allKindsExpression: (expression | increment | decrement | reverse);

assignment: IDENTIFIER EQUALS allKindsExpression;
output: SHOW allKindsExpression;

easterEgg: SLAVAUKRAINI;

command: (assignment | decrement | increment | reverse | output);

condition : condition (AND | OR) condition
            | LPAREN condition RPAREN
            | (NOT)* allKindsExpression ((NOT)* (EQUALS | GREATERTHAN | LESSTHAN |
            GREATEROREQUAL | LESSOREQUAL | NOTEQUAL | CONTAINS) allKindsExpression)*;

conditional: IF condition (THEN | COLON)? validStructure
(OTHERWISE validStructure)?;

loop: (((DO | REPEAT) ((allKindsExpression TIMES) | (UNTIL condition)) COLON? validStructure) |
((DO | REPEAT) validStructure allKindsExpression TIMES));

validStructure: (command | conditional | loop | easterEgg) (ALSO validStructure)* ;

validProgram: (validStructure DOT)* EOF;