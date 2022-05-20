parser grammar MovaParser;

options { tokenVocab = MovaLexer; }

value : IDENTIFIER | INTEGER | DECIMAL | STRING;
action: PLUS | MINUS | MULTIPLY | DIVIDE | PREFIX | SUFFIX | WITH;

expression : expression action expression
           | LPAREN expression RPAREN
           | value;

assignment: IDENTIFIER EQUALS expression DOT;

output: SHOW expression;
decrement: DECREMENT IDENTIFIER;
increment: INCREMENT IDENTIFIER;

command: (assignment | decrement | increment | output);

condition: expression (NOT)* ((EQUALS | MORETHAN | LESSTHAN | MOREOREQUAL | LESSOREQUAL | NOTEQUAL) expression)*;
conditional: (IF condition ((AND | OR) condition)* (THEN | COLON) command (ALSO command)*)+
(OTHERWISE command (ALSO command)*)*;

loop: (((DO | REPEAT) ((expression TIMES) | (UNTIL condition)) COLON command (ALSO command)*) |
(command expression TIMES));

validStructure: (command | conditional | loop) (ALSO validStructure)* DOT;

validProgram: validStructure* EOF;

//todo: negative values, comments