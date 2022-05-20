parser grammar MovaParser;

options { tokenVocab = MovaLexer; }

value : IDENTIFIER | INTEGER | DECIMAL | STRING ;
action: PLUS | MINUS | MULTIPLY | DIVIDE | PREFIX | SUFFIX | WITH ;
something: value | expression;
expression : (LPAREN value action something RPAREN) | (value action something);

assignment: IDENTIFIER EQUALS something ;

output: SHOW something ;
decrement: DECREMENT IDENTIFIER;
increment: INCREMENT IDENTIFIER;

command: (assignment | decrement | increment | output) ;

condition: something (NOT)* ((EQUALS | MORETHAN | LESSTHAN | MOREOREQUAL | LESSOREQUAL | NOTEQUAL) something)* ;
conditional: (IF condition ((AND | OR) condition)* (THEN | COLON) command (ALSO command)*)+
(OTHERWISE command (ALSO command)*)*;

loop: (((DO | REPEAT) ((something TIMES) | (UNTIL condition)) COLON command (ALSO command)*) | (command something TIMES));

validStructure: (command | conditional | loop) DOT ;

validProgram: validStructure+ ;