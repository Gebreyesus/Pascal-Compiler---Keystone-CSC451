
package parser;

public class Parser 
{ 	

}

public void declarations()
{
	if(this.lookahead.type == tokentype.VAR)
	{
		match(TokenType.VAR);
		identifierList();
		match(TokenType.COLON);
		type();
		match(TokenType.SEMI_COLON);
		declaration();
	}
	else 
	{
		//lambda option
	}
	
}
public void program()
{
	match(TokenTYpe.PROGRAM);
	match(TokenType.ID);
	match(TokenType.SEMICOLON);
	declarfations();
	subprogram.declarations();
	compoundstatement();
	match.TokenType.PERIOD);
}

public void standard_type()
{
	if(this.lookahead.type == TokenType.INTEGER)
	{
		match (TOkenType.INTEGER);
	}
	else if (this.lookahead.type == TokenType.REAL)
	{
		match(TokenType.REAL);
	}
	else
	{
		error(" erronous data");
	}
}

public void identifierlist()
{
	match (TokenType.ID);
	if(this.lookahead.type == TokenType.ID);
	{
		match(TOkenType.COMMA);
		identifierList();
	}
	else
	{
		//lambda option
	}
}


