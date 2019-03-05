
package scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erik Steinmetz
 */
public class ExpScannerTest {
    
    public ExpScannerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of yytext method, of class ExpScanner.
     */
    @Test
    public void testYytext() throws IOException {
        System.out.println("yytext");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("expressions/simplest.pas");
        } catch (FileNotFoundException ex) {
        }
        InputStreamReader isr = new InputStreamReader( fis);
        ExpScanner instance = new ExpScanner(isr);
        
        instance.nextToken();
        String expResult = "34";
        String result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "+";
        result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "17";
        result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "*";
        result = instance.yytext();
        assertEquals(expResult, result);

        instance.nextToken();
        expResult = "7";
        result = instance.yytext();
        assertEquals(expResult, result);
    }


    /**
     * Test of nextToken method, of class ExpScanner.
     */
    @Test
    public void testNextToken() throws Exception {
        System.out.println("nextToken");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("expressions/simplest.pas");
        } catch (FileNotFoundException ex) {
        }
        InputStreamReader isr = new InputStreamReader( fis);
        
        
        ExpScanner instance = new ExpScanner(isr);
        
        ExpTokenType expResult = ExpTokenType.NUMBER;
        ExpTokenType result = instance.nextToken().getType();
        assertEquals(expResult, result);

        expResult = ExpTokenType.PLUS;
        result = instance.nextToken().getType();
        assertEquals(expResult, result);

        expResult = ExpTokenType.NUMBER;
        result = instance.nextToken().getType();
        assertEquals(expResult, result);

        expResult = ExpTokenType.MULTIPLY;
        result = instance.nextToken().getType();
        assertEquals(expResult, result);

        expResult = ExpTokenType.NUMBER;
        result = instance.nextToken().getType();
        assertEquals(expResult, result);

    }
    
}
