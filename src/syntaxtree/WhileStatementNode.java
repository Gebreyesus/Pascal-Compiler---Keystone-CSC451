package syntaxtree;

/**
 * Represents a while statement
 * 
 * * @author Beteab Gebru
 */
public class WhileStatementNode extends StatementNode 
{

	private ExpressionNode test; //check each iteration in the while loop
	private StatementNode doW; // check at each iteration

	public void setTest(ExpressionNode test) 
	{
		this.test = test;
	}

	public void setDoStatement(StatementNode doW) 
	{
		this.doW = doW;
	}

	public ExpressionNode getTest() 
	{
		return test;
	}

	public StatementNode getDo() 
	{
		return doW;
	}

	/**
	 * Creates a String representation of this while statement node+children.
	 * @param level The tree level 
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) 
	{
		String answer = this.indentation(level);
		answer += "While:\n";
		answer += this.test.indentedToString(level + 1);
		answer += this.indentation(level) + "Do:\n" + this.doW.indentedToString(level + 1);
		return answer;
	}

}
