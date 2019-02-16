package scanner;

import java.util.HashMap;

/**
 * A lookup table to find the token types for symbols based on the String.
 * @author Beteab Gebru
 */
public class LookupTable extends HashMap<String,TokenType> 
{
    
    /**
     * Creates a lookup table, loading all the token types.
     */
    public LookupTable() {
        this.put( "+", TokenType.PLUS);
        this.put( "-", TokenType.MINUS);
        this.put( "*", TokenType.MULTIPLY);
        this.put( "/", TokenType.DIVIDE);
        this.put( "(", TokenType.LEFT_ROUND_PAREN);
        this.put( ")", TokenType.RIGHT_ROUND_PAREN);
        this.put( "]", TokenType.RIGHT_SQUARE_PAREN);
        this.put( "[", TokenType.LEFT_SQUARE_PAREN);
        this.put( ";", TokenType.SEMICOLON);
        this.put( ":", TokenType.COLON);
        this.put( ",", TokenType.COMMA);
        this.put( ":=", TokenType.ASSIGNOP);
        this.put( ".", TokenType.DOT);    
        this.put( "+", TokenType.ADDOP); 
        this.put( "%", TokenType.MOD);
        this.put( "&", TokenType.AND);
        
        
    	//RELOP
    	
    } 
}
