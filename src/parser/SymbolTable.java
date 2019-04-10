package parser;

import java.util.LinkedHashMap;

/**
 * create hasmap to serve as symbol table
 * We will keep track of all Identifiers of a type found
 * @author Beteab Gebru
 */
public class SymbolTable 
{
	private LinkedHashMap<String, SymbolType> mySymbolTable;
	private LinkedHashMap<String, Integer> arrayTable;

	/**
	 * Constructor for symbol table
	 */
	public SymbolTable() 
	{
		mySymbolTable = new LinkedHashMap<>();
		arrayTable = new LinkedHashMap<>();
	}

	/**
	 * recognised symbol with its attributes is stored
	 * @param string	     the name of the symbol
	 * @param SymbolType     the type of the symbol
	 */
	public void add(String symbolName, SymbolType type) 
	{
		mySymbolTable.put(symbolName, type);
	}

	/**
	 * adding a program name 
	 * @param the  name of the program symbol
	 */
	public void addProgramName(String symbolName) 
	{
		this.add(symbolName, SymbolType.PROGRAMTYPE);
	}

	/**
	 * adds the variable name
	 * @param the  name of the variable symbol
	 */
	public void addVariableName(String symbolName) 
	{
		this.add(symbolName, SymbolType.VARIABLETYPE);
	}

	/**
	 * function to add the function name
	 * @param the  name of the function symbol
	 */
	public void addFunctionName(String symbolName) 
	{
		this.add(symbolName, SymbolType.FUNCTIONTYPE);
	}

	/**
	 * adds the procedure name
	 * @param the name of the procedure symbol
	 */
	public void addProcedureName(String symbolName) 
	{
		this.add(symbolName, SymbolType.PROCEDURETYPE);
	}

	/**
	 * adds the array name
	 * @param the name of the array symbol
	 */
	public void addArrayName(String symbolName) 
	{
		this.add(symbolName, SymbolType.ARRAYTYPE);
	}

	/**
	 * adds an array name and size to the array table
	 * @param name of array
	 * @param size of array
	 */
	public void addArrayNameTable(String symbolName, Integer size) 
	{
		this.arrayTable.put(symbolName, size);
	}

	/**
	 * determines whether or not the symbol is a program
	 * @param the  name of the symbol to be determined
	 * @return whether or not the symbol is a program
	 */
	public Boolean isProgramName(String symbolName) 
	{
		Boolean check;

		if (mySymbolTable.containsKey(symbolName) && mySymbolTable.get(symbolName) == SymbolType.PROGRAMTYPE) 
		{
			check = true;
		} 
		else 
		{
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a variable
	 * @param the name of the symbol to be determined
	 * @return whether or not the symbol is a variable
	 */
	public Boolean isVariableName(String symbolName)
	{
		Boolean check;
		if (mySymbolTable.containsKey(symbolName) && mySymbolTable.get(symbolName) == SymbolType.VARIABLETYPE) 
		{
			check = true;
		} 
		else 
		{
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a function
	 * @param the name of the symbol to be determined
	 * @return whether or not the symbol is a function
	 */
	public Boolean isFunctionName(String symbolName) 
	{
		Boolean check;
		if (mySymbolTable.containsKey(symbolName) && mySymbolTable.get(symbolName) == SymbolType.FUNCTIONTYPE) 
		{
			check = true;
		} 
		else 
		{
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a procedure
	 * @param the name of the symbol to be determined
	 * @return whether or not the symbol is a procedure
	 */
	public Boolean isProcedureName(String symbolName) 
	{
		Boolean check;
		if (mySymbolTable.containsKey(symbolName) && mySymbolTable.get(symbolName) == SymbolType.PROCEDURETYPE) 
		{
			check = true;
		} 
		else 
		{
			check = false;
		}
		return check;
	}

	/**
	 * returns true if given symbol is an array
	 * @param the name of the symbol to be determined
	 * @return whether or not the symbol is an array
	 */
	public Boolean isArrayName(String symbolName) 
	{
		Boolean check;
		if (mySymbolTable.containsKey(symbolName) && mySymbolTable.get(symbolName) == SymbolType.ARRAYTYPE) 
		{
			check = true;
		} 
		else 
		{
			check = false;
		}

		return check;

	}

	/**
	 * gets the symbol table (linkedhashmap) 
	 * @return the symbol table
	 */
	public LinkedHashMap<String, SymbolType> getTable() 
	{
		return mySymbolTable;
	}

	public LinkedHashMap<String, Integer> getArrayTable() 
	{
		return arrayTable;
	}

	/**
	 * prints the symbol tablesymbolTableonsole
	 * @throws FileNotFoundException
	 */
	public SymbolType getType(String symbolName) 
	{
		return mySymbolTable.get(symbolName);
	}
	
	/**
	 * gets the array size for given array name
	 * @param name of array
	 * @return size of the array
	 */
	public Integer getArraySize(String symbolName) 
	{
		return arrayTable.get(symbolName);
	}
	
	/**
	 * Will print out contents of our symbol table 
	 */
	public String toString() 
	{
		String symTableString = "Symbol Table:";
		for (String symbolName : mySymbolTable.keySet()) 
		{
			String key = symbolName.toString();
			String value = mySymbolTable.get(symbolName).toString();
			symTableString += "\n  " + value + ": " + key;
		}
		return symTableString;
	}

}
