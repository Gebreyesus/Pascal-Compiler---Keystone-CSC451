package syntaxtree;

/**
 * Represents a read command
 * * @author Beteab Gebru
 */
public class ReadNode extends StatementNode 
{

	private VariableNode name;

	public ReadNode(VariableNode name) 
	{
		this.name = name;
	}

	public VariableNode getName() 
	{
		return name;
	}

	/**
	 * Creates a String representation of this read node and its children.
	 * @param level The tree level 
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) 
	{
		String answer = this.indentation(level);
		answer += "Read \n ";
		answer += name.indentedToString(level + 1);
		return answer;
	}



}
