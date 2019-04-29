package tests;
import java.io.File;
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
	 * test the input file and throw exception here
	 * will open file if possible and 
	 *  * @throws IOException : if file is not readable
	 */
	public boolean testFile(File input)
	{
		 
		File file = input;
                 
		try (FileInputStream fis = new FileInputStream(file)) 
		{
			System.out.println("Testing Input Total file size to read (in bytes) : "+ fis.available());

			int content;
			System.out.print("Content of file is : ");
			while ((content = fis.read()) != -1) 
			{
			// convert to char and display it
			System.out.print((char) content);
			}
			return true;
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
			System.out.println("Unreadable file");
			return false;
		}
	
	}
	    
		/**
		 * IN the following two methods we will test that the myScanner with sample input 
		 * and check if yytext()/nextToken function is interpreting the tokens.
		 */
		
	    @Test
	    public void testYytext() throws IOException 
	    {
	    	 
	    	System.out.println("**********************Testing the yytext() ");
	    	 
	        //Sample Input
        	//we have this exp "455*7/4=7" in the input file
        	// we expect 7 tokens from the input expression 
        	// 455, *, 7, /, 4, =, 7 are the expected tokens from yytest
	        File file1 = new File("test/scanner/sample1.txt");
	       
	        	if (testFile(file1)) 
	        	{
		        	FileInputStream fis =  new FileInputStream(file1);
		        	InputStreamReader isr = new InputStreamReader( fis);
		       	        
		        	myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
		              
		        	instance.nextToken();
		        	String expectedResult = "455";
		        	String result = instance.yytext();
		        	System.out.println("yytext1 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
		        	//assertEquals(expectedResult, result);
	
		        	instance.nextToken();
		        	expectedResult = "*";
		        	result = instance.yytext();
		        	System.out.println("yytext2 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
		        	assertEquals(expectedResult, result);
		        	
		        	instance.nextToken();
		        	expectedResult = "7";
		        	result = instance.yytext();
		        	System.out.println("yytext3 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
		        	assertEquals(expectedResult, result);
	
		        	instance.nextToken();
		        	expectedResult = "/";
		        	result = instance.yytext();
		        	System.out.println("yytext4 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
		        	assertEquals(expectedResult, result);
	
		        	instance.nextToken();
		        	expectedResult = "4";
		        	result = instance.yytext();
		        	System.out.println("yytext5 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        		assertEquals(expectedResult, result);
	           
	        		instance.nextToken();
	        		expectedResult = "=";
	        		result = instance.yytext();
	        		System.out.println("yytext6 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        		assertEquals(expectedResult, result);
	        	
	        		instance.nextToken();
	        		expectedResult = "7";
	        		result = instance.yytext();
	        		System.out.println("yytext7 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        		assertEquals(expectedResult, result);
	        	}
	        }
	    
	    
	    
	    /**
	     * Test of nextToken method, of class Scanner.
	     * we will test the nextToken() in our scanner against preset expectations
	     */
	    @Test
	    public void testNextToken() throws Exception 
	    {
	        System.out.println("**********************Testing the nextToken()");
	        
	        //Sample Input
        	//we have this exp "455*7/4=7" in the input file
        	// we expect 7 tokens from the input expression 
        	// Program, Lesson1, ;, Var, Num1, Num2, Sum, :, Integer, ;,   are the expected tokens from nexttoken
	        File file2 = new File("test/scanner/sample1.txt");
	        
	        if (testFile(file2)) 
	        {
	        
	        	FileInputStream fis =  new FileInputStream(file2);
	        	InputStreamReader isr = new InputStreamReader( fis);
        	        
	        	myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
	        
	        	
	        	TokenType expectedResult = TokenType.NUMBER;
	        	TokenType result = instance.nextToken().getType();
	        	System.out.println("testNextToken1 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.MULTIPLY;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken2 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result); 
	        	
	        	expectedResult = TokenType.NUMBER;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken3 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.DIVIDE;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken4 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.NUMBER;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken5 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        	
	        	expectedResult = TokenType.EQUAL;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken6 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        	
	        	expectedResult = TokenType.NUMBER;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken7 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        }

	  }
	    
	   
		
		   /**
	     * Test of nextToken method, of class Scanner.
	     * we will test the nextToken() in our scanner against preset expectations
	     */
	    @Test
	    public void testNextTokenIdentifiers() throws Exception 
	    {
	        System.out.println("**********************Testing IDs()");
	        
	        //Sample Input
        	//we have this exp "455*7/4=7" in the input file
        	// we expect 7 tokens from the input expression 
        	// Program, Lesson1, ;, Var, Num1, Num2, Sum, :, Integer, ;,   are the expected tokens from nexttoken
	        File file2 = new File("test/scanner/sample2.txt");
	         
	        if (testFile(file2)) 
	        {
	        
	        	FileInputStream fis =  new FileInputStream(file2);
	        	InputStreamReader isr = new InputStreamReader( fis);
        	        
	        	myScannerfromJFLEX instance = new myScannerfromJFLEX(isr);
	        	
	        	//instance.nextToken();
	        	//String expectedResult1 = "Var";
	        	//String result1 = instance.yytext();
	        	
	        
	        	TokenType expectedResult = TokenType.ID;
	        	TokenType result = instance.nextToken().getType();
	        	System.out.println("testNextToken1 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.ID;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken2 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result); 
	        	
	        	expectedResult = TokenType.SEMICOLON;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken3 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.ID;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken4 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);

	        	expectedResult = TokenType.ID;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken5 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        	
	        	expectedResult = TokenType.COMMA;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken6 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        	
	        	expectedResult = TokenType.ID;
	        	result = instance.nextToken().getType();
	        	System.out.println("testNextToken7 Result Found: "+result+" [VS] Expected Result: "+ expectedResult);
	        	assertEquals(expectedResult, result);
	        }

	  }
}
