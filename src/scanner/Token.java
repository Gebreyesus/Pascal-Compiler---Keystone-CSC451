package scanner;
/**
 * Represents the token read by the scanner, contains a lexeme and the type of
 * token
 *  * @author Beteab Gebru
 */

public class Token 
{
    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////
	public String lexeme;
	public TokenType type;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
	/**
	 * Called by the scanner to create a token 
	 * @param inputLexeme	 *            the lexeme returned from the scanner
	 * @param inputType	 *            represents the type for the lexeme,
	 * 
	 */
	public Token(String content, TokenType inputType) 
	{
		String inputLexeme = content.toLowerCase();
		this.lexeme = inputLexeme;
		this.type = inputType;
	}
	
    ///////////////////////////////
    //     Getters and Setters
    ///////////////////////////////
	
	public String getLexeme() {		return this.lexeme;	}
	public TokenType getType() 	{		return this.type; }

    ///////////////////////////////
    //       Methods
    ///////////////////////////////
	/**
	 * Prints the token and its lexeme
	 * @return A string sentence with the TokenType and the lexeme
	 */
	@Override
	public String toString() 
	{
		 return "Token: \"" + this.lexeme + "\" of type: " + this.type;
	}

}