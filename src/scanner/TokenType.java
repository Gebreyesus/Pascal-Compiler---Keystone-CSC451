package scanner;

/**
 * 
 * @author Beteab Gebru
 *  will have enums of predefined keywords and symbols for the minpascal language
 */
public enum TokenType {
	// keywords and symbols
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    LEFT_ROUND_PAREN,
    RIGHT_ROUND_PAREN,
    RIGHT_SQUARE_PAREN,
    LEFT_SQUARE_PAREN,
    SEMICOLON,
    COLON,
    COMMA,
    ASSIGNOP,
    DOT, 
	RELOP,
	ADDOP,
	MULOP,
	EQUAL,
	LESSTHAN,
	GREATERTHAN,
    LESSOREQUAL,
    GREATEROREQUAL, 
    
	PROGRAM, 
	VAR,
	ID,
	ARRAY,
	INTEGER,
	REAL,
	FUNCTION,
	PROCEDURE,	
	BEGIN,
	END,
 
	IF,
	THEN,
	ELSE,
	WHILE,
	DO,

	NUM,
	NOT,
	AND,
	OR,
	MOD,
	DIV,
	OF,
	FLOAT,
	POSEXP,
	NEGEXP,
 
	NUMBER,
	SIGN, 
	EOF, 
}
