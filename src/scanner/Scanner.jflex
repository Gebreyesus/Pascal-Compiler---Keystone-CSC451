 
/**
 * This file defines lexical analyzer that will be used with the pasal compiler
 */

package scanner;
/**
 * @author Beteab Gebru
 */
%%

/////////// JFlex Directives  ////////////////

%public                     // make output class public
%class  Scanner   /* Names the produced java file */
%function nextToken /* new name for  the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
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

/* Patterns defined using regex expressions 
/* this definitions are exctracted 
/* from a grammer provided as input for the project


other         = .
letter        = [A-Za-z]
word          = {letter}+ | {letter}+[0-9]+{letter}*

lineTerminator = |\n|\n
whitespace     = {lineTerminator} | [ \t]

symbols       = [ ";" | "," | "." | ":" | "[" | "]" | "(" | ")" | "+" | "-" | 
                  "=" | "<>" | "<" | "<=" | ">" | ">=" | "*" | "/" | ":=" ]

integer 	=  [0-9] | [1-9][0-9]*
float 		=      [0-9]+ \. [0-9]*
positiveExp 	=    [0-9]+ "E" [0-9]* | [0-9]+ "E+" [0-9]*
negativeExp 	=    [0-9]+ "E-" [0-9]*

comment =    [{] [^*] ~ [}]



%%
/* Lexical Rules -> we will form the tokens and return the token object*/

{whitespace}    { /* Ignore Whitespace */ }
.               {   System.out.println("Illegal character, '" + yytext() + "' found."); }


{number}        {	// Found a number
					Token t = new Token( yytext(), TokenType.NUMBER); 
					return t;
                }

{integer}    	{ 
					Token t ( new Token( yytext(), TokenType.INTEGER ));
					return t;
				}


{symbols}       {	// Found a symbol
                    String inputLexeme = yytext();
                    TokenType ett = table.get( inputLexeme);	//find the token type from lookup table
                    Token t = new ExpToken( yytext(),  ett);
                    return t;
                }

{word}     		{ 
					Token t(new Token( yytext(), TokenType.ID ));
					return t;
		   		}
