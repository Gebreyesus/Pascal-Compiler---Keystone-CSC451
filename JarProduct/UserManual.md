
* Beteab Gebru
* Compilers II 
* MiniPascal Compiler

##### Description
    * This app will take in user provided pascal code and convert it into a MIPS assembly language.
    * Command Line prompt must be used to run the app. 

##### Instructions.
    * 1. Open cmd from system menu
    * 2. navigate to the directory, where the MiniPascalCompiler.jar resides.(cd "path to the directory/"
    * 3. Tyep: "java -jar MiniPascal.jar "path to the pascal code.pas" and click enter

 ##### Errors
    When pascal code is not valid code we may find error messages 
    Error Example
	- "Expected token-> BEGIN : found SEMICOLON instead at line 1 column 35"
		this error message is a syntax error 
		indicating the line and column number to look into debuging


* There should be two new ?les residing int the directory where MiniPascalCompiler.jar exists
   * "pascal input filename.asm" : the compiled assembly code in MIPS assembly istruction
   * "pascal input filename.table" :the table of symbols from the pascal code

NOTE to user:
-In its current shape the file will not handle some important data types such as arrays and floating numbers
-In addition there are some major bugs which will be fixed in next release (prob with parser)
	-> this will serve as demonstration in the meantime

 




* Repo owner/admin : Beteab Gebru 
