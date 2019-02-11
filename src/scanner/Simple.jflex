/**
 *
 * This file defines functions that will go into the final output.
 * Helper functions and lexical rules 
 * @author Beteab Gebru
 */

package scanner;

%%

%class  Scanner   /* Names the produced java file */
%function nextToken /* new name for  the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}
/* Patterns defined using regex expressions - from the grammer book*/

other         = .
letter        = [A-Za-z]
word          = {letter}+ | {letter}+[0-9]+{letter}*

lineTerminator = |\n|\n
whitespace     = {lineTerminator} | [ \t]

symbols       = [ ";" | "," | "." | ":" | "[" | "]" | "(" | ")" | "+" | "-" | 
                  "=" | "<>" | "<" | "<=" | ">" | ">=" | "*" | "/" | ":=" ]

integer 	=  [0-9] | [1-9][0-9]*
float 	=    [0-9]+ \. [0-9]*
positiveExp 	=    [0-9]+ "E" [0-9]* | [0-9]+ "E+" [0-9]*
negativeExp 	=    [0-9]+ "E-" [0-9]*

comment =    [{] [^*] ~ [}]



%%
/* Lexical Rules */
