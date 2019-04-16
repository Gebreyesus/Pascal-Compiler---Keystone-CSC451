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
		boolean validCode = true;
		Parser happyPascal = new Parser("test/parser/program_pass.pas", true);
		Parser badPascal = new Parser("test/parser/program_fail.pas", true);

		try 
		{
			happyPascal.program();
			System.out.println("Passed tests - valid pascal program()");
		} 
		catch (Exception e) 
		{
			validCode = false;
			System.out.println("Failed to recognise Pascal program()");
		}

		try 
		{
			badPascal.program();
			System.out.println("Failed to find a valid program()");
			validCode = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Program() found\n");
		}

		assertTrue(validCode);
	}
	
 
	@Test
	public void testDeclarations() 
	{
		boolean allPassed = true;

		Parser happyPascal = new Parser("test/parser/declaration_pass.pas", true);
		Parser badPascal = new Parser("test/parser/declaration_fail.pas", true);

		// pass
		try 
		{
			happyPascal.declarations();
			System.out.println("Valid Declaration() found");
		} 
		catch (Exception e) 
		{
			allPassed = false;
			System.out.println("failed to find valid Declarations()");
		}

		try 
		{
			badPascal.declarations();
			System.out.println("failed to find valid Declarations()");
			allPassed = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Valid Declaration() found\\n");
		}
		System.out.println();
		assertTrue(allPassed);
	}
	 
	@Test
	public void testSubprogramDeclarations() 
	{
		boolean allPassed = true;

		Parser passTest = new Parser("test/parser/subprogramdeclarations_pass.pas", true);
		Parser failTest = new Parser("test/parser/subprogramdeclarations_fail.pas", true);
 
		try 
		{
			passTest.subprogram_declaration();
			System.out.println(" Valid Subprogram() found");
		} 
		catch (Exception e) 
		{
			allPassed = false;
			System.out.println("failed to find valid Subprogram  ");
		}
 
		try 
		{
			failTest.subprogram_declaration();
			System.out.println("failed to find valid Subprogram() ");
			allPassed = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Valid Subprogram() found\\n");
		}

		System.out.println();
		assertTrue(allPassed);

	}
	
	@Test
	public void testStatement() 
	{
		boolean allPassed = true;
		
		Parser happyPascal = new Parser("test/parser/statement_pass.pas", true);
		Parser badPascal = new Parser("test/parser/statement_fail.pas", true);

		happyPascal.addToTable("someVar", SymbolType.VARIABLETYPE);
		
		try 
		{
			happyPascal.statement();
			System.out.println(" Valid Statement() found");
		} 
		catch (Exception e) 
		{
			allPassed = false;
			System.out.println("failed to find valid Statement()");
		}

		try 
		{
			badPascal.statement();
			System.out.println("failed to find valid Statement()");
			allPassed = false;
		} catch (Exception e) {
			System.out.println("Valid Statement() found\\n");
		}

		System.out.println();
		assertTrue(allPassed);
	}

 
	@Test
	public void testSimpleExpression() 
	{
		boolean allPassed = true;

		Parser happyPascal = new Parser("test/parser/simpleexpression_pass.pas", true);
		Parser badPascal = new Parser("test/parser/simpleexpression_fail.pas", true);

		// should pass
		try 
		{
			happyPascal.simple_expression();
			System.out.println("Valid SimpleExpression() found");
		} 
		catch (Exception e) 
		{
			allPassed = false;
			System.out.println("failed to find valid SimpleExpression()");
		}
 
		try 
		{
			badPascal.simple_expression();
			System.out.println("failed to find valid SimpleExpression()");
			allPassed = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Valid SimpleExpression() found\n");
		}

		System.out.println();
		assertTrue(allPassed);
	}

 
	@Test
	public void testFactor() 
	{
		boolean allPassed = true;

		Parser happyPascal = new Parser("test/parser/factor_pass.pas", true);
		Parser badPascal = new Parser("test/parser/factor_fail.pas", true);

		// should pass
		try {
			happyPascal.factor();
			System.out.println("Valid Factor() found");
		} 
		catch (Exception e) 
		{
			allPassed = false;
			System.out.println("failed to find valid Factor()");
		}

		// should catch
		try 
		{
			badPascal.factor();
			System.out.println("failed to find valid Factor()");
			allPassed = false;
		} 
		catch (Exception e) 
		{
			System.out.println("Valid Factor() found\n");
		}

		System.out.println();
		assertTrue(allPassed);
	}

}
     
   

   
   