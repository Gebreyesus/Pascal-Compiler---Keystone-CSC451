package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.SymbolTable;
import parser.SymbolType;

/**
 * Testing our symbol table 
 * we will populate the table and do tests on the functions
 * @author Beteab Gebru
 */

public class SymbolTableTest 
{

	SymbolTable table = new SymbolTable();
	
	//constructor test
	@Before
	public void setUp() throws Exception 
	{
		this.table = new SymbolTable();
	}

	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void addTest() 
	{
		
		
		// Populating a sample table for assertion test
		table.add("programName", SymbolType.PROGRAMTYPE);
		table.add("variableName", SymbolType.VARIABLETYPE);
		table.add("functionName", SymbolType.FUNCTIONTYPE);
		table.add("procedureName", SymbolType.PROCEDURETYPE);
		table.add("arrayName", SymbolType.ARRAYTYPE);

		// tests must return true
		assertTrue(table.isProgramName("programName"));
		assertTrue(table.isVariableName("variableName"));
		assertTrue(table.isFunctionName("functionName"));
		assertTrue(table.isProcedureName("procedureName"));
		assertTrue(table.isArrayName("arrayName"));

		// tests must return false
		assertFalse(table.isProgramName("arrayName"));
		assertFalse(table.isVariableName("functionName"));
		assertFalse(table.isFunctionName("variableName"));
		assertFalse(table.isProcedureName("programName"));
		assertFalse(table.isArrayName("procedureName"));

		table.toString();
	}

	
	@Test
	public void addProgramNameTest() 
	{
		table.addProgramName("programName");

		// must return true
		assertTrue(table.isProgramName("programName"));

		// must return false
		assertFalse(table.isArrayName("programName"));

	}


	@Test
	public void addFunctionNameTest() 
	{

		table.addFunctionName("functionName");

		// must return true
		assertTrue(table.isFunctionName("functionName"));

		// must return false
		assertFalse(table.isProcedureName("functionName"));

	}
	

	@Test
	public void addProcedureNameTest() 
	{

		table.addProcedureName("procedureName");

		// must return true
		assertTrue(table.isProcedureName("procedureName"));

		// must return false
		assertFalse(table.isProgramName("procedureName"));

	}

	@Test
	public void addArrayNameTest() 
	{

		table.addArrayName("arrayName");

		// must return true
		assertTrue(table.isArrayName("arrayName"));

		// must return false
		assertFalse(table.isProgramName("arrayName"));

	}
	
	@Test
	public void addVariableNameTest() 
	{

		table.addVariableName("variableName");

		// must return true
		assertTrue(table.isVariableName("variableName"));

		// must return false
		assertFalse(table.isProgramName("variableName"));

	}
}
