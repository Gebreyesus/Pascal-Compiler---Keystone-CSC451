
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 * @author Erik Steinmetz, Beteab Gebru
 */
public class DeclarationsNode extends SyntaxTreeNode {
    
    private ArrayList<VariableNode> variables = new ArrayList<VariableNode>();
    private ArrayList<ArrayNode> arrayDeclarations = new ArrayList<ArrayNode>();
    /**
     * Adds a variable to this declaration.
     * @param aVariable The variable node to add to this declaration.
     */
    public void addVariable( VariableNode aVariable) 
    {
    	variables.add( aVariable);
    }
    
    public void addDeclarations(DeclarationsNode aVariable) 
    {
    	variables.addAll(aVariable.variables);
	}
    public ArrayList<VariableNode> getDeclarations() {
		return this.variables;

	}
    
    /**
     * Creates a String representation of this declarations node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) 
    {
        String answer = this.indentation( level);
        answer += "Declarations\n";
        for( VariableNode variable : variables) 
        {
            answer += variable.indentedToString( level + 1);
        }
        return answer;
    }

    /**
     * Creates a String representation of this declarations node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
	public void addVariable(ArrayNode arrayVariable) 
	{
		variables.add(arrayVariable);
	}

	
}
