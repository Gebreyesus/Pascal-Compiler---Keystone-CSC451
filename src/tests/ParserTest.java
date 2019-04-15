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
		Parser happyPascal = new Parser("test/parser/program_test_pass.pas", true);
		Parser badPascal = new Parser("test/parser/program_test_fail.pas", true);

		try 
		{
			happyPascal.program();
			System.out.println("Passed tests - valid P code");
		} 
		catch (Exception e) 
		{
			ValidCode = false;
			System.out.println("Failed to recognise P Code");
		}

		try 
		{
			happyPascal.program();
			System.out.println("Fail to find a valid program");
			ValidCode = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Program found");
		}

		assertTrue(ValidCode);
	}
	
 
	@Test
	public void testDeclarations() 
	{
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/declaration_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/declaration_test_fail.pas", true);

		// should pass
		try 
		{
			pPass.declarations();
			System.out.println("Declaration Passed");
		} 
		catch (Exception e) 
		{
			allTrue = false;
			System.out.println("No declaration found");
		}

		// should catch
		try 
		{
			pFail.declarations();
			System.out.println("Declaration failed");
			allTrue = false;
		} catch (Exception e) 
		{
			System.out.println("Declaration Passed");
		}
		System.out.println();
		assertTrue(allTrue);
	}
	 
	@Test
	public void testSubprogramDeclarations() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/subprogramdeclarations_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/subprogramdeclarations_test_fail.pas", true);

		// should pass
		try {
			pPass.subprogram_declaration();
			System.out.println("Subprogram Declaration pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Subprogram Declaration part fail");
		}

		// should catch
		try {
			pFail.subprogram_declaration();
			System.out.println("Subprogram Declaration fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Subprogram Declaration pass");
		}

		System.out.println();
		assertTrue(allTrue);

	}

}
     
   

   
   