package semanticanalyser;

import java.util.ArrayList;
import java.util.HashMap;
 
import parser.*;
import syntaxtree.*;

/**
 * class that takes in the syntax tree and checks for the following
 * Check if variables are declared before they are used, 
 * check if it assigns a type, integer or real, to each variable or expression
 * check if we violate the type assignments across the application
 * @author Beteab Gebru
 */
public class SemanticAnalyser 
{
	private ProgramNode progNode = null;
	private SymbolTable mySymbolTable = null;
	 
	private HashMap<String, DataType> variableTypes = new HashMap<String, DataType>();

	public SemanticAnalyser(ProgramNode progNode, SymbolTable mySymbolTable) 
	{
		this.progNode = progNode;
		this.mySymbolTable = mySymbolTable;
	}

	/**
	 * function called by main, will put declared variables with their types into 
	 * a hashmap.  and gets the compound statements so their expressions can be given types
	 * @return the program node : top level program node
	 */
	public ProgramNode analyze() 
	{

		
		// We place the variables and they corr types into hashmap
		// This will allow us to check if theres undeclared use of variables
		ArrayList<VariableNode> varNodes = progNode.getVariables().getDeclarations();
		for (int i = 0; i < varNodes.size(); i++) 
		{
			variableTypes.put(varNodes.get(i).getName(), varNodes.get(i).getType());
		}

		verifyVariableDeclarations();

		// In here we assign the compund statments gets the compound statement nodes from the main program and the subdeclartion
		// node and sends them to setExpTypes so their expressions can be assigned types
		CompoundStatementNode mainCompStatNode = progNode.getMain();
		assignTypeToExp(mainCompStatNode);

		for (int i = 0; i < progNode.getFunctions().getSubProgs().size(); i++) {
			CompoundStatementNode subCompStatNode = progNode.getFunctions().getSubProgs().get(i).getMain();
			assignTypeToExp(subCompStatNode);
		}

		return progNode;
	}

	/**
	 * verifies that all variable in the program are declared before use, yields an
	 * error to console when one has not been declared but doesn't stop compilation
	 */
	private void verifyVariableDeclarations() 
	{

		// creates an array list for declared variable names 
		ArrayList<String> varsDeclaredNames = new ArrayList<String>();
		for (int i = 0; i < progNode.getVariables().getDeclarations().size(); i++)
		{
			varsDeclaredNames.add(progNode.getVariables().getDeclarations().get(i).getName());
		}
		
		 
		ArrayList<String> varsUsedNames = progNode.getAllVarNames();

		// checks for use of undeclared variables, handles error
		for (int i = 0; i < varsUsedNames.size(); i++) 
		{
			if ( ! varsDeclaredNames.contains(varsUsedNames.get(i)))
			{
				System.out.println("DECLARATION ERROR: The variable '" + varsUsedNames.get(i) + "' was never declared");
				System.out.println("Cannot resolve the variable name, Please declare the variable first");
			}
		}
		
	}

	/**
	 * @param compStatNode
	 */
	private void assignTypeToExp(CompoundStatementNode compStatNode) 
	{

		ArrayList<StatementNode> statementList = compStatNode.getStateNodes();
		for (StatementNode currentStatement : statementList) 
		{
			if (currentStatement instanceof AssignmentStatementNode) 
			{
				setExpTypes(((AssignmentStatementNode) currentStatement).getExpression());

				if ((variableTypes.get(((AssignmentStatementNode) currentStatement).getLvalue().getName()) != null))
					((AssignmentStatementNode) currentStatement).getLvalue()
						.setType(variableTypes.get(((AssignmentStatementNode) currentStatement).getLvalue().getName()));

			} 
			else if (currentStatement instanceof WhileStatementNode) 
			{

				setExpTypes(((WhileStatementNode) currentStatement).getTest());

			} 
			else if (currentStatement instanceof ProcedureNode) 
			{

				ArrayList<ExpressionNode> myExpressionNodes = ((ProcedureNode) currentStatement).getExpNode();
				for (ExpressionNode exp : myExpressionNodes)
					setExpTypes(exp);

			} 
			else if (currentStatement instanceof IfStatementNode) 
			{

				setExpTypes(((IfStatementNode) currentStatement).getTest());
				StatementNode thenStat = (((IfStatementNode) currentStatement).getThenStatement());
				StatementNode elseStat = (((IfStatementNode) currentStatement).getElseStatement());

				CompoundStatementNode ifThenComStat = new CompoundStatementNode();

				ifThenComStat.addStatement(thenStat);
				ifThenComStat.addStatement(elseStat);

				assignTypeToExp(ifThenComStat);

			} 
			else if (currentStatement instanceof CompoundStatementNode) 
			{

				assignTypeToExp(((CompoundStatementNode) currentStatement));

			} 
			else if (currentStatement instanceof WriteNode) 
			{

				ExpressionNode writeExp = (((WriteNode) currentStatement).getContent());

				if (writeExp instanceof VariableNode)
					((WriteNode) currentStatement).getContent().setType(variableTypes.get(((VariableNode) writeExp).getName()));
				else if (writeExp instanceof OperationNode) 
				{
					setExpTypes((((WriteNode) currentStatement).getContent()));

				}

			} 
			else if (currentStatement instanceof ReadNode) 
			{
				((ReadNode) currentStatement).getName();
				((ReadNode) currentStatement).getName().setType(variableTypes.get(((ReadNode) currentStatement).getName().getName()));
			}

		}

	}

	/**
	 * sets the expression types for the given expression 
	 * 	 * @param myExpressionNode
	 */
	private void setExpTypes(ExpressionNode myExpressionNode) 
	{

		if (getLNode(myExpressionNode) instanceof OperationNode)
			setExpTypes(getLNode(myExpressionNode));
		else if (   getLNode(myExpressionNode) instanceof VariableNode 
				 || getLNode(myExpressionNode) instanceof ValueNode  )
			setVarVal(getLNode(myExpressionNode));

		if (getRNode(myExpressionNode) instanceof OperationNode)
			setExpTypes(getRNode(myExpressionNode));
		else if (  getRNode(myExpressionNode) instanceof VariableNode 
				|| getRNode(myExpressionNode) instanceof ValueNode   )
			setVarVal(getRNode(myExpressionNode));

		if (myExpressionNode instanceof OperationNode) 
		{

			// if one is a real number the expression should be real, only if both are
			// integer should the expression be integer
			if ( getLNode(myExpressionNode).getType() == DataType.REAL_TYPE 
			   ||getRNode(myExpressionNode).getType() == DataType.REAL_TYPE  )
				myExpressionNode.setType(DataType.REAL_TYPE);
			else
				myExpressionNode.setType(DataType.INT_TYPE);

		}

		if (myExpressionNode instanceof ValueNode && myExpressionNode.getType() == null)
			setVarVal(myExpressionNode);

	}

	/**
	 * sets the variable/value for the given expression node
	 * @param myExpressionNode
	 */
	private void setVarVal(ExpressionNode myExpressionNode) 
	{

		if (myExpressionNode instanceof ValueNode) 
		{
			if (((ValueNode) myExpressionNode).getAttribute().contains("."))
				myExpressionNode.setType(DataType.REAL_TYPE);
			else
				myExpressionNode.setType(DataType.INT_TYPE);
		}

		else if (myExpressionNode instanceof VariableNode)
			myExpressionNode.setType(variableTypes.get((((VariableNode) myExpressionNode).getName())));

	}

	/**
 	 * Seeks out the left leaf of the expression
	 * @param myExpressionNode
	 * @return the left expression node
	 */
	private ExpressionNode getLNode(ExpressionNode myExpressionNode) 
	{
		ExpressionNode answer = null;

		if (myExpressionNode instanceof OperationNode)
			answer = ((OperationNode) myExpressionNode).getLeft();

		return answer;

	}

	/**
	 * Seeks out the right leaf of the expression
	 * @param myExpressionNode
	 * @return the right expression node
	 */
	private ExpressionNode getRNode(ExpressionNode myExpressionNode) 
	{
		ExpressionNode answer = null;

		if (myExpressionNode instanceof OperationNode)
			answer = ((OperationNode) myExpressionNode).getRight();

		return answer;

	}

 
}
