
* Beteab Gebru
* CSC 451 - Compilers II
* Building Lexical Analyser For a Minipascal Language Defined In the attached Document. 

# Description 

* JFlex is a lexical analyzer generator (also known as scanner generator) for Java.
* JFlex takes as input a specification with a set of regular expressions and corresponding actions. 
* It generates Java source of a lexer that reads input, matches the input against the regular expressions in the spec file, and runs the corresponding action if a regular expression matched.
* Lexers usually are the first front-end step in compilers, matching keywords, comments, operators, etc, and generating an input token stream for parsers.
* JFlex lexers are based on deterministic finite automata (DFA).
* They are fast, without expensive backtracking.
Source: http://jflex.de

#### List of packages/files 
 
* scanner
    * LookupTable.java (will serve to store scanner lexims and symbols)
    * myScannerfromJFLEX.java (DFA scanner produced with help of Jflex)
    * Token.java (defines a token object and attributes thereof)
    * TokenType.java (specifications for the different token types)
    * jflex-1.6.1.jar (jflex tool from jflex)
    * Scanner.jflex (input file for jflex)
* parser
    * Parser.java (will recognise tokens for what they mean(variable declarations,functions....)
    * SymbolTable.java(will define a table storing recognised symbols)
    * SybolsType.java(specification file for the types of symbols we could store in the table)
* tests
    * we will have test file for all classes defined in scanner and parser packages

* SDD+MiscDocs
    * SDD Document
    * MiniPascal Grammer.pdf
* Test
    * we will have files in here to test the packages
    * we will have sample pascal input code as well as expected output
    * we will have errenous code as well as good pascal code
 

#### Who do I talk to? ###
* Repo owner/admin : Beteab Gebru
