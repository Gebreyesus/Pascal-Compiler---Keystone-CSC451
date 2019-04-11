package parser;

import java.io.*; 
import java.util.*;
import scanner.*;
import syntaxtree.*;
/**
 * The parser will look at series of tokens and decide if they form expressions
 * parser will read an input file containing sample pascal function 
 * It will then call the top-level function  code <code>program()</code>.
 * If the functions returns without an error, the file
 * contains an acceptable pascal code/expression
 * @Author Beteab Gebru
 */
public class Parser 
{
    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////
    private Token lookAhead; 		//peeks at next token
    private myScannerfromJFLEX myScanner; 	//grabs tokens from text
    public SymbolTable symTable = new SymbolTable(); //syntax tree buit from the tokens in the table
    public ProgramNode prog;
    private int arrySize = 0;
    private String lexim = new String(); 
    boolean isArray = false;
    ArrayList<String> allVariableNames = new ArrayList<String>();
   
    ///////////////////////////////
    //       Constructors
    ///////////////////////////////  
   
    /**
     * Input could be string or a file to be read
     * making sure the file/textInput is readable and valid
     * @param input, the string input/name of input file.
     * @param inputIsaFile, true if the string is a file name
     */
    public Parser( String input, boolean inputIsaFile) 
    {
        if(inputIsaFile) 
        {
        	FileInputStream fis = null;
        	try 
        		{
        			fis = new FileInputStream(input);
        		} 
        	catch (FileNotFoundException ex) 
        		{
        			error( "File unreadable/doesnt exist");
        		}
        	//using valid file to feed myScanner - identify tokens
        	InputStreamReader isr = new InputStreamReader( fis);
        	myScanner = new myScannerfromJFLEX( isr);    
        }
        else //if it is not file name - read the string directly
        {
         	myScanner = new myScannerfromJFLEX( new StringReader( input));
        }
        
        try 
        {
        	lookAhead = myScanner.nextToken();
        } 
        catch (IOException ex) 
        {
        	error( "bad string/file(doesnt contain tokens)");
        }
    }
    
    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    // Production rule functions
 	/**
 	 * If pascal code passes as valid, create the create a head Node for the sytax-tree
 	 * @return ProgramNode : 
 	 */
 	public ProgramNode program() 
 	{
 		match(TokenType.PROGRAM);
 		lexim = this.lookAhead.getLexeme();
 		String progName = lexim;
 		match(TokenType.ID);
 		ProgramNode progNode = new ProgramNode(progName);
 		symTable.addProgramName(lexim);
 		match(TokenType.SEMICOLON);
 		DeclarationsNode declarations = declarations();
 		SubProgramDeclarationsNode subProgramDeclarations = subprogram_declarations();
 		CompoundStatementNode compoundStatement = compound_statement();
 		match(TokenType.PERIOD);
 		
 		
 		
 		//set and return top node for a possible tree
 		progNode.setVariables(declarations);
 		progNode.setMain(compoundStatement);
 		progNode.setFunctions(subProgramDeclarations);
 		//progNode.setAllVarNames(allVarNames);
 		return progNode; 
 	}
 	
 	/**
 	 *  
	 * @return CompoundStatementNode :
	 */
	public CompoundStatementNode compoundstatement() 
	{
		CompoundStatementNode comStatementNode = new CompoundStatementNode();
		match(TokenType.BEGIN);
		comStatementNode = optional_statements();
		match(TokenType.END);
		return comStatementNode;
	}
	
	/**
	 * We check if we may have statments
	* @return CompoundStatementNode :
	 */
	public CompoundStatementNode optionalstatements() 
	{
		CompoundStatementNode comStatementNode = new CompoundStatementNode();
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.ID
				|| (this.lookAhead.getType() == TokenType.BEGIN) || (this.lookAhead.getType() == TokenType.IF)
				|| (this.lookAhead.getType() == TokenType.WHILE) || (this.lookAhead.getType() == TokenType.READ)
				|| (this.lookAhead.getType() == TokenType.WRITE))) 
		{
			comStatementNode.addAllStateNodes(statement_list());
		} 
		else {/* lambda option**/ }
		
		return comStatementNode;  
	}


	/**
	 * List of statements separated by semicolons 
	 * @return ArrayList<StatementNode>: 
	 */
	public ArrayList<StatementNode> statementlist() 
	{
		ArrayList<StatementNode> statNodeList = new ArrayList<StatementNode>();
		StatementNode statNode = statement();

		if (statNode != null) 
		{
			statNodeList.add(statNode);
		}
		if (lookAhead != null && (lookAhead.getType() == TokenType.SEMICOLON)) 
		{
			match(TokenType.SEMICOLON);
			statNodeList.addAll(statement_list());
		}
		
		return statNodeList; // else lambda option
	}
 
	/**
	 * taking in a subprogram_declaration or a list of 
	 * subprogram_declarations separated by commas
	 * @return SubProgramDeclarationsNode
	 */
	public SubProgramDeclarationsNode subProgramDeclarations() 
	{
		SubProgramDeclarationsNode mysubProgDecNode = new SubProgramDeclarationsNode();
		if (lookAhead != null && (lookAhead.getType() == TokenType.FUNCTION || 
				lookAhead.getType() == TokenType.PROCEDURE)) 
		{
			mysubProgDecNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMICOLON);
			mysubProgDecNode.addAll(subprogram_declarations().getSubProgs());
		}
		// else lambda option
		
		return mysubProgDecNode;
	}
	
	
	/**
	 * declaring a Var or list Variables of a specified type
	 * @return DeclarationsNode :
	 */
	public DeclarationsNode declarations() 
	{
		DeclarationsNode declarNode = new DeclarationsNode();
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.VAR)) 
		{
			match(TokenType.VAR);
			ArrayList<String> identifiersList = identifier_list();

			match(TokenType.COLON);
			DataType myType = type(identifiersList);
			
			if (!isArray) 
			{
				for (String varID : identifiersList) 
				{
					declarNode.addVariable(new VariableNode(varID, myType));
					if (! allVariableNames.contains(varID))
						  allVariableNames.add(varID);//add new variable declarations
				} 
			}
			
			if (isArray) //case of an array
			{
				for (String varID : identifiersList) 
				{
					declarNode.addVariable(new ArrayNode(varID, myType));

					if (!allVariableNames.contains(varID))
						 allVariableNames.add(varID);
					// removes array thats been designated a variable name
					symTable.getTable().remove(varID);
					// adds array as an arraytype
					symTable.addArrayName(varID);

					symTable.addArrayNameTable(varID, arrySize);

					isArray = false;
				}
			}
		  match(TokenType.SEMICOLON);
		  declarNode.addDeclarations(declarations());
		} 
		else 
		{			/* lambda option */  }
		

		return declarNode;
	}


	/**
	 * handling array of standard type
	 */
	public DataType type(ArrayList<String> idList) 
	{
		DataType t = null;
		int arrayStart = 0;
		int arrayEnd = 0;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ARRAY)) 
		{
			match(TokenType.ARRAY);
			match(TokenType.LEFT_ROUND_PAREN);

			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) 
			{
				arrayStart = Integer.parseInt(lookAhead.getLexeme());
				match(TokenType.INTEGER);
			} 
			else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL))
				match(TokenType.REAL);
        		match(TokenType.COLON);

			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) 
			{
				arrayEnd = Integer.parseInt(lookAhead.getLexeme());
				match(TokenType.INTEGER);
			} 
			else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL))
				match(TokenType.REAL);

			match(TokenType.RIGHT_ROUND_PAREN);
			match(TokenType.OF);
			t = standard_type();
			arrySize = arrayEnd - arrayStart;
			isArray = true;

		} 
		else if (lookAhead != null
				&& (lookAhead.getType() == TokenType.INTEGER || lookAhead.getType() == TokenType.REAL)) 
		{
			t = standard_type();

		} 
		else
			error("type");
		return t;
	}

	
//-----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * handling standard data type
	 * taking in a number, either an integer or a real
	 */
	public DataType standard_type() {
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.INTEGER)) {
			match(TokenType.INTEGER);
			return DataType.DATINTEGER;
		} else if (lookAhead != null && (this.lookAhead.getType() == TokenType.REAL)) {
			match(TokenType.REAL);
			return DataType.DATREAL;
		} else {
			error("in the standard_type function");
			return null;
		}
	}
 	
 	/**
	 * variable input or series of variable inputs
	 */
	public ArrayList<String> identifier_list() {
		ArrayList<String> idList = new ArrayList<>();
		lexi = this.lookAhead.getLexeme();
		String name = lexi;
		match(TokenType.ID);
		symTab.addVariableName(lexi);
		idList.add(name);
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.COMMA)) {
			match(TokenType.COMMA);
			idList.addAll(identifier_list());
		} else {
			// just the id option
		}
		return idList;

	}

 
//-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Executes the rule for the exp non-terminal symbol in
     * the expression grammar.
     */
    public void expression() 
    {
        terminal();
        exp_prime();
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the rule for the exp&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void exp_prime() 
    {
        if( lookAhead.getType() == TokenType.PLUS || 
                lookAhead.getType() == TokenType.MINUS ) 
        {
            addop();
            terminal();
            exp_prime();
        }
        else
        {
            // lambda option
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the rule for the addop non-terminal symbol in
     * the expression grammar.
     */
    public void addop() 
    {
        if( lookAhead.getType() == TokenType.PLUS) 
        {
            match( TokenType.PLUS);
        }
        else if( lookAhead.getType() == TokenType.MINUS) 
        {
            match( TokenType.MINUS);
        }
        else 
        {
            error( "Addop");
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     */
    public void terminal() 
    {
        factor();
        terminal_prime();
    }
 
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the rule for the term&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void terminal_prime() 
    {
        if( isMulop( lookAhead) )
        {
            mulop();
            factor();
            terminal_prime();
        }
        else
        {
            // lambda option
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Determines whether or not the given token is
     * a mulop token.
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    private boolean isMulop( Token token) 
    {
        boolean answer = false;
        if( token.getType() == TokenType.MULTIPLY || 
                token.getType() == TokenType.DIVIDE ) 
        {
            answer = true;
        }
        return answer;
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Executes the rule for the mulop non-terminal symbol in
     * the expression grammar.
     */
    public void mulop() 
    {
        if( lookAhead.getType() == TokenType.MULTIPLY) 
        {
            match( TokenType.MULTIPLY);
        }
        else if( lookAhead.getType() == TokenType.DIVIDE) 
        {
            match( TokenType.DIVIDE);
        }
        else 
        {
            error( "Mulop");
        }
    }
    
    /**
    * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     */

    /**
	 * an ID, or an ID with an expression surrounding by brackets or an
	 * expression_list surrounded by parenthesis or a number, a single expression or
	 * a 'NOT' factor
	 */
	public ExpressionNode factor() 
	{
		ExpressionNode exper = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID)) {
			String name = lookAhead.getLexeme();
			match(TokenType.ID);
			if (lookAhead != null && (lookAhead.getType() == TokenType.LEFTBRACKET)) {
				ArrayNode arrNode = new ArrayNode(name);
				match(TokenType.LEFTBRACKET);
				ExpressionNode temp = expression();
				arrNode.setExpNode(temp);
				match(TokenType.RIGHTBRACKET);
				return arrNode;
			} else if (lookAhead != null && (lookAhead.getType() == TokenType.LEFTPAR)) {
				FunctionNode funcNode = new FunctionNode(name);
				match(TokenType.LEFTPAR);
				funcNode.setExpNode(expression_list());
				match(TokenType.RIGHTPAR);
				return funcNode;
			} else {
				if (!allVarNames.contains(name))
					allVarNames.add(name);
				return new VariableNode(name);
			}
		} else if (lookAhead != null
				&& (lookAhead.getType() == TokenType.INTEGER || lookAhead.getType() == TokenType.REAL)) {

			if (lookAhead.getLexeme().contains("."))
				exper = new ValueNode(lookAhead.getLexeme(), DataType.DATREAL);
			else
				exper = new ValueNode(lookAhead.getLexeme(), DataType.DATINTEGER);

			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) {
				match(TokenType.INTEGER);
			} else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL)) {
				match(TokenType.REAL);
			}

		} else if (lookAhead != null && (lookAhead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			exper = expression();
			match(TokenType.RIGHTPAR);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.NOT)) {
			SignNode uoNode = new SignNode(TokenType.NOT);
			match(TokenType.NOT);
			uoNode.setExpression(factor());
			return uoNode;
		} else
			error(" in factor");
		return exper;
	}
	
    public void factor() 
    {
        // Executed this decision as a switch instead of an
        // if-else chain. Either way is acceptable.
        switch (lookAhead.getType()) 
        {
            case LEFT_ROUND_PAREN:
                match( TokenType.LEFT_ROUND_PAREN);
                exp();
                match( TokenType.LEFT_ROUND_PAREN);
                break;
            case NUMBER:
                match( TokenType.NUMBER);
                break;
            default:
                error("Factor");
                break;
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Matches the expected token.
     * This function will serve to check 
     * if lookAhead contains expected token 
     * based on the current token from scanner
     * @param expected The expected token type.
     */
    public void match( TokenType expected) 
    {
        System.out.println("Matching (" + expected + ") with token from lookAhead");
        
        if( this.lookAhead.getType() == expected) 
        {
            try 
            { //handling null value at end of file/string 
                this.lookAhead = myScanner.nextToken();
                if( this.lookAhead == null) 
                {
                    this.lookAhead = new Token( "End of File", null);
                }
            } 
            catch (IOException ex) 
            {
                error( "Scanner exception - End of file/string reached");
            }
        }
        else 
        {
            error("Match of - Expected token-> " + expected + " : found " + this.lookAhead.getType() + " instead.");
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    //returns a list of identifier strings (ie. variable and array names).
    //used to bulk declare VariableNodes in declaration sections. Calls
    //itself recursively until all IDs are matched.
    private ArrayList<String> identifier_list() 
    {
    	ArrayList<String> identifierList = new ArrayList<String>();
    	identifierList.add( lookAhead.getLexeme());
		match( TokenType.ID);
		if( lookAhead.getType() == TokenType.COMMA)
		{
			match( TokenType.COMMA);
			identifierList.addAll( identifier_list());
		}
		
		return identifierList;
    }

    
//------------------------------------------------------------------------------------------------------------------------------------
  //tests if token is a relational operator
  	private boolean isRelop( Token token)
  	{
  		boolean answer = false;
  		TokenType nextType = token.getType();
  		if( nextType == TokenType.EQUALITY_OPERATOR || nextType == TokenType.NOT_EQUAL || nextType == TokenType.LESS_THAN || 
  				nextType == TokenType.LESS_THAN_EQUAL_TO || nextType == TokenType.GREATER_THAN_EQUAL_TO || 
  				nextType == TokenType.GREATER_THAN)
  		{
  			answer = true;
  		}
  		return answer;
  }
    
    

    

	 

	/**
	 * variable input or series of variable inputs
	 */
	public ArrayList<String> identifier_list() {
		ArrayList<String> idList = new ArrayList<>();
		lexi = this.lookAhead.getLexeme();
		String name = lexi;
		match(TokenType.ID);
		symTab.addVariableName(lexi);
		idList.add(name);
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.COMMA)) {
			match(TokenType.COMMA);
			idList.addAll(identifier_list());
		} else {
			// just the id option
		}
		return idList;

	}

	
	

	/**
	 * taking in a subprogram_declaration or a series of subprogram_declarations
	 * separated by commas
	 */
	public SubProgramDeclarationsNode subprogram_declarations() {
		SubProgramDeclarationsNode subPorgDecNode = new SubProgramDeclarationsNode();
		if (lookAhead != null
				&& (lookAhead.getType() == TokenType.FUNCTION || lookAhead.getType() == TokenType.PROCEDURE)) {
			subPorgDecNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMI);
			subPorgDecNode.addall(subprogram_declarations().getSubProgs());
		}
		// else lambda case
		return subPorgDecNode;
	}

	/**
	 * calling a series of other production rules in order to get a proper order
	 */
	public SubProgramNode subprogram_declaration() {
		SubProgramNode subProgNode = subprogram_head();
		subProgNode.setVariables(declarations());
		subProgNode.setFunctions(subprogram_declarations());
		subProgNode.setMain(compound_statement());
		return subProgNode;
	}

	/**
	 * creating a function or a procedure
	 */
	public SubProgramNode subprogram_head() {
		SubProgramNode spNode = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.FUNCTION)) {
			match(TokenType.FUNCTION);
			String functName = lookAhead.getLexeme();
			spNode = new SubProgramNode(functName);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			symTab.addFunctionName(functName);
			match(TokenType.SEMI);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.PROCEDURE)) {
			match(TokenType.PROCEDURE);
			String procName = lookAhead.getLexeme();
			spNode = new SubProgramNode(procName);
			match(TokenType.ID);
			arguments();
			symTab.addProcedureName(procName);
			match(TokenType.SEMI);
		} else
			error("subprogram_head");
		return spNode;
	}

	/**
	 * takes in parameters inside of parenthesis
	 */
	public ArrayList<String> arguments() {
		ArrayList<String> args = new ArrayList<String>();
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			args = parameter_list();
			match(TokenType.RIGHTPAR);
		} else {
			// lambda option
		}

		return args;
	}

	/**
	 * takes in an ID and it's corresponding types or a series of parameters
	 * separated by semicolons
	 */
	public ArrayList<String> parameter_list() {
		ArrayList<String> idList = identifier_list();
		match(TokenType.COLON);
		type(idList);
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.SEMI)) {
			match(TokenType.SEMI);
			idList.addAll(parameter_list());
		} else {
			// just the first option
		}
		return paramList;
	}

	/**
	 * verifying the main code, starting with begin and ending with end
	 */
	public CompoundStatementNode compound_statement() {
		CompoundStatementNode comStatNode = new CompoundStatementNode();
		match(TokenType.BEGIN);
		comStatNode = optional_statements();
		match(TokenType.END);
		return comStatNode;
	}

	/**
	 * for statement_list or nothing (lamda)
	 */
	public CompoundStatementNode optional_statements() {
		CompoundStatementNode compStatNode = new CompoundStatementNode();
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.ID
				|| (this.lookAhead.getType() == TokenType.BEGIN) || (this.lookAhead.getType() == TokenType.IF)
				|| (this.lookAhead.getType() == TokenType.WHILE) || (this.lookAhead.getType() == TokenType.READ)
				|| (this.lookAhead.getType() == TokenType.WRITE))) {
			compStatNode.addAllStateNodes(statement_list());

		} else {
			// lambda option
		}
		return compStatNode; // not sure what to put here
	}

	/**
	 * statements or a series of statements separated by semicolons
	 */
	public ArrayList<StatementNode> statement_list() {
		ArrayList<StatementNode> statNodeList = new ArrayList<StatementNode>();

		StatementNode statNode = statement();

		if (statNode != null) {
			statNodeList.add(statNode);
		}

		if (lookAhead != null && (lookAhead.getType() == TokenType.SEMI)) {
			match(TokenType.SEMI);
			statNodeList.addAll(statement_list());
		}
		// else lambda case
		return statNodeList;
	}

	/**
	 * creates all possible statements in the Pascal cod
	 */
	public StatementNode statement() {
		StatementNode state = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID)) {
			if (symTab.isVariableName(lookAhead.getLexeme()) || symTab.isArrayName((lookAhead.getLexeme()))) {
				AssignmentStatementNode assign = new AssignmentStatementNode();

				assign.setLvalue(variable());

				assignop();
				assign.setExpression(expression());
				return assign;
			} else if (symTab.isProcedureName(lookAhead.getLexeme())) {
				return procedure_statement();
			} else
				error("Name not found in symbol table.");
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.BEGIN))
			state = compound_statement();
		else if (lookAhead != null && (lookAhead.getType() == TokenType.IF)) {
			IfStatementNode ifState = new IfStatementNode();
			match(TokenType.IF);
			ifState.setTest(expression());
			match(TokenType.THEN);
			ifState.setThenStatement(statement());
			match(TokenType.ELSE);
			ifState.setElseStatement(statement());

			return ifState;
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.WHILE)) {
			WhileStatementNode whileState = new WhileStatementNode();
			match(TokenType.WHILE);
			whileState.setTest(expression());
			match(TokenType.DO);
			whileState.setDoStatement(statement());
			return whileState;
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.READ)) {
			match(TokenType.READ);
			match(TokenType.LEFTPAR);
			String varName = lookAhead.getLexeme();
			match(TokenType.ID);
			match(TokenType.RIGHTPAR);
			if (!allVarNames.contains(varName))
				allVarNames.add(varName);
			return new ReadNode(new VariableNode(varName));
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.WRITE)) {
			match(TokenType.WRITE);
			match(TokenType.LEFTPAR);
			WriteNode write = new WriteNode(expression());
			match(TokenType.RIGHTPAR);
			return write;
		}

		else {
			// nothing
		}
		return state;
	}

	/**
	 * a variable ID or a variable ID with an expression after it surrounded by
	 * brackets
	 */
	public VariableNode variable() {
		String lex = lookAhead.getLexeme();

		// VariableNode var = null;

		match(TokenType.ID);
		if (lookAhead != null && (lookAhead.getType() == TokenType.LEFTBRACKET)) {
			match(TokenType.LEFTBRACKET);

			ExpressionNode expNodeTemp = expression();
			match(TokenType.RIGHTBRACKET);
			ArrayNode arrNode = new ArrayNode(lex);

			arrNode.setExpNode(expNodeTemp);
			return arrNode;

		} else {

			VariableNode varNode = new VariableNode(lex);
			if (!allVarNames.contains(varNode.getName()))
				allVarNames.add(varNode.getName());
			return varNode;
		}
		// else lambda case

	}

	/**
	 * a procedure ID or a production ID with an expression after it surrounded by
	 * parenthesis
	 */
	public ProcedureNode procedure_statement() {
		ProcedureNode psNode = new ProcedureNode();
		String procName = lookAhead.getLexeme();
		psNode.setVariable(new VariableNode(procName));
		match(TokenType.ID);
		if (lookAhead != null && (lookAhead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			psNode.addAllExpressions(expression_list());
			match(TokenType.RIGHTPAR);
		}
		symTab.addProcedureName(procName);
		return psNode;
	}

	/**
	 * expressions or a series of expressions separated by commas
	 */
	public ArrayList<ExpressionNode> expression_list() 
	{
		ArrayList<ExpressionNode> exList = new ArrayList<>();
		exList.add(expression());
		if (lookAhead != null && (lookAhead.getType() == TokenType.COMMA)) 
		{
			match(TokenType.COMMA);
			exList.addAll(expression_list());
		}
		return exList;
	}

	/**
	 * simple expression or a simple_expression compared to another
	 * simple_expression with a relop
	 * 
	 * @return
	 */
	public ExpressionNode expression() {
		ExpressionNode left = simple_expression();
		if (isRelop(lookAhead)) {

			OperationNode opNode = new OperationNode(lookAhead.getType());
			opNode.setLeft(left);
			match(lookAhead.getType());
			opNode.setRight(simple_expression());
			return opNode;
		}
		return left;
	}

	/**
	 * either a term and a simple_part or a sign and then a term and a simmple_part
	 */
	public ExpressionNode simple_expression() {
		ExpressionNode expNode = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID || lookAhead.getType() == TokenType.INTEGER
				|| lookAhead.getType() == TokenType.REAL || lookAhead.getType() == TokenType.LEFTPAR
				|| lookAhead.getType() == TokenType.NOT)) {
			expNode = term();
			expNode = simple_part(expNode);
		} else if (lookAhead != null
				&& (lookAhead.getType() == TokenType.PLUS || lookAhead.getType() == TokenType.MINUS)) {
			SignNode sig = sign();
			expNode = term();
			sig.setExpression(simple_part(expNode));
			return sig;
		} else
			error(" in simple expression");

		return expNode;
	}

	/**
	 * an addop and then a term and simple_part
	 */
	public ExpressionNode simple_part(ExpressionNode left) 
	{

		if (isAddop(lookAhead)) 
		{

			OperationNode operNode = new OperationNode(lookAhead.getType());
			match(lookAhead.getType());
			ExpressionNode right = term();
			operNode.setLeft(left);
			operNode.setRight(right);
			return simple_part(operNode);
		}
		// else lambda case
		return left;
	}

	/**
	 * a factor then a term_part
	 * 
	 * @return
	 */
	public ExpressionNode term() 
	{
		ExpressionNode left = factor();
		return term_part(left);
	}

	/**
	 * a mulop and then a factor and term_part
	 */
	public ExpressionNode term_part(ExpressionNode posLeft) 
	{
		if (isMulop(lookAhead)) {
			OperationNode operNode = new OperationNode(lookAhead.getType());
			match(lookAhead.getType());
			ExpressionNode right = factor();
			operNode.setLeft(posLeft);
			operNode.setRight(term_part(right));
			return operNode;
		}
		// else lambda case
		return posLeft;
	}

	

	/**
	 * a plus or minus
	 */
	public SignNode sign() {
		SignNode sig = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.PLUS)) {
			sig = new SignNode(TokenType.PLUS);
			match(TokenType.PLUS);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.MINUS)) {
			sig = new SignNode(TokenType.MINUS);
			match(TokenType.MINUS);
		} else
			error("sign");
		return sig;
	}

	 
	/**
	 * matching the relop and recognizing the token
	 */
	public void relop() 
	{
		if (lookAhead != null && (lookAhead.getType() == TokenType.EQUAL)) 
		{
			match(TokenType.EQUAL);
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.NOTEQUAL)) 
		{
			match(TokenType.NOTEQUAL);
		}
		else if (lookAhead != null && (lookAhead.getType() == TokenType.LESSTHAN)) 
		{
			match(TokenType.LESSTHAN);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.LESSOREQUAL)) 
		{
			match(TokenType.LESSOREQUAL);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.GREATEROREQUAL) 
				{
			match(TokenType.GREATEROREQUAL);
		} else if (lookAhead != null && (lookAhead.getType() == TokenType.GREATERTHAN)) 
		{
			match(TokenType.GREATERTHAN);
		} else {
			error("Relop");
		}
	}

	/**
	 * determining whether or not the token is an addop
	 * 
	 * @param token
	 *            the token to be determined
	 * @return whether or not the token is a relop
	 */
	public boolean isAddop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS
				|| token.getType() == TokenType.OR) {
			answer = true;
		}
		return answer;
	}

 

	 

	/**
	 * recognizing the assign operator
	 */
	public void assignop() {
		if (lookAhead != null && (lookAhead.getType() == TokenType.COLON)) {
			match(TokenType.COLON);
			match(TokenType.EQUAL);
		} else {
			error("in assignop");
		}
	}

	 

	/**
	 * Gets the symbol table as a string from SymbolTable
	 * 
	 * @return the symbol table as a string
	 */
	public String getSymbolTableStr() {
		return symTable.toString();
	}

	public SymbolTable getSymbolTable() {
		return symTable;
	}

	/**
	 * Error message printing
	 * 
	 * @param to
	 *            be printed
	 * 
	 */
	public void addToTable(String lexeme, SymbolType type) {
		symTable.add(lexeme, type);
	}

 

    
    
    
    
    
    
 
    
   
    
    /** 
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     * @param message The error message to print.
     */
    public void error( String message) 
    {
        System.out.println( "Error " + message + " at line " + this.scanner.getLine() + " column " + this.scanner.getColumn());
        //System.exit( 1);
    }
}