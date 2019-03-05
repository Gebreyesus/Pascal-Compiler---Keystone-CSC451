package scanner;

/**
 * 
 * @author Beteab Gebru
 *  will have enums of predefined keywords and symbols for the minpascal language
 */
public enum TokenType {
	// keywords and symbols

    LEFT_ROUND_PAREN,
    RIGHT_ROUND_PAREN,
    RIGHT_SQUARE_PAREN,
    LEFT_SQUARE_PAREN,
    RIGHT_CURLY_PAREN,
    LEFT_CURLY_PAREN,
    
	RELOP,
	EQUAL,
	LESSTHAN,
	GREATERTHAN,
    LESSOREQUAL,
    GREATEROREQUAL,
    NOTEQUAL,
        
	ADDOP,
    PLUS,
    MINUS,
	OR,
	
    SEMICOLON,
    COLON,
    COMMA,
    ASSIGNOP,
    PERIOD, 
	
	MULOP,
	MULTIPLY,
    DIVIDE,
    MOD,
    AND,

	NUMBER,
	REAL,
	INTEGER,
	FLOAT,
	POSEXP,
	NEGEXP,
	SIGN, 
	
	PROGRAM, 
	VAR,
	ID,
	ARRAY,
	FUNCTION,
	PROCEDURE,	
	BEGIN,
	END,
 
	IF,
	THEN,
	ELSE,
	WHILE,
	DO,
	OF,
	NOT,
	
	EOF, 
}
