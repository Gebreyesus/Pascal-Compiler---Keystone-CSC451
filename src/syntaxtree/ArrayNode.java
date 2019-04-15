package syntaxtree;
import parser.DataType;
/**
 * Represents a declared array in Pascal program 
 *  had ID, data type of array elements,
 * @author Erik Steinmetz, Beteab Gerbu
 */
public class ArrayNode extends VariableNode 
{
	 
		private ExpressionNode expression;
		private DataType type = null;

		public ArrayNode(String varName, DataType myType) 
		{
			super(varName);
			this.type = myType;
		}

		public ArrayNode(String varName) 
		{
			super(varName);
		}

		public String getName() 
		{
			return (super.getName());
		}

		public ExpressionNode getExpNode() 
		{
			return this.expression;
		}

		public void setExpNode(ExpressionNode input) 
		{
			this.expression = input;
		}

		/**
		 * Creates a String representation of this array node and its children.
		 * 
		 * @param level The tree level at which this node resides.
		 * @return A String representing this node.
		 */
		@Override
		public String indentedToString(int level) {
			String answer = this.indentation(level);
			answer += "Array: " + super.getName() + ", Type: " + type + "\n";
			if (this.expression != null) {
				answer += this.expression.indentedToString(level + 1);
			}
			return answer;
		}


}