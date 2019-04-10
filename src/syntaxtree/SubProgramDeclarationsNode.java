
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations.
 * @author Erik Steinmetz, Beteab Gebru
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode 
{
    
    private ArrayList<SubProgramNode> subProgramsList = new ArrayList<SubProgramNode>();
    
    public void addSubProgramDeclaration( SubProgramNode mySubProgram) 
    {
    	subProgramsList.add( mySubProgram);
    }
	public void addAll(ArrayList<SubProgramNode> aSubProgram) 
	{
		subProgramsList.addAll(aSubProgram);//apend the list
	}
	public ArrayList<SubProgramNode> getSubProgs() {
		return this.subProgramsList;
	}
    /**
     * Output will be syntax tree, with indentation used to mark level on the tree.
     * @param level shows where on the tree the node resides
     * @return A String representing this node(tokens).
     */
    @Override
    public String indentedToString( int level) 
    {
        String answer = this.indentation( level);
        answer += "SubProgramDeclarations\n";
        for( SubProgramNode subProgram : subProgramsList) 
        {
        	answer += subProgram.indentedToString(level + 1);
        }
        return answer;
    }

	 
    
}



