
@Test
public void programTest() {
    Parser parser = new Parser( "money.pas");
    ProgramNode actual = parser.program();
    String actualString = actual.indentedToString( 0);
    String expectedString =
    
"Program: sample\n" +
"|-- Declarations\n" +
"|-- --- Name: dollars\n" +
"|-- --- Name: yen\n" +
"|-- --- Name: bitcoins\n" +
"|-- SubProgramDeclarations\n" +
"|-- Compound Statement\n" +
"|-- --- Assignment\n" +
"|-- --- --- Name: dollars\n" +
"|-- --- --- Value: 1000000\n" +
"|-- --- Assignment\n" +
"|-- --- --- Name: yen\n" +
"|-- --- --- Operation: MULTIPLY\n" +
"|-- --- --- --- Name: dollars\n" +
"|-- --- --- --- Value: 110\n" +
"|-- --- Assignment\n" +
"|-- --- --- Name: bitcoins\n" +
"|-- --- --- Operation: DIVIDE\n" +
"|-- --- --- --- Name: dollars\n" +
"|-- --- --- --- Value: 3900\n";


    assertEquals( expectedString, actualString);
}