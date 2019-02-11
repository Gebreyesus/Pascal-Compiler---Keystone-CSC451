package scanner;
/**
 * Represents the token read by the scanner, contains a lexeme and the type of
 * token
 *  * @author Beteab Gebru
 */

public class Token 
{
	public String lexeme;
	public TokenType type;

	public String getLexeme() 
	{
		return this.lexeme;
	}
	public TokenType getType() 
	{
		return this.type;
	}

	/**
	 * Called by the scanner to create a token 
	 * @param inputLexeme	 *            the lexeme returned from the scanner
	 * @param inputType	 *            represents the type of lexeme,
	 * 
	 */
	public Token(String inputLexeme, TokenType inputType) 
	{
		this.lexeme = inputLexeme;
		this.type = inputType;
	};

	
	/**
	 * Prints the token
	 * 
	 * @return A string sentence with the TokenType and the lexeme
	 */
	@Override
	public String toString() 
	{
		return "Token: " + this.type + ", " + this.lexeme;
	}

}