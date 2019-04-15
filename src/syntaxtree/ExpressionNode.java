package syntaxtree;
import parser.DataType;
 
/**
 * General representation of any expression.
* @author Erik Steinmetz, Beteab Gebru
 */
public abstract class ExpressionNode extends SyntaxTreeNode 
{

	DataType mytype;

	public ExpressionNode() 
	{
		mytype = null;
	}

	public ExpressionNode(DataType type) 
	{
		this.mytype = type;
	}

	public void setType(DataType dataType) 
	{
		this.mytype = dataType;
	}

	public DataType getType() 
	{
		return mytype;
	}
    
}
