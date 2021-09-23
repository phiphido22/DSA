/***************************************************************************
*  FILE: UnitTestGraph.java
*  AUTHOR: Phi Do - 19134613
*  LAST MOD: 22/10/2019
*  CITATION: This test harness follows the same format as the test harness
             used for the practical 3 in DSA, provided to be used when
             testing the linked list created initally
***************************************************************************/

import java.io.*;

public class UnitTestGraph
{
 public static void main(String args[])
 {
       // VARIABLE DECLARATIONS
       int iNumPassed = 0;
       int iNumTests = 0;
       DSAGraph graph = null;
       String sTestString;
       Object nodeValue;

//---------------------------------------------------------------------------

       System.out.println("\n\nTesting Normal Conditions - Constructor");
       System.out.println("=======================================");

       // TEST 1 : CONSTRUCTOR
       try {
           iNumTests++;
           graph = new DSAGraph();
           System.out.print("Testing creation of DSAGraph (getVerticeList()): ");
           if (graph.getVerticeList() == null)
               throw new NullPointerException("Graph has not being constructed.");
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

//---------------------------------------------------------------------------

       System.out.println("\nTest inserting and removing follows and likes");
       System.out.println("==========================================================");

       // TEST 2 : addVertex
       try {
           iNumTests++;
           System.out.print("Testing addVertex(): ");
           graph.addVertex("Adam", true, 1);
           graph.addVertex("Bob", true, 1);
           graph.addVertex("Chris", true, 1);
           graph.addVertex("Hello!", false, 1);
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

       // TEST 3 : addEdge
       try {
           iNumTests++;
           System.out.print("Testing addEdge(): ");
           graph.addEdge(graph.getVertex("Adam"), graph.getVertex("Bob"));
           graph.addEdge(graph.getVertex("Bob"), graph.getVertex("Adam"));
           if (!graph.isAdjacent(graph.getVertex("Adam"), graph.getVertex("Bob")))
               throw new IllegalArgumentException("FAILED.");
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

       // TEST 4 : addToLiked
       try {
           iNumTests++;
           System.out.print("Testing addToLiked(): ");
           graph.addToLiked(graph.getVertex("Adam"), graph.getVertex("Hello!"));
           if (!graph.isLiked(graph.getVertex("Adam"), graph.getVertex("Hello!")))
               throw new IllegalArgumentException("FAILED.");
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

       // TEST 5 : removeLiked
       try {
           iNumTests++;
           System.out.print("Testing removeLiked(): ");
           graph.removeLiked(graph.getVertex("Adam"), graph.getVertex("Hello!"));
           graph.removeLiked(graph.getVertex("Hello!"), graph.getVertex("Adam"));
           if (graph.isLiked(graph.getVertex("Adam"), graph.getVertex("Hello!")))
               throw new IllegalArgumentException("FAILED.");
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

       // TEST 5 : removeEdge
       try {
           iNumTests++;
           System.out.print("Testing removeEdge(): ");
           graph.removeEdge(graph.getVertex("Adam"), graph.getVertex("Bob"));
           if (graph.isAdjacent(graph.getVertex("Adam"), graph.getVertex("Bob")))
               throw new IllegalArgumentException("FAILED.");
           iNumPassed++;
           System.out.println("passed");
       } catch(Exception e) { System.out.println("FAILED"); }

//---------------------------------------------------------------------------

       // PRINT TEST SUMMARY
       System.out.print("\nNumber PASSED: " + iNumPassed + "/" + iNumTests);
       System.out.print(" -> " + (int)(double)iNumPassed/iNumTests*100 + "%\n");
   }
//---------------------------------------------------------------------------
}
