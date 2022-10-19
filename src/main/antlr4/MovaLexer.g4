lexer grammar MovaLexer;

WS                 : [ \n\r\t]+ -> skip ;

DO                 : 'do' ;
UNTIL              : 'until' ;
TIMES              : 'times';
REPEAT             : 'repeat' ;
IF                 : 'if' ;
OTHERWISE          : 'otherwise' | 'else' ;
SHOW               : 'show' | 'print' | 'output' ;

PLUS               : '+' | 'plus' | 'add' ;
MINUS              : '-' | 'minus' | 'subtract' ;
MULTIPLY           : '*' | 'multiply' | 'multiply by' ;
DIVIDE             : '/' | 'div'('ide''d'?)?' by'? ;
PREFIX             : 'with prefix' | 'prefix''ed'?' with'? ;
SUFFIX             : 'with'' suffix'? | 'suffix''ed'?' with'? ;

EQUALS             : '=' | 'is' | 'equal''s'?' to'? ;
LPAREN             : '(' ;
RPAREN             : ')' ;
THEN               : 'then' ;
COLON              : ':' ;

OR                 : 'or' | '|' | '||' ;
AND                : 'and' | '&' | '&&' ;
NOT                : 'not' | '!' ;

INCREMENT          : 'increment''ed'? | '++' ;
DECREMENT          : 'decrement''ed'? | '--' ;
REVERSE            : 'reverse''d'? ;

COMMA              : ',' ;
ALSO               : ';' | 'also' ;
DOT                : '.' ;

GREATERTHAN        : '>' | ('greater'|'more')' than'? ;
LESSTHAN           : '<' | 'less'' than'? ;
GREATEROREQUAL     : '>=' | ('more'|'greater')' or equal'' to'? ;
LESSOREQUAL        : '<=' | 'less or equal'' to'? ;
NOTEQUAL           : '!=' | 'not equal'' to'? ;
CONTAINS           : 'contains' ;

fragment DIGIT     : [0-9] ;
fragment SYMBOL    : [A-Za-z0-9_] ;
fragment ESCAPE_CHAR : '\\' [0btnfr"'\\] ;

INTEGER            : '-'? DIGIT+ ;
DECIMAL            : '-'? DIGIT+ COMMA DIGIT+ ;
ARGUMENT           : 'arg' INTEGER ;
STRING             : '"' ( ~[\\"\r\n] | ESCAPE_CHAR )* '"' ;
IDENTIFIER         : SYMBOL+ ;

COMMENT            : ('//' | '#' | 'comment:' | 'note:') ~[\r\n]* -> skip ;

EASTEREGG       : ((('s' | 'S')'lava ' ('u' | 'U') 'kraini') | (('с' | 'С')'лава ' ('у' | 'У') 'країні')) '!'? ;