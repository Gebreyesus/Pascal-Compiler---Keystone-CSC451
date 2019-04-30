package tests;

import java.io.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.*;
import scanner.*;
import syntaxtree.*;
 

public class SyntaxTreeTest 
{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 *  comparing our indented string against a preset expectation
	 *	we will use the overloaded tostring method to print our tree
	 *	indentations will signify level on the tree and hence the appearance of our pascal code
	 */
	@Test
	public void test() 
	{
		
		// testing for a program tree --> valid program should be detected
		//expectedResult is how our program should understand 
		// the program input file (bitcoin.pascal)
		Parser parse = new Parser("test/syntaxtree/bitcoin Good Pascal Program.pas", true);
		String parseTree = parse.program().indentedToString(0);
		System.out.println("Sytax PASS TEST");
		String expectedResult =  "Program: BitcoinConversion\n" 
							   + "|-- Declarations\n" 
							   + "|-- --- Name: dollars\n"
							   + "|-- --- Name: yen\n" 
							   + "|-- --- Name: bitcoins\n" 
							   + "|-- SubProgramDeclarations\n"
							   + "|-- Compound Statement\n" 
							   + "|-- --- Assignment\n" 
							   + "|-- --- --- Name: dollars\n"
							   + "|-- --- --- Value: 1000000\n" 
							   + "|-- --- Assignment\n" 
							   + "|-- --- --- Name: yen\n"
							   + "|-- --- --- Operation: MULTIPLY\n" 
							   + "|-- --- --- --- Name: dollars\n"
							   + "|-- --- --- --- Value: 102\n" 
							   + "|-- --- Assignment\n" 
							   + "|-- --- --- Name: bitcoins\n"
							   + "|-- --- --- Operation: DIVIDE\n" 
							   + "|-- --- --- --- Name: potato\n" 
							   + "|-- --- --- --- Value: 8500\n"
							   + "|-- --- Write\n" 
							   + "|-- --- --- Name: dollars\n" 
							   + "|-- --- Write\n" 
							   + "|-- --- --- Name: yen\n"
							   + "|-- --- Write\n" 
							   + "|-- --- --- Name: bitcoins\n" + "";
		System.out.println("Sytax PASS TEST****");
		assertEquals(expectedResult, parseTree);
		
		// testing for a program tree --> valid program should be detected
		System.out.println("Sytax FAIL TEST");
		parse = new Parser("test/syntaxtree/bitcoin Bad Pascal Program.pas", true);
		parseTree = parse.program().indentedToString(0);
		if (parseTree == expectedResult)
		{
			System.out.println("*************sytax text");
			assertTrue(false);
		}else
			assertTrue(true);

		
	}
	
	
}
