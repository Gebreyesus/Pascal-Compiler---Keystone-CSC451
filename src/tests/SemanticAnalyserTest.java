package tests;


import static org.junit.Assert.*;
import syntaxtree.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;
import semanticanalyser.SemanticAnalyser;
/**
 * 
 * @author Beteab Gebru
 */
public class SemanticAnalyserTest 
{
	
	
	@Test
	public void test() 
	{

		//  pass test - valid program test
		
		Parser parse = new Parser("test/parser/program_pass.pas", true);//bitcoin.pas
		SemanticAnalyser semAnalysis = new SemanticAnalyser(parse.program(), parse.getSymbolTable());

		String parseTree = semAnalysis.analyze().indentedToString(0);

		String expectedResult = "Program: BitcoinConversion\n" 
				+ "|-- Declarations\n"
				+ "|-- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- Name: yen (Expression Type: INTEGER)\n"
				+ "|-- --- Name: bitcoins (Expression Type: INTEGER)\n" 
				+ "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" 
				+ "|-- --- Assignment\n"
				+ "|-- --- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 1000000 (Expression Type: INTEGER)\n" 
				+ "|-- --- Assignment\n"
				+ "|-- --- --- Name: yen (Expression Type: INTEGER)\n"
				+ "|-- --- --- operation: MULTIPLY (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 102 (Expression Type: INTEGER)\n" 
				+ "|-- --- Assignment\n"
				+ "|-- --- --- Name: bitcoins (Expression Type: INTEGER)\n"
				+ "|-- --- --- operation: DIVIDE (Expression Type: null)\n"
				+ "|-- --- --- --- Name: potato (Expression Type: null)\n"
				+ "|-- --- --- --- Value: 8500 (Expression Type: INTEGER)\n" 
				+ "|-- --- Write\n"
				+ "|-- --- --- Name: dollars (Expression Type: INTEGER)\n" 
				+ "|-- --- Write\n"
				+ "|-- --- --- Name: yen (Expression Type: INTEGER)\n" 
				+ "|-- --- Write\n"
				+ "|-- --- --- Name: bitcoins (Expression Type: INTEGER)\n" 
				+ "";

		assertEquals(expectedResult, parseTree);

		// fail test - bad pascal
		
		parse = new Parser("test/syntaxtree/illegal.pas", true);
		semAnalysis = new SemanticAnalyser(parse.program(), parse.getSymbolTable());
		parseTree = semAnalysis.analyze().indentedToString(0);
		if (parseTree == expectedResult)
			assertTrue(false);
		else
			assertTrue(true);
	}
	

} 
