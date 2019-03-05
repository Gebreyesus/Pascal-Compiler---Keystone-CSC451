package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.util.logging.Level;
import java.util.logging.Logger;

import scanner.myScannerfromJFLEX; 
import scanner.Token;
import scanner.TokenType;


/**
 * The parser will look at series of tokens and decide if they are expressions
 * parser will read an input file containing sample pascal function
 * It will then call the top-level function from the input code
 * If the functions returns without an error, the file
 * contains an acceptable expression. 
 * Betab Gebru
 */
public class Parser 
{
    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////
    
    private Token lookahead;     
    private myScannerfromJFLEX scanner;
    
    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
    // making sure the file is readable and valid
    public Parser( String text, boolean isFilename) 
    {
        if( isFilename) 
        {
        	FileInputStream fis = null;
        	try 
        		{
        			fis = new FileInputStream("expressions/simplest.pas");
        		} 
        	catch (FileNotFoundException ex) 
        		{
        			error( "No file with that name");
        		}
        	// getting the scanner started with a valid input file
        	InputStreamReader isr = new InputStreamReader( fis);
        	scanner = new myScannerfromJFLEX( isr);    
        }
        else 
        	{
            	scanner = new myScannerfromJFLEX( new StringReader( text));
        	}
        	try 
        	{
        		lookahead = scanner.nextToken();
        	} 
        	catch (IOException ex) 
        	{
        		error( "Error reading the file contents");
        	}
    }
    
    ///////////////////////////////
    //       Methods
    ///////////////////////////////
    
    /**
     * Executes the rule for the exp non-terminal symbol in
     * the expression grammar.
     */
    public void exp() 
    {
        terminal();
        exp_prime();
    }
    
    /**
     * Executes the rule for the exp&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void exp_prime() 
    {
        if( lookahead.getType() == TokenType.PLUS || 
                lookahead.getType() == TokenType.MINUS ) 
        {
            addop();
            terminal();
            exp_prime();
        }
        else
        {
            // lambda option
        }
    }
    
    /**
     * Executes the rule for the addop non-terminal symbol in
     * the expression grammar.
     */
    public void addop() 
    {
        if( lookahead.getType() == TokenType.PLUS) 
        {
            match( TokenType.PLUS);
        }
        else if( lookahead.getType() == TokenType.MINUS) 
        {
            match( TokenType.MINUS);
        }
        else 
        {
            error( "Addop");
        }
    }
    
    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     */
    public void terminal() 
    {
        factor();
        terminal_prime();
    }
    
    /**
     * Executes the rule for the term&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void terminal_prime() 
    {
        if( isMulop( lookahead) )
        {
            mulop();
            factor();
            terminal_prime();
        }
        else
        {
            // lambda option
        }
    }
    
    /**
     * Determines whether or not the given token is
     * a mulop token.
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    private boolean isMulop( Token token) 
    {
        boolean answer = false;
        if( token.getType() == TokenType.MULTIPLY || 
                token.getType() == TokenType.DIVIDE ) 
        {
            answer = true;
        }
        return answer;
    }
    
    /**
     * Executes the rule for the mulop non-terminal symbol in
     * the expression grammar.
     */
    public void mulop() 
    {
        if( lookahead.getType() == TokenType.MULTIPLY) 
        {
            match( TokenType.MULTIPLY);
        }
        else if( lookahead.getType() == TokenType.DIVIDE) 
        {
            match( TokenType.DIVIDE);
        }
        else 
        {
            error( "Mulop");
        }
    }
    
    /**
     * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     */
    public void factor() 
    {
        // Executed this decision as a switch instead of an
        // if-else chain. Either way is acceptable.
        switch (lookahead.getType()) 
        {
            case LEFT_ROUND_PAREN:
                match( TokenType.LEFT_ROUND_PAREN);
                exp();
                match( TokenType.LEFT_ROUND_PAREN);
                break;
            case NUMBER:
                match( TokenType.NUMBER);
                break;
            default:
                error("Factor");
                break;
        }
    }
    
    /**
     * Matches the expected token.
     * If the current token in the input stream from the scanner
     * matches the token that is expected, the current token is
     * consumed and the scanner will move on to the next token
     * in the input.
     * The null at the end of the file returned by the
     * scanner is replaced with a fake token containing no
     * type.
     * @param expected The expected token type.
     */
    public void match( TokenType expected) 
    {
        System.out.println("match( " + expected + ")");
        if( this.lookahead.getType() == expected) 
        {
            try 
            {
                this.lookahead = scanner.nextToken();
                if( this.lookahead == null) 
                {
                    this.lookahead = new Token( "End of File", null);
                }
            } 
            catch (IOException ex) 
            {
                error( "Scanner exception");
            }
        }
        else 
        {
            error("Match of " + expected + " found " + this.lookahead.getType()  + " instead.");
        }
    }
    
    /**
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     * @param message The error message to print.
     */
    public void error( String message) 
    {
        System.out.println( "Error " + message + " at line " + this.scanner.getLine() + " column " + this.scanner.getColumn());
        //System.exit( 1);
    }
}