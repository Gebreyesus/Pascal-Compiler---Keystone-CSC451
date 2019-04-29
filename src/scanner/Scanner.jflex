package scanner;
/**
 * This file defines lexical analyzer that will be used with the pasal compiler
 * it will recognise tokens defined in grammer manual set at the begining of project
 * @author Beteab Gebru
 */
%%

/////////// JFlex Directives  ////////////////

%public                     /* make output class public*/
%class  myScannerfromJFLEX  /* Names the produced java file */
%function nextToken         // name of the token function
%type   Token      			/* Defines the return type of the scanning function */
%line                       /* turn on line counting*/
%column                     /* turn on column counting*/

%eofval{ /* return on end of file */
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
  
 /** 
  * The lookup table of token types for symbols. 
  */
  private LookupTable mytable = new LookupTable();
  
%}

/** Patterns defined using regex expressions 
    this definitions are exctracted 
    from a grammer provided as input for the project	*/


letter        = [A-Za-z]
digit      	  = [0-9]
digits        = ({digit})({digit}*)
 

symbol        = ";" | "," | "." | ":" | "[" | "]" | "(" | ")" | "+" | "-" | "=" | "<>" | "<" | "<=" | ">" | ">=" | "*" | "/" | ":=" 

integer 		= [0-9] | [1-9][0-9]*
posExponent 	= [0-9]+ "E" [0-9]* | [0-9]+ "E+" [0-9]*
negExponent 	= [0-9]+ "E-" [0-9]*
exponent 		= {posExponent}|{negExponent}
fraction 		= ([.])({digits})
number     		= {digits}|{fraction}|{exponent}|{integer}

id              = ({letter}+)({letter}|{digit})*

whitespace      = [ \n\t\r]|(([\{])([^\{])*([\}]))
comment			= [{] [^*] ~ [}]

other           = .

%%

/* Lexical Rules -> we will form the tokens and return the token object*/
{number}        {	// Found a number
					Token newToken = new Token( yytext(), TokenType.NUMBER); 
					return newToken;
                }

{integer}    	{ 
					Token newToken = new Token( yytext(), TokenType.INTEGER );
					return newToken;
				}

{symbol}        {	// locate lexeme from lookup table for the symbol
                    String inputLexeme = yytext();
                    TokenType symbolLexeme = mytable.get(inputLexeme);	
                    
                    Token newToken = new Token( yytext(),  symbolLexeme);
                    return newToken;
                }

{id}     		{ 	/*if word is not in our lookup table return token else return ID token*/	
								
					TokenType newType = mytable.get(yytext());
					if(newType != null)
					{
						Token newToken = new Token( yytext(), newType);
               			return newToken;
             		}
             		else
             		{
             			Token newToken = new Token( yytext(), TokenType.ID);
                		return newToken;
             		}
             		 
				}
		   		
{posExponent}	{ 
					Token newToken = new Token( yytext(), TokenType.POSEXP);	
					return newToken; 
				}
	
{negExponent}	{ 
					Token newToken = new Token( yytext(), TokenType.NEGEXP);	
					return newToken;  
				}	
 
 				
{whitespace} 	{
					//if(( yytext().charAt(0) == '{') && (yytext().charAt( yytext().length() - 1) == '}'))
					//{
                     //	System.out.println("Comment: " + yytext());
 					//}
 					//else
						//System.out.println("whitespace: ':-" +  yytext() + "'-: found.");
				}
				
{other}   	 	{ 
            		//System.out.println("Illegal Char: " + yytext() + " found.");
           		}
  