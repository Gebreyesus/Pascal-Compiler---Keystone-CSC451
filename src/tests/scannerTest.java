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
        System.out.println("yytext");
        FileInputStream fis = null;
        try 
        {
            fis = new FileInputStream("C:\\\\Users\\\\betea\\\\Desktop\\\\Augsburg Projects folder\\\\0000 Git Projects\\\\Compilers II\\\\gebruCompiler\\\\Compilerproj\\\\src\\\\tests\\\\SampleInput\\\\ScannerSampleInput.pas");
        } catch (FileNotFoundException ex) 
        {
        }
        InputStreamReader isr = new InputStreamReader( fis);
        myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
        
        instance.nextToken();
        String expResult = "34";
        String result = instance.yytext(); System.out.println("xxx"+result);
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "+";
        result = instance.yytext();System.out.println("xxx"+result);
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "17";
        result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "*";
        result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "7";
        result = instance.yytext();
        assertEquals(expResult, result);
    }
		
		//now we will test the nextToken() in our scanner against preset expectations
		
		
		
	    /**
	     * Test of nextToken method, of class ExpScanner.
	     */
	    @Test
	    public void testNextToken() throws Exception 
	    
	    {
	        System.out.println("nextToken");
	        FileInputStream fis = null;
	        try {
	            fis = new FileInputStream("C:\\Users\\betea\\Desktop\\Augsburg Projects folder\\0000 Git Projects\\Compilers II\\gebruCompiler\\Compilerproj\\src\\tests\\SampleInput\\ScannerSampleInput.pas");
	        } catch (FileNotFoundException ex) {
	        }
	        InputStreamReader isr = new InputStreamReader( fis);
	        
	        
	        myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
	        
	        TokenType expResult = TokenType.NUMBER;
	        TokenType result = instance.nextToken().getType();
	        assertEquals(expResult, result);

	        expResult = TokenType.PLUS;
	        result = instance.nextToken().getType();
	        assertEquals(expResult, result);

	        expResult = TokenType.NUMBER;
	        result = instance.nextToken().getType();
	        assertEquals(expResult, result);

	        expResult = TokenType.MULTIPLY;
	        result = instance.nextToken().getType();
	        assertEquals(expResult, result);

	        expResult = TokenType.NUMBER;
	        result = instance.nextToken().getType();
	        assertEquals(expResult, result);

	    }
}
