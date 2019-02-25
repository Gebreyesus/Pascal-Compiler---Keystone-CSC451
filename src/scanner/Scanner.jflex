package scanner;
/**
 * This file defines lexical analyzer that will be used with the pasal compiler
 * it will recognise tokens defined in grammer manual set at the begining of project
 * @author Beteab Gebru
 */
%%

/////////// JFlex Directives  ////////////////

%public                     // make output class public
%class  myScannerfromJFLEX   /* Names the produced java file */
%function nextToken			 /* new name for  the yylex() function */
%type   Token      			/* Defines the return type of the scanning function */
%line                       // turn on line counting
%column                     // turn on column counting

%eofval{ 	// return on end of file
  return null;
%eofval}


  	/////////////////////////////////////////////////////////////////
 	//  helper functions that will go into the scanner to be produced 
    /////////////////////////////////////////////////////////////////
%{
  /**
   * Gets the line number of the most recent lexeme.
   * @return The current line number.
   */
  public int getLine() { return yyline;}
  
  /**
   * Gets the column number of the most recent lexeme.
   * This is the number of chars since the most recent newline char.
   * @return The current column number.
   */
  public int getColumn() { return yycolumn;}
  
 /** The lookup table of token types for symbols. */
  private LookupTable table = new LookupTable();
  
%}

/** Patterns defined using regex expressions 
    this definitions are exctracted 
    from a grammer provided as input for the project	*/


other         = .
letter        = [A-Za-z]
digit      	  = [0-9]

lineTerminator = \n
whitespace     = {lineTerminator} | [ \t]

symbols       = [ ";" | "," | "." | ":" | "[" | "]" | "(" | ")" | "+" | "-" | "=" | "<>" | "<" | "<=" | ">" | ">=" | "*" | "/" | ":=" ]


integer 		=  [0-9] | [1-9][0-9]*
posExpression 	=    [0-9]+ "E" [0-9]* | [0-9]+ "E+" [0-9]*
negExpression 	=    [0-9]+ "E-" [0-9]*

word          = {letter}+ | {letter}+[0-9]+{letter}*
number     =	 {digit}+ | {integer}+ | {posExpression}+ | {negExpression}+

comment =    [{] [^*] ~ [}]


%%
/* Lexical Rules -> we will form the tokens and return the token object*/

{whitespace}    { /* Ignore Whitespace */ }
.               {   System.out.println("Illegal character, whitespace'" + yytext() + "' found."); }

{number}        {	// Found a number
					Token newToken = new Token( yytext(), TokenType.NUMBER); 
					return newToken;
                }

{integer}    	{ 
					Token newToken ( new Token( yytext(), TokenType.INTEGER ));
					return newToken;
				}


{symbols}       {	// Found a symbol - find lexeme from lookup table
                    String inputLexeme = yytext();
                    TokenType symbolLexeme = table.get( inputLexeme);	//find the token type from lookup table
                    Token newToken = new Token( yytext(),  symbolLexeme);
                    return newToken;
                }

{word}     		{ 	Token newToken(new Token( yytext(), TokenType.ID ));		return newToken;}
		   		
{posExpression}	{ Token newToken = new Token( yytext(), posExpression);	return newToken; }
	
{negExpression}	{ Token newToken = new Token( yytext(), negExpression);	return newToken;  }	
