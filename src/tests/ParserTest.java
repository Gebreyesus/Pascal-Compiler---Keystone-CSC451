package tests;

import scanner.TokenType;
import parser.Parser;
import parser.SymbolType;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
    * TESTING using two input files for every test 
    * Good pascal code - should produce no errors
    * Bad Pascal Code - we should fail to recognise it as valid
 *  @author Beteab Gebru
 */

public class ParserTest 
{    
	/**
	 * 
	 * @throws Exception
	 */
   @BeforeClass
   public static void setUpClass()    {   }
   
	/**
	 * 
	 * @throws Exception
	 */
   @AfterClass
   public static void tearDownClass()    {   }
   

	@Test
	public void testProgram() 
	{
		boolean ValidCode = true;
		Parser HappyPascal = new Parser("GoodCode", true);
		Parser badPascal = new Parser("BadCode", true);

		try 
		{
			HappyPascal.program();
			System.out.println("Passed tests - valid P code");
		} 
		catch (Exception e) 
		{
			ValidCode = false;
			System.out.println("Failed to recognise P Code");
		}

		try 
		{
			HappyPascal.program();
			System.out.println("Program part 2 fail");
			ValidCode = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Program part 2 pass");
		}

		
		assertTrue(ValidCode);

	}


}
     
   

   
   