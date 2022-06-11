parser grammar MovaParser;

options { tokenVocab = MovaLexer; }

value : IDENTIFIER | INTEGER | DECIMAL | STRING;

expression : expression (MULTIPLY | DIVIDE) expression
            | expression (PLUS | MINUS) expression
            | expression (PREFIX | SUFFIX | WITH) expression
            | LPAREN expression RPAREN
            | value;
decrement: DECREMENT IDENTIFIER | IDENTIFIER DECREMENT;
increment: INCREMENT IDENTIFIER | IDENTIFIER INCREMENT;
allKindsExpression: (expression | increment | decrement);

assignment: IDENTIFIER EQUALS allKindsExpression;
output: SHOW allKindsExpression;

slavaUkraini: SLAVAUKRAINI;

command: (assignment | decrement | increment | output);

condition : condition (AND | OR) condition
            | LPAREN condition RPAREN
            | (NOT)* allKindsExpression ((NOT)* (EQUALS | GREATERTHAN | LESSTHAN | GREATEROREQUAL | LESSOREQUAL | NOTEQUAL) allKindsExpression)*;

conditional: IF condition (THEN | COLON)? validStructure
(OTHERWISE validStructure)?;

loop: (((DO | REPEAT) ((allKindsExpression TIMES) | (UNTIL condition)) COLON validStructure) |
((DO | REPEAT) validStructure allKindsExpression TIMES));

validStructure: (command | conditional | loop | slavaUkraini) (ALSO validStructure)* ;

validProgram: (validStructure DOT)* EOF;