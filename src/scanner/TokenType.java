package scanner;

/**
 * 
 * @author Beteab Gebru
 *  will have enums of predefined keywords and symbols for the minpascal language
 */
public enum TokenType {
	// keywords and symbols
	PROGRAM, VAR, ID, ARRAY, INTEGER, REAL, FUNCTION, PROCEDURE,
	BEGIN, END, ASSIGNOP, IF, THEN, ELSE, WHILE, DO, RELOP, ADDOP,
	MULOP, NUM, NOT, AND, OR, MOD, DIV, OF, FLOAT, POSEXP, NEGEXP, 
	SEMICOLON, COLON, COMMA, NUMBER, L_ROUND_BRACKET, R_ROUND_BRACKET,
	L_SQUARE_BRACKET, R_SQUARE_BRACKET, L_CURLY_BRACKET,R_CURLY_BRACKET,
	SIGN, EOF, DOT
}
