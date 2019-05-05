
* Beteab Gebru
* CSC 451 - Compilers II
* Building Compiler For a Minipascal Language Defined In the attached Document. 

# Description 

  * JFlex is a lexical analyzer generator (also known as scanner generator) for Java.
  * JFlex takes as input a specification with a set of regular expressions and corresponding actions. 
  * It generates Java source of a lexer that reads input, matches the input against the regular expressions in the spec file, and runs the corresponding action if a regular expression matched.
  * Lexers usually are the first front-end step in compilers, matching keywords, comments, operators, etc, and generating an input token stream for parsers.
  * JFlex lexers are based on deterministic finite automata (DFA).
  * They are fast, without expensive backtracking.
  *           Source: http://jflex.de

## List of packages/files 

### * SDD+MiscDocs
    * SDD Document
    * MiniPascal Grammer.pdf

### * JarProduct 
    * contains jar file and the user manual

### * Doc
    * Containsjava doc output from eclipse

## SRC Package

##### * scanner *
    * LookupTable.java (will serve to store scanner lexims and symbols)
    * myScannerfromJFLEX.java (DFA scanner produced with help of Jflex)
    * Token.java (defines a token object and attributes thereof)
    * TokenType.java (specifications for the different token types)
    * jflex-1.6.1.jar (jflex tool from jflex)
    * Scanner.jflex (input file for jflex)
##### * parser *
    * Parser.java (will recognise tokens for what they mean(variable declarations,functions....)
    * SymbolTable.java(will define a table storing recognised symbols)
    * SybolsType.java(specification file for the types of symbols we could store in the table)

##### * syntaxtree *
    * Package contains the class definitions of the various pascal code constructs

##### * semanticanalyser *
    * Will construct the syntax tree structure for validation

##### * compiler *
    * will be called by java which will take in file name from user as argument
    * it will run parser by taking the entire pascal code from inputfile as a string 
    * It'll try to create two files with the input filename as output filname
    * if file with same name and extension exists it'll overwrite it

##### * codegenerator *
    * has series of methods defining conversion to MIPS code
    * there are asm equivalents for the various pascal grammars 


##### * tests *
    * we will have test file for all classes defined in scanner and parser packages


##### * test *
    * we will have files in here to test the packages
    * we will have sample pascal input code as well as expected output
    * we will have errenous code as well as good pascal code
 


 




* Repo owner/admin : Beteab Gebru 
