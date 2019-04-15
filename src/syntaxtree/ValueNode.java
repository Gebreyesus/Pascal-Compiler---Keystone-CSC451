/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;
import parser.DataType;

/**
 * Represents a value or number in an expression.
 *  @author Erik Steinmetz, Beteab Gebru
 */
public class ValueNode extends ExpressionNode 
{
    /** The attribute associated with this node. */
    String attribute;
	DataType myType;
    	
    /**
     * Creates a ValueNode with the given attribute, and a type
     * @param attr The attribute for this value node.
     */
	public ValueNode(String attr, DataType type) 
	{
		this.myType = type;
		this.attribute = attr;
	}
	
	public DataType getType() 
	{
		return this.myType;
	}
    /** 
     * Returns the attribute of this node.
     * @return The attribute of this ValueNode.
     */
    public String getAttribute() 
    { 
    	return( this.attribute);
    }
    
	public void setType(DataType type) 
	{
		super.setType(type);
		this.myType = type;
	}
	
    /**
     * Returns the attribute as the description of this node.
     * @return The attribute String of this node.
     */
    @Override
    public String toString() 
    {
        return( attribute);
    }
    
    /**
     * Creates a String representation of this value node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) 
    {
        String answer = this.indentation(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }

    @Override
    public boolean equals( Object o) 
    {
        boolean answer = false;
        if( o instanceof ValueNode) 
        {
            ValueNode other = (ValueNode)o;
            if( this.attribute.equals( other.attribute)) answer = true;
        }
        return answer;
    }    
}
