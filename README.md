* Beteab Gebru
* CSC 451 - Compilers II
* Building Lexical Analyser For a Minipascal Language Defined In the attached Document. 
* Added Testing files to test the output program

#Description 



##JFlex
* JFlex is a lexical analyzer generator (also known as scanner generator) for Java.
* JFlex takes as input a specification with a set of regular expressions and corresponding actions. 
* It generates Java source of a lexer that reads input, matches the input against the regular expressions in the spec file, and runs the corresponding action if a regular expression matched.
* Lexers usually are the first front-end step in compilers, matching keywords, comments, operators, etc, and generating an input token stream for parsers.
* JFlex lexers are based on deterministic finite automata (DFA).
* They are fast, without expensive backtracking.
Source: http://jflex.de

##List Of Files 

###Minipascal Grammar File
* Minipascal Grammar file(SDD and Misc Documents - Directory)

###Token Definition
* Tokens are defined with TokenDefinition class that holds token name and regular expression. Token name can be empty, and in that case, lexer will ignore/skip such tokens.

### Who do I talk to? ###
* Repo owner/admin : Beteab Gebru
