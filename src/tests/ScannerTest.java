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
	public void setUpClass() throws Exception 	{	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownClass() throws Exception 	{	}

	
	/**
	 * IN the following two methods we will test that the myScanner with sample input 
	 * and check if yytext()/nextToken function is interpreting the tokens.
	 * @throws IOException
	 */

	
    @Test
    public void testYytext() throws IOException 
    {
    	System.out.println("Testing the yytext() function ");
        FileInputStream fis = null;
               
        try 
        {
            fis = new FileInputStream("SampleInput\\ScannerSampleInput.pas");
        } catch (FileNotFoundException ex) 
        {
        }
        InputStreamReader isr = new InputStreamReader( fis);
        myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
        //**********
        System.out.println("Next string extracted->"+instance.yytext());
        
        instance.nextToken();
        String expectedResult = "34";
        String result = instance.yytext(); System.out.println("xxx"+result);
        assertEquals(expectedResult, result);

        instance.nextToken();
        expectedResult = "+";
        result = instance.yytext();System.out.println("xxx"+result);
        assertEquals(expectedResult, result);

        instance.nextToken();
        expectedResult = "17";
        result = instance.yytext();
        assertEquals(expectedResult, result);

        instance.nextToken();
        expectedResult = "*";
        result = instance.yytext();
        assertEquals(expectedResult, result);

        instance.nextToken();
        expectedResult = "7";
        result = instance.yytext();
        assertEquals(expectedResult, result);
    }
		
		//now we will test the nextToken() in our scanner against preset expectations
		
	    /**
	     * Test of nextToken method, of class ExpScanner.
	     */
	    @Test
	    public void testNextToken() throws Exception 
	    
	    {
	        System.out.println("Testing the nextToken() function");
	        
	        FileInputStream fis = null;
	        try 
	        {
	            fis = new FileInputStream(".\\SampleInput\\ScannerSampleInput.pas");
	        } 
	        catch (FileNotFoundException ex) 
	        {
	        }
	        InputStreamReader isr = new InputStreamReader( fis);
	        
	        
	        myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
	        
	        
	        TokenType expectedResult = TokenType.NUMBER;
	        TokenType result = instance.nextToken().getType();
	        assertEquals(expectedResult, result);

	        expectedResult = TokenType.PLUS;
	        result = instance.nextToken().getType();
	        assertEquals(expectedResult, result);

	        expectedResult = TokenType.NUMBER;
	        result = instance.nextToken().getType();
	        assertEquals(expectedResult, result);

	        expectedResult = TokenType.MULTIPLY;
	        result = instance.nextToken().getType();
	        assertEquals(expectedResult, result);

	        expectedResult = TokenType.NUMBER;
	        result = instance.nextToken().getType();
	        assertEquals(expectedResult, result);

	    }
}
