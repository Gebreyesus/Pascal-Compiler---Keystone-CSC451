package tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.StringReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import scanner.myScannerfromJFLEX; 
import scanner.Token;
import scanner.TokenType;

/**
 *  @author Beteab Gebru
 *  will test the myScanner file.
 */

public class ScannerTest 
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpClass() throws Exception 
	{
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownClass() throws Exception 
	{
	}

	
	/**
	 * IN the following two methods we will test that the myScanner with sample input 
	 * and check if yytext()/nextToken function is interpreting the tokens.
	 * @throws IOException
	 */
	
    @Test
    public void testYytext() throws IOException 
    {
		// instantiate the myScanner and setup a sample input 
		String samplePascal = "5*5/2+5<100";
		StringReader input =  new StringReader(samplePascal);

		myScannerfromJFLEX myScanner = new myScannerfromJFLEX(input);
   
        myScanner.nextToken();
        String expResult = "5";
        String result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = "*";
        result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = " 5";
        result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = "/";
        result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = "2";
        result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = "+";
        result = myScanner.yytext();
        assertEquals(expResult, result);
        
        myScanner.nextToken();
        expResult = "5";
        result = myScanner.yytext();
        assertEquals(expResult, result);
        
        myScanner.nextToken();
        expResult = "<";
        result = myScanner.yytext();
        assertEquals(expResult, result);

        myScanner.nextToken();
        expResult = "100";
        result = myScanner.yytext();
        assertEquals(expResult, result);
    }
    

	@Test
	public void testNextToken() throws IOException 
	{
		// instantiate the myScanner and setup a sample input 
		String samplePascal = "program one ; begin Gebru.G end . {comment in parenthesis } ";
		StringReader input = new StringReader(samplePascal);

		myScannerfromJFLEX myScanner = new myScannerfromJFLEX(input);
		Token myToken = null;

		// verifying if the program will pickup tokens and identify them accordingly
		//comments will be ignored 
		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, "program");
		assertEquals(myToken.type, TokenType.PROGRAM);

		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, "one");
		assertEquals(myToken.type, TokenType.ID);

		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, ";");
		assertEquals(myToken.type, TokenType.SEMICOLON);

		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, "begin");
		assertEquals(myToken.type, TokenType.BEGIN);
		
		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, "Gebru.G");
		assertEquals(myToken.type, TokenType.ID);

		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, "end");
		assertEquals(myToken.type, TokenType.END);

		myToken = myScanner.nextToken();
		System.out.println(myToken);
		assertEquals(myToken.lexeme, ".");
		assertEquals(myToken.type, TokenType.PERIOD);
	}
}
