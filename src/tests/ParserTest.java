package tests;

import parser.Parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import scanner.TokenType;
/**
 *  @author Beteab Gebru
 */

public class ParserTest 
{
   
   public ParserTest() 
   {
   }
   
   @BeforeClass
   public static void setUpClass() 
   {
   }
   
   @AfterClass
   public static void tearDownClass() 
   {
   }
   /**
    * Test of exp method, of class Parser.
    */
   @Test
   public void testExp() {
       System.out.println("exp");
       Parser instance = new Parser( "C:\\Users\\betea\\Desktop\\Augsburg Projects folder\\0000 Git Projects\\Compilers II\\gebruCompiler\\Compilerproj\\test\\scanner\\test0.txt", true);
       instance.exp();
       System.out.println("It Parsed!");
   }

   /**
    * Test of exp_prime method, of class Parser.
    */
   @Test
   public void testExp_prime() {
       System.out.println("test exp_prime");
       Parser instance = new Parser( "+ 34", false);
       instance.exp_prime();
   }

//   /**
//    * Test of addop method, of class Parser.
//    */
//   @Test
//   public void testAddop() {
//       System.out.println("addop");
//       Parser instance = null;
//       instance.addop();
//       // TODO review the generated test code and remove the default call to fail.
//       fail("The test case is a prototype.");
//   }
//
   /**
    * Test of term method, of class Parser.
    */
   @Test
   public void testTerm() {
       System.out.println("test term");
       Parser instance = new Parser("23 / 17", false);
       //instance.term();
       System.out.println("Parsed a term");
   }

//   /**
//    * Test of term_prime method, of class Parser.
//    */
//   @Test
//   public void testTerm_prime() {
//       System.out.println("term_prime");
//       Parser instance = null;
//       instance.term_prime();
//       // TODO review the generated test code and remove the default call to fail.
//       fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of mulop method, of class Parser.
//    */
//   @Test
//   public void testMulop() {
//       System.out.println("mulop");
//       Parser instance = new Parser( "expressions/muloponly.pas");
//       //instance.mulop();
//       System.out.println("Saw the single mulop");
//   }
//
   /**
    * Test of factor method, of class Parser.
    */
   @Test
   public void testFactor() {
       System.out.println("factor");
       Parser instance = new Parser( "54321", false);
       instance.factor();
   }
//
//   /**
//    * Test of match method, of class Parser.
//    */
//   @Test
//   public void testMatch() {
//       System.out.println("match");
//       ExpTokenType ett = null;
//       Parser instance = null;
//       instance.match(ett);
//       // TODO review the generated test code and remove the default call to fail.
//       fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of error method, of class Parser.
//    */
//   @Test
//   public void testError() {
//       System.out.println("error");
//       String message = "";
//       Parser instance = null;
//       instance.error(message);
//       // TODO review the generated test code and remove the default call to fail.
//       fail("The test case is a prototype.");
//   }
}
