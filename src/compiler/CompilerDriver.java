package compiler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import codegenerator.CodeGenerator;
import parser.Parser;
import semanticanalyser.SemanticAnalyser;
import syntaxtree.ProgramNode;

/**
 * driver for the compiler, creates and prints MIPs assembly code
 * Will write out the  symbol table, and syntax tree into files
 * 
 @author Beteab Gebru
 */
public class CompilerDriver 
{
	
	// Instance Variables
	public static Parser parse;
	public static String filename;
	public static SemanticAnalyser semAnalysis;
	public static CodeGenerator codeGen;

	public static void main(String[] args) 
	{

		// taking in the argument which should be a filename 
		filename = args[0];
		parse = new Parser(filename, true);//"test/parser/program_pass.pas"
		
		String PascalCode = filename.substring(0, filename.lastIndexOf('.')); 
		// "test/parser/program_pass.pas"

		// Creates symbol table and parse tree strings
		ProgramNode progNode = parse.program();

		// creates semantic analysis object given the program node
		semAnalysis = new SemanticAnalyser(progNode, parse.getSymbolTable());

		// progNode is now analyzed using semantic analysis and made out to a string
		progNode = semAnalysis.analyze();		
		String parseTree = progNode.indentedToString(0);

		// symbol table is put into a string
		String symbolTable = parse.getSymbolTableStr();

		// prints the tree and the symbol table to the console
		//System.out.print(parseTree);
		//System.out.print(symbolTable);

		// Code generation - creates asm code from our progNode
		codeGen = new CodeGenerator(progNode, parse.getSymbolTable());
		codeGen.generate();
		
		// gets the string containing the asm code
		String asmCode = codeGen.getAsmCode();

		//System.out.println(parseTree);
		//System.out.println(asmCode);

		// prints the symbol table to a text file called [inputname].table
		PrintWriter symTabWriter;
		try 
		{
			symTabWriter = new PrintWriter(PascalCode + ".table");
			symTabWriter.println(symbolTable);
			symTabWriter.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("unable to create file - Table file");
			e.printStackTrace();
		}

		// prints the parse tree to a text file called [input name].tree
		PrintWriter pareseTreeWriter;
		try 
		{
			pareseTreeWriter = new PrintWriter(PascalCode + ".tree");
			pareseTreeWriter.println(parseTree);
			pareseTreeWriter.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("unable to create file - tree output file");
			e.printStackTrace();
		}

		// prints the parse tree to a text file called [input name].tree
		PrintWriter asmWriter;
		try 
		{
			asmWriter = new PrintWriter(PascalCode + ".asm");
			asmWriter.println(asmCode);
			asmWriter.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("unable to create file - asm output file");
			e.printStackTrace();
		}

	}

}
