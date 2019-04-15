package syntaxtree;

import java.util.ArrayList;

/**
 * function in pascal
 * @author Beteab Gebru
 */
public class FunctionNode extends VariableNode {

	private ArrayList<ExpressionNode> expNode;

	public FunctionNode(String attr) {
		super(attr);
		expNode = null;
		this.type = null;
	}

	public ArrayList<ExpressionNode> getExpNode() {
		return this.expNode;
	}

	public void setExpNode(ArrayList<ExpressionNode> input) {
		this.expNode = input;
	}

	public void addExpNode(ExpressionNode input) {
		expNode.add(input);
	}

	public void addAll(ArrayList<ExpressionNode> input) {
		expNode.addAll(input);
	}

	public String getName() {
		return (super.name);
	}

	@Override
	public String toString() {
		return ("VariableNode: " + super.name + "ExpressionNode: " + expNode);
	}

	/**
	 * return a String representation of this a function node
	 * @param level: level on the tree of the node.
	 * @return A String 
	 */

	@Override
	public String indentedToString(int level) {
		String formatedString = this.indentation(level);
		formatedString += "Name: " + super.name + ", Type: " + super.type + "\n";
		formatedString += this.indentation(level);
		formatedString += "Arguments: \n";
		for (ExpressionNode expression : expNode) {
			formatedString += expression.indentedToString(level + 1);
		}
		return formatedString;
	}

}