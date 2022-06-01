lexer grammar MovaLexer;

WS                 : [ \n\r\t] -> skip ;

DO                 : 'do' ;
UNTIL              : 'until' ;
TIMES              : 'times';
REPEAT             : 'repeat' ;
IF                 : 'if' ;
OTHERWISE          : 'otherwise' ;
SHOW               : 'show' ;

PLUS               : '+' | 'plus' | 'add' ;
MINUS              : '-' | 'minus' | 'subtract' ;
MULTIPLY           : '*' | 'multiply' | 'multiply by' ;
DIVIDE             : '/' | 'divide' | 'divide by' | 'div' ;
PREFIX             : 'prefix' | 'prefixed' | 'prefixed with' ;
SUFFIX             : 'suffix' | 'with suffix' ;
WITH               : 'with' ;

EQUALS             : '=' | 'is' | 'equal to' | 'equals to' | 'equals';
LPAREN             : '(' ;
RPAREN             : ')' ;
THEN               : 'then' ;
COLON              : ':' ;

OR                 : 'or' | '|' | '||' ;
AND                : 'and' | '&' | '&&' ;
NOT                : 'not' | '!' ;

INCREMENT          : 'increment' | '++' ;
DECREMENT          : 'decrement' | '--' ;

COMMA              : ',' ;
ALSO               : ';' | 'also' ;
DOT                : '.' ;

MORETHAN           : '>' | 'more than' | 'more' ;
LESSTHAN           : '<' | 'less than' | 'less' ;
MOREOREQUAL        : '>=' | 'more or equal to' | 'more or equal' ;
LESSOREQUAL        : '<=' | 'less or equal to' | 'less or equal' ;
NOTEQUAL           : '!=' | 'not equal to' | 'not equal' ;

fragment DIGIT     : [0-9] ;
fragment SYMBOL    : [A-Za-z0-9_] ;
fragment ESCAPE_CHAR : '\\' [0btnfr"'\\] ;

INTEGER            : '-'? DIGIT+ ;
DECIMAL            : '-'? DIGIT+ COMMA DIGIT+ ;
STRING             : '"' ( ~[\\"\r\n] | ESCAPE_CHAR )* '"' ;
IDENTIFIER         : SYMBOL+ ;

COMMENT            : ('//' | '#' | 'comment:' | 'note:') ~[\r\n]* -> skip ;

// easter egg, this command should reply in terminal "Heroyam Slava!" ("Героям Слава!")
SLAVAUKRAINI       : ((('s' | 'S')'lava ' ('u' | 'U') 'kraini') | (('с' | 'С')'лава ' ('у' | 'У') 'країні')) '!'? ;