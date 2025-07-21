lexer grammar QueryLexer;

@header {
	package query.parser;
}	

SEMICOLON: ';';

COMMA: ',';

ASTERISK: '*';

LEFTBRACKET: '(';
RIGHTBRACKET: ')';
LEFTBRACE: '{';
RIGHTBRACE: '}';
LEFTSQUARE: '[';
RIGHTSQUARE: ']';

AND: 'and';
OR: 'or';
NOT: 'not';

IN: 'in';
FROM: 'from';
WITH: 'with';

BACK: 'back';
FORWARD: 'forward';
TRACK: 'track';
SEARCH: 'search';

AS: 'as';

DISPLAY: 'display';

EXPORT: 'export';

RETURN: 'return';

SELECT: 'select';

CONNECTDB: 'connectdb';
DB: 'db';

WHERE: 'where';
INCLUDE: 'include';
EXCLUDE: 'exclude';
NODES: 'nodes';
EDGES: 'edges';

LIMIT: 'limit';
TIME: 'time';
STEP: 'step';

MS: 'ms';
SECOND: 's';
MINUTE: 'm';

TYPE: 'type';
NAME: 'name';
PATH: 'path';
DSTIP: 'dstip';
DSTPORT: 'dstport';
SRCIP: 'srcip';
SRCPORT: 'srcport';
PID: 'pid';
EXENAME: 'exename';
EXEPATH: 'exepath';
CMDLINE: 'cmdline';
OPTYPE: 'optype';

IDSTR: 'id';
SRCID: 'srcid';
DSTID: 'dstid';
STARTTIME: 'starttime';
ENDTIME: 'endtime';
AMOUNT: 'amount';

PROCESS: 'process';
FILE: 'file';
NETWORK: 'network';

READ: 'read';
WRITE: 'write';
EXECVE: 'execve';
NULL: 'null';
ARROW: '->';

EQ: '=';
NEQ: '!=';

GT: '>';
GEQ: '>=';
LT: '<';
LEQ: '<=';

LIKE: 'like';

UNION: '|';
INTERSECTION: '&';
DIFFERENCE: '-';

LOGAND: '&&';
LOGOR: '||';

// Values (from BQL)
INT
:
	POSINT | NEGINT
;

/* FLOAT
:
	POSFLOAT | NEGFLOAT
; */

fragment
POSINT
:
	DIGIT+
;

/* fragment
POSFLOAT
:
	DIGIT+ '.' DIGIT* // match 1. 39. 3.14159 etc...

	| '.' DIGIT+ // match .1 .14159
; */

fragment
NEGINT
:
	('-')DIGIT+
;

/* fragment
NEGFLOAT
:
	('-')DIGIT+ '.' DIGIT* // match 1. 39. 3.14159 etc...

	| ('-')'.' DIGIT+ // match .1 .14159
; */

STRING
:
	'"' DoubleStringCharacter* '"'
	| '\'' SingleStringCharacter* '\''
;

fragment
DoubleStringCharacter
:
	~["\r\n]
;
fragment
SingleStringCharacter
:
	~['\r\n]
;
fragment
DIGIT
:
	[0-9]
;
ID
:
	ID_LETTER
	(
		ID_LETTER
		| DIGIT
	)*
;
fragment
ID_LETTER
:
	[a-zA-Z_]
;
// Whitespace and comments
NEWLINE
:
	[\r\n]+ -> skip
;
WS
:
	[ \t\r\n\u000C]+ -> skip
;
COMMENT
:
	'/*' .*? '*/' -> skip
;
LINE_COMMENT
:
	'//' .*? '\r'? '\n' -> skip
;
