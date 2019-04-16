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
 		progNode.setAllVarNames(allVariableNames);
 	 
 		return progNode; 
 	}
 	
	  /**
     * Matches the expected token.
     * This function will serve to check 
     * if lookAhead contains expected token 
     * based on the current token from scanner
     * @param expected The expected token type.
     */
    public void match( TokenType expected) 
    {
        System.out.println("Matching (" + expected + ") with token from lookAhead("+this.lookAhead.getType().toString()+")");
        
        if( this.lookAhead.getType() == expected) 
        {
            try 
            { //handling null value at end of file
                this.lookAhead = myScanner.nextToken();
                if( this.lookAhead == null) 
                {
                    this.lookAhead = new Token( "End of File", null);
                }
            } 
            catch (IOException ex) 
            {
                error( "Scanner exception : End of file/string reached");
            }
        }
        else 
        {
            error("Match Eror - Expected token-> " + expected + " : found " + this.lookAhead.getType() + " instead");
        }
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
		if (lookAhead != null 
			 && ((this.lookAhead.getType() == TokenType.ID)
			 ||  (this.lookAhead.getType() == TokenType.BEGIN) 
			 ||	 (this.lookAhead.getType() == TokenType.IF)
			 ||	 (this.lookAhead.getType() == TokenType.WHILE) 
			 ||	 (this.lookAhead.getType() == TokenType.READ)
			 ||	 (this.lookAhead.getType() == TokenType.WRITE) )) 
		   comStatementNode.addAllStateNodes(statement_list()); 
		else 
		{/* lambda option**/ }
		
		return comStatementNode;  
	}


	/**
	 * List of statements separated by semicolons 
	 * @return ArrayList<StatementNode>: list containing statementNode/s
	 */
	public ArrayList<StatementNode> statementlist() 
	{
		ArrayList<StatementNode> statementNodeList = new ArrayList<StatementNode>();
		StatementNode statNode = statement();

		if (statNode != null) 
		{
			statementNodeList.add(statNode);
		}
		if (lookAhead != null && (lookAhead.getType() == TokenType.SEMICOLON)) 
		{
			match(TokenType.SEMICOLON);
			statementNodeList.addAll(statement_list());
		}
		//  lambda option
		
		return statementNodeList; 
	}
 
	/**
	 * taking in a subprogram_declaration or a comma separated list 
	 * @return SubProgramDeclarationsNode - confirmed declaration of this type
	 */
	public SubProgramDeclarationsNode subProgramDeclarations() 
	{
		SubProgramDeclarationsNode mysubProgDecNode = new SubProgramDeclarationsNode();
		
		if ( lookAhead != null 
			 && (lookAhead.getType() == TokenType.FUNCTION 
			 ||	lookAhead.getType() == TokenType.PROCEDURE) ) 
		 {
			mysubProgDecNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMICOLON);
			mysubProgDecNode.addAll(subprogram_declarations().getSubProgs());
		 }
		// lambda option
	
		return mysubProgDecNode;
	}
	
	
	/**
	 * a Variable or list of Variables of a type
	 * @return DeclarationsNode 
	 */
	public DeclarationsNode declarations() 
	{
		DeclarationsNode declaredNode = new DeclarationsNode();
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
					declaredNode.addVariable(new VariableNode(varID, myType));
					if (! allVariableNames.contains(varID))
						  allVariableNames.add(varID);//add new variable declarations
				} 
			}
			
			if (isArray) //case of an array
			{
				for (String varID : identifiersList) 
				{
					declaredNode.addVariable(new ArrayNode(varID, myType));

					if ( !allVariableNames.contains(varID)) 
						 allVariableNames.add(varID);
					// removes array thats been designated a variable name
					// and adds array as an arraytype
					symTable.getTable().remove(varID);
					symTable.addArrayName(varID);
					symTable.addArrayNameTable(varID, arrySize);

					isArray = false;
				}
			}
		  
		} 
		/* lambda option */  
		
	  return declaredNode;
	}


	/**
	 * array containing either an INT or REAL type
	 * @return DataType  
	 */
	public DataType type(ArrayList<String> identifierList) 
	{
		DataType mytype = null;
		int startIndex = 0; 
		int endIndex = 0;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ARRAY)) 
		{
			match(TokenType.ARRAY);
			match(TokenType.LEFT_ROUND_PAREN);

			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) 
			{
				startIndex = Integer.parseInt(lookAhead.getLexeme()); 
				match(TokenType.INTEGER); 
			} 
			else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL))
				match(TokenType.REAL);
        		match(TokenType.COLON);

			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) 
			{                     
				endIndex = Integer.parseInt(lookAhead.getLexeme());
				match(TokenType.INTEGER);
			} 
			else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL))
				match(TokenType.REAL);
				match(TokenType.RIGHT_ROUND_PAREN);
				match(TokenType.OF);
				
				mytype = standardType();
				arrySize = endIndex - startIndex;
				isArray = true;

		} 
		else 
			if ( lookAhead != null 
				  && (lookAhead.getType() == TokenType.INTEGER 
				  || lookAhead.getType() == TokenType.REAL) ) 
			mytype = standardType();
	
		else
			error("Data type in the array is invalid");
		
		return mytype;
	}



	/**
	 * handling standard data type
	 * taking in either an integer or a real
	 * @return DataType : standard type
	 */
	public DataType standardType() 
	{
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.INTEGER)) 
		{
			match(TokenType.INTEGER);
			return DataType.INT_TYPE;
		} 
		else if (lookAhead != null && (this.lookAhead.getType() == TokenType.REAL)) 
		{
			match(TokenType.REAL);
			return DataType.REAL_TYPE;
		} 
		else 
		{
			error("Error : check the standardType function");
			return null;
		}
	}
 
	/**
	 * tests if token is a relational operator(ADDOP, MULOP, ASSIGNOP)
	 * @param token : the token
	 * @return true/false depending on wether the token is a relop (T/F)
	 */
  	private boolean isRelop( Token token)
  	{
  		TokenType tokenType = token.getType();
  		
  		if( tokenType == TokenType.EQUAL 
  			|| tokenType == TokenType.NOTEQUAL 
  			|| tokenType == TokenType.LESSTHAN 
  			|| tokenType == TokenType.LESSOREQUAL 
  			|| tokenType == TokenType.GREATEROREQUAL 
  			|| tokenType == TokenType.GREATERTHAN )
  			return true; 
  		else	
  			return false;
  	}
 
    /**
     * Executes the rule for the exp&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void exp_prime() 
    {
        if( lookAhead.getType() == TokenType.PLUS 
        	|| lookAhead.getType() == TokenType.MINUS ) 
        	{
            	addop();
            	terminal();
            	exp_prime();
        	}
        	// lambda option   
    }

    /**
	 * matching the addop and recognizing the token
	 */
	public void addop() 
	{
		if (lookAhead != null && (lookAhead.getType() == TokenType.PLUS)) 
		{
			match(TokenType.PLUS);
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.MINUS)) 
		{
			match(TokenType.MINUS);
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.OR)) 
		{
			match(TokenType.OR);
		} 
		else 
		{
			error("Error : checkAddop");
		}
	}

    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     */
    public void terminal() 
    {
        factor();
        terminal_prime();
    }
 

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
        // lambda option
    }

    /**
     * Determines whether or not the given token is
     * a mulop token.
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    private boolean isMulop( Token token) 
    {
        
        if( token.getType() == TokenType.MULTIPLY || token.getType() == TokenType.DIVIDE ) 
        	return true;
        else
        	return false;
    }

	/**
	 * matching the mulop and recognizing the token
	 */
	public void mulop() 
	{
		if (lookAhead != null && (lookAhead.getType() == TokenType.MULTIPLY)) 
			match(TokenType.MULTIPLY);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.DIVIDE)) 
			match(TokenType.DIVIDE);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.MOD))
			match(TokenType.MOD);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.AND))
			match(TokenType.AND);
		else 
			error(" ERROR: Check Mullop format");
	}
    
    /**
    * Executes the rule for the factor non-terminal symbol in
     * the expression grammar. Factor could be - a number,
     * id, id[expression], id(expression_list)| num|    
     * (expression), the not factor
     *  @return ExpressionNode
	 */
	public ExpressionNode factor() 
	{
		ExpressionNode myExpression = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID)) 
		{
			String lex = lookAhead.getLexeme();
			match(TokenType.ID);
			
			if (lookAhead != null && (lookAhead.getType() == TokenType.LEFT_ROUND_PAREN)) 
			{//found array of factors
				ArrayNode arrNode = new ArrayNode(lex);
				match(TokenType.LEFT_ROUND_PAREN);
				ExpressionNode temp = expression();
				arrNode.setExpNode(temp);
				match(TokenType.RIGHT_ROUND_PAREN);
				return arrNode;
			} 
			else if (lookAhead != null && (lookAhead.getType() == TokenType.LEFT_SQUARE_PAREN)) 
			{//
				FunctionNode funcNode = new FunctionNode(lex);
				match(TokenType.LEFT_SQUARE_PAREN);
				funcNode.setExpNode(expression_list());
				match(TokenType.RIGHT_SQUARE_PAREN);
				return funcNode;
			} 
			else 
			{
				if(! allVariableNames.contains(lex))
					allVariableNames.add(lex);
				else 
					return new VariableNode(lex);
			}
		} 
		else if (  lookAhead != null
				&& (lookAhead.getType() == TokenType.INTEGER 
				|| lookAhead.getType() == TokenType.REAL)  ) 
		 {
			if (lookAhead.getLexeme().contains("."))
				myExpression = new ValueNode( lookAhead.getLexeme(), DataType.REAL_TYPE);
			else
				myExpression = new ValueNode(lookAhead.getLexeme(), DataType.INT_TYPE);
			if (lookAhead != null && (lookAhead.getType() == TokenType.INTEGER)) 
				match(TokenType.INTEGER);
			else if (lookAhead != null && (lookAhead.getType() == TokenType.REAL)) 
				match(TokenType.REAL);
		 } 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.LEFT_SQUARE_PAREN)) 
		 {
			match(TokenType.LEFT_SQUARE_PAREN);
			myExpression = expression();
			match(TokenType.RIGHT_SQUARE_PAREN);
		 } 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.NOT)) 
		 {	// NOT factor
			SignNode mySign = new SignNode(TokenType.NOT);
			match(TokenType.NOT);
			mySign.setExpression(factor());
			return mySign;
		 } 
		else
			error(" Error : check in factor");
		
		return myExpression;
	}
	
 
	/**
	 * returns a list of identifier strings - variable and array names. 
	 * function will be called recursively until all IDs are matched.
	 *  @return ArrayList : list of identifiers
	 */
	public ArrayList<String> identifier_list() 
	{
		match(TokenType.ID);
		
		ArrayList<String> identifierList = new ArrayList<>();
		lexim = this.lookAhead.getLexeme();
		String name = lexim;
		
		symTable.addVariableName(lexim);
		identifierList.add(name);
		
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.COMMA)) 
		{
			match(TokenType.COMMA);
			identifierList.addAll(identifier_list());
		} 
		 
		return identifierList;

	}
 	
	/**
	 * taking in a subprogram_declaration or a series of subprogram_declarations
	 * separated by commas
	 *  @return SubProgramDeclarationsNode
	 */
	public SubProgramDeclarationsNode subprogram_declarations() 
	{
		SubProgramDeclarationsNode subProgDeclartionNode = new SubProgramDeclarationsNode();
		if (lookAhead != null
				&& (lookAhead.getType() == TokenType.FUNCTION 
				|| lookAhead.getType() == TokenType.PROCEDURE)) 
		{
			subProgDeclartionNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMICOLON);
			subProgDeclartionNode.addAll(subprogram_declarations().getSubProgs());
		}
		// lambda option
		
		return subProgDeclartionNode;
	}

	/**
	 * calling a series of other production rules in order to get a proper order
	 *  @return SubProgramNode
	 */
	public SubProgramNode subprogram_declaration() 
	{
		SubProgramNode subProgNode = subprogram_head();
		subProgNode.setVariables(declarations());
		subProgNode.setFunctions(subprogram_declarations());
		subProgNode.setMain(compound_statement());
		return subProgNode;
	}

	/**
	 * creating a function or a procedure
	 *  @return SubProgramNode
	 */
	public SubProgramNode subprogram_head() 
	{
		SubProgramNode spNode = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.FUNCTION)) 
		{
			match(TokenType.FUNCTION);
			String functName = lookAhead.getLexeme();
			spNode = new SubProgramNode(functName);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			symTable.addFunctionName(functName);
			match(TokenType.SEMICOLON);
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.PROCEDURE)) 
		{
			match(TokenType.PROCEDURE);
			String procName = lookAhead.getLexeme();
			spNode = new SubProgramNode(procName);
			match(TokenType.ID);
			arguments();
			symTable.addProcedureName(procName);
			match(TokenType.SEMICOLON);
		} 
		else
			error("subprogram_head");
		return spNode;
	}

	/**
	 * takes in parameters inside of parenthesis
	 * @return ArrayList : list containing argument-IDs 
	 */
	public ArrayList<String> arguments() 
	{
		ArrayList<String> args = new ArrayList<String>();
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.LEFT_ROUND_PAREN)) 
		{
			match(TokenType.LEFT_ROUND_PAREN);
			args = parameter_list();
			match(TokenType.RIGHT_ROUND_PAREN);
		} 
		 // lambda option
		
		return args;
	}

	/**
	 * takes in an ID and it's corresponding types or a series of parameters
	 * separated by semicolons
	 * @return  ArrayList
	 */
	public ArrayList<String> parameter_list() 
	{
		ArrayList<String> idList = identifier_list();
		match(TokenType.COLON);
		type(idList);
		if (lookAhead != null && (this.lookAhead.getType() == TokenType.SEMICOLON))
		{
			match(TokenType.SEMICOLON);
			idList.addAll(parameter_list());
		} 
			 
		return idList;
	}

	/**
	 * verifying the main code, starting with begin and ending with end
	 * @return  CompoundStatementNode
	 */
	public CompoundStatementNode compound_statement() 
	{
		CompoundStatementNode comStatNode = new CompoundStatementNode();
		match(TokenType.BEGIN);
		comStatNode = optional_statements();
		match(TokenType.END);
		return comStatNode;
	}

	/**
	 * for statement_list or nothing (lamda)
	 */
	public CompoundStatementNode optional_statements() 
	{
		CompoundStatementNode compStatNode = new CompoundStatementNode();
		if ( lookAhead != null && 
			 ( this.lookAhead.getType() == TokenType.ID
				|| (this.lookAhead.getType() == TokenType.BEGIN) 
				|| (this.lookAhead.getType() == TokenType.IF)
				|| (this.lookAhead.getType() == TokenType.WHILE) 
				|| (this.lookAhead.getType() == TokenType.READ)
				|| (this.lookAhead.getType() == TokenType.WRITE)) ) 
			compStatNode.addAllStateNodes(statement_list());
			// lambda option
		
		return compStatNode;  
	}

	/**
	 * statements or a series of statements separated by semicolons
	 * return@ ArrayList : 
	 */
	public ArrayList<StatementNode> statement_list() 
	{
		ArrayList<StatementNode> statNodeList = new ArrayList<StatementNode>();
		StatementNode statNode = statement();

		if (statNode != null) 
			statNodeList.add(statNode);
		if (lookAhead != null && (lookAhead.getType() == TokenType.SEMICOLON)) 
		{
			match(TokenType.SEMICOLON);
			statNodeList.addAll(statement_list());
		}
		// else lambda option
		return statNodeList;
	}

	/**
	 * creates all possible statements in the Pascal code
	 * return@ StatementNode
	 */
	public StatementNode statement() 
	{
		StatementNode state = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID)) 
		{
			if ( symTable.isVariableName(lookAhead.getLexeme()) 
				|| symTable.isArrayName((lookAhead.getLexeme())) ) 
			 {
				AssignmentStatementNode assign = new AssignmentStatementNode();
				assign.setLvalue(variable());
				assignop();
				assign.setExpression(expression());
				return assign;
			 }
			else if (symTable.isProcedureName(lookAhead.getLexeme())) 
			 {
				return procedure_statement();
			 } 
			else
				error("Name not found in symbol table.");
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.BEGIN))
			state = compound_statement();
		else if (lookAhead != null && (lookAhead.getType() == TokenType.IF)) 
		{
			IfStatementNode ifState = new IfStatementNode();
			match(TokenType.IF);
			ifState.setTest(expression());
			match(TokenType.THEN);
			ifState.setThenStatement(statement());
			match(TokenType.ELSE);
			ifState.setElseStatement(statement());

			return ifState;
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.WHILE)) 
		{
			WhileStatementNode whileState = new WhileStatementNode();
			match(TokenType.WHILE);
			whileState.setTest(expression());
			match(TokenType.DO);
			whileState.setDoStatement(statement());
			return whileState;
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.READ)) 
		{
			match(TokenType.READ);
			match(TokenType.LEFT_ROUND_PAREN);
			String varName = lookAhead.getLexeme();
			match(TokenType.ID);
			match(TokenType.RIGHT_ROUND_PAREN);
			if (!allVariableNames.contains(varName))
				 allVariableNames.add(varName);
			return new ReadNode(new VariableNode(varName));
		} 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.WRITE)) 
		{
			match(TokenType.WRITE);
			match(TokenType.LEFT_ROUND_PAREN);
			WriteNode write = new WriteNode(expression());
			match(TokenType.RIGHT_ROUND_PAREN);
			return write;
		}
 
		return state;
	}

	/**
	 * a variable ID or a variable ID with an expression after it surrounded by
	 * brackets
	 * return@ VariableNode
	 */
	public VariableNode variable() 
	{
		String lex = lookAhead.getLexeme();

		// VariableNode var = null;

		match(TokenType.ID);
		if (lookAhead != null && (lookAhead.getType() == TokenType.LEFT_SQUARE_PAREN)) 
		{
			match(TokenType.LEFT_SQUARE_PAREN);
			ExpressionNode expNodeTemp = expression();
			match(TokenType.RIGHT_SQUARE_PAREN);
			ArrayNode arrNode = new ArrayNode(lex);
			arrNode.setExpNode(expNodeTemp);
			
			return arrNode;
		} 
		else 
		{
			VariableNode varNode = new VariableNode(lex);
			if (!allVariableNames.contains(varNode.getName()))
				 allVariableNames.add(varNode.getName());
			return varNode;
		}
		// else lambda case

	}

	/**
	 * a procedure ID or a production ID with an expression after it surrounded by
	 * parenthesis
	 * return@ ProcedureNode
	 */
	public ProcedureNode procedure_statement() 
	{
		ProcedureNode psNode = new ProcedureNode();
		String procName = lookAhead.getLexeme();
		psNode.setVariable(new VariableNode(procName));
		match(TokenType.ID);
		
		if (lookAhead != null && (lookAhead.getType() == TokenType.LEFT_SQUARE_PAREN)) 
		{
			match(TokenType.LEFT_ROUND_PAREN);
			psNode.addAllExpressions(expression_list());
			match(TokenType.LEFT_ROUND_PAREN);
		}
		symTable.addProcedureName(procName);
		
		return psNode;
	}

	/**
	 * expressions or a series of expressions separated by commas
	 * @return ArrayList
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
	public ExpressionNode expression() 
	{
		ExpressionNode left = simple_expression();
		if (isRelop(lookAhead)) 
		{
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
	public ExpressionNode simple_expression() 
	{
		ExpressionNode expNode = null;
		if (lookAhead != null && (lookAhead.getType() == TokenType.ID 
				|| lookAhead.getType() == TokenType.INTEGER
				|| lookAhead.getType() == TokenType.REAL 
				|| lookAhead.getType() == TokenType.LEFT_ROUND_PAREN
				|| lookAhead.getType() == TokenType.NOT)) 
		 {
			expNode = term();
			expNode = simple_part(expNode);
		 } 
		else if (lookAhead != null
				&& (lookAhead.getType() == TokenType.PLUS 
				|| lookAhead.getType() == TokenType.MINUS) ) 
	     {
			SignNode sig = sign();
			expNode = term();
			sig.setExpression(simple_part(expNode));
			return sig;
		 } 
		else
			error("ERROR: check in simple expression");

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
			match(TokenType.EQUAL); 
		else if (lookAhead != null && (lookAhead.getType() == TokenType.NOTEQUAL)) 
			match(TokenType.NOTEQUAL);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.LESSTHAN)) 
			match(TokenType.LESSTHAN);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.LESSOREQUAL)) 
			match(TokenType.LESSOREQUAL);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.GREATEROREQUAL)) 
			match(TokenType.GREATEROREQUAL);
		else if (lookAhead != null && (lookAhead.getType() == TokenType.GREATERTHAN)) 
			match(TokenType.GREATERTHAN);
		else 
			error("ERROR : Check Relop");
	}

	/**
	 * determining whether or not the token is an addop
	 * @param token  the token to be determined
	 * @return whether or not the token is a relop
	 */
	public boolean isAddop(Token token) 
	{
		boolean answer = false;
		if (token.getType() == TokenType.PLUS 
				|| token.getType() == TokenType.MINUS
				|| token.getType() == TokenType.OR) {
			answer = true;
		}
		return answer;
	}

 

	 

	/**
	 * recognizing the assign operator
	 */
	public void assignop() 
	{
		if (lookAhead != null && (lookAhead.getType() == TokenType.COLON)) 
		{
			match(TokenType.COLON);
			match(TokenType.EQUAL);
		} 
		else 
			error("ERROR : in assignop");
	}

	 

	/**
	 * Gets the symbol table as a string from SymbolTable
	 * @return the symbol table as a string
	 */
	public String getSymbolTableStr() 
	{
		return symTable.toString();
	}

	public SymbolTable getSymbolTable() 
	{
		return symTable;
	}

	/**
	 * Error message printing 
	 * @param to     be printed
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
        System.out.println( "Error " + message + " at line " + this.myScanner.getLine() 
        + " column " + this.myScanner.getColumn());
        //System.exit( 1);
    }
}