parser grammar MovaParser;

options { tokenVocab = MovaLexer; }

value : IDENTIFIER | INTEGER | DECIMAL | STRING;

expression : expression (MULTIPLY | DIVIDE) expression
            | expression (PLUS | MINUS) expression
            | expression (PREFIX | SUFFIX | WITH) expression
            | LPAREN expression RPAREN
            | value;

assignment: IDENTIFIER EQUALS expression;

output: SHOW expression;
decrement: DECREMENT IDENTIFIER;
increment: INCREMENT IDENTIFIER;
slavaUkraini: SLAVAUKRAINI;

command: (assignment | decrement | increment | output);

condition: expression (NOT)* ((EQUALS | MORETHAN | LESSTHAN | MOREOREQUAL | LESSOREQUAL | NOTEQUAL) expression)*;
conditional: (IF condition ((AND | OR) condition)* (THEN | COLON) command (ALSO command)*)+
(OTHERWISE command (ALSO command)*)*;

loop: (((DO | REPEAT) ((expression TIMES) | (UNTIL condition)) COLON command (ALSO command)*) |
(command expression TIMES));

validStructure: ((command | conditional | loop) (ALSO validStructure)* DOT)
                | slavaUkraini;

validProgram: validStructure* EOF;