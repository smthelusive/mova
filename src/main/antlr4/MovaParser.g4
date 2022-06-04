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

block: command (ALSO command)*;
condition: allKindsExpression (NOT)* ((EQUALS | MORETHAN | LESSTHAN | MOREOREQUAL | LESSOREQUAL | NOTEQUAL) allKindsExpression)*;
conditional: (IF condition ((AND | OR) condition)* (THEN | COLON) block)+
(OTHERWISE block)*;

loop: (((DO | REPEAT) ((allKindsExpression TIMES) | (UNTIL condition)) COLON block) |
((DO | REPEAT) block allKindsExpression TIMES));

validStructure: ((command | conditional | loop) (ALSO validStructure)* DOT)
                | slavaUkraini;

validProgram: validStructure* EOF;