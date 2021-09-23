/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 *****************************************************************************/
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class UserInterface
{
    /*
    FUNCTION: menu
    PURPOSE: main menu for the interactive mode of the program, displays all
    the options available within interactive mode
    */
    public void menu()
    {
         int selection;
         double likeProb = 0.60;
         double followProb = 0.50;
         Scanner sc = new Scanner(System.in);
         DSAGraph graph = new DSAGraph();
         do
         {
             selection = intInput("\nSelect the number beside the option desired."
                                     +"\n1. Load network"
                                     +"\n2. Set probabilities"
                                     +"\n3. Node operations (find, insert, delete)"
                                     +"\n4. Edge operations (like/follow - add, remove)"
                                     +"\n5. New Post"
                                     +"\n6. Display network"
                                     +"\n7. Display statistics"
                                     +"\n8. Update (run a timestep)"
                                     +"\n9. Save network"
                                     +"\n0. Exit");
             // switch and case for menu
             switch (selection) {
               case 1: System.out.println("Input a file name");
                       String fileName = sc.nextLine();
                       FileManager.loadNetwork(graph, fileName);
                       break;
               case 2: likeProb = realInput("Probability that a person"
                                         +" likes a post of someone they follow\n"
                                         +"Between 0.00 and 1.00(exclusive)\n");
                       followProb = realInput("Probability that a person"
                                           +" follows the poster of the post they"
                                           +" liked\nBetween 0.00 and 1.00\n");
                       break;
               case 3: nodeOperations(graph);
                       break;
               case 4: edgeOperations(graph);
                       break;
               case 5: Operations.newPost(graph);
                       break;
               case 6: Operations.displayFollowers(graph);
                       Operations.displayPosts(graph);
                       Operations.displayLikedPosts(graph);
                       break;
               case 7: Operations.displayStats(graph);
                       break;
               case 8: long startTime = System.nanoTime();
                       Operations.timeStep(graph, likeProb, followProb);
                       long endTime = System.nanoTime();
                       long duration = (endTime - startTime);
                       System.out.println(duration+"\n");
                       break;
               case 9: String fileOutName = stringInput("What would you like to name the output file?\n");
                       FileManager.printStorage(graph, fileOutName);
                       break;
               case 0: System.out.print("Goodbye\n");
                       break;
               default:System.out.println("Error: Selection must be between 0 and 9"
                                          +"\nPlease Try Again");
                       break;
             }
          } while(selection != 0);
    }

    /*
    FUNCTION: nodeOperations
    PURPOSE: secondary menu that prompts the user to select from another set of
    options, to find, insert or delete a node within the graph
    */
    public void nodeOperations(DSAGraph graph)
    {
        int selection;
        String label;

        try
        {
            selection = intInput("What operation would you like to do?"
                                  +"\n1. Find Node"
                                  +"\n2. Insert Node"
                                  +"\n3. Delete Node");
            while(selection < 0 || selection > 3)
            {
                 selection = intInput("Error: Selection must be between 1 and 3"
                                       +"\nPlease Try Again");
            }
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: Invalid Input\nReturning to Menu");
            selection = -1;
        }
        switch (selection) {
            case 1:
                label = stringInput("What is the label of the node to be found?\n");
                Operations.findNode(graph, label);
                break;
            case 2:
                label = stringInput("What is the label of the node to be added?\n");
                if(graph.getVertices().contains(label) == true)
                {
                    System.out.println("Node already exists in the graph\n");
                }
                else
                {
                    Operations.insertNode(graph, label);
                }
                break;
            case 3:
                label = stringInput("What is the label of the node to be deleted?\n");
                if(graph.getVertices().contains(label) == true)
                {
                    Operations.deleteNode(graph, label);
                }
                else
                {
                    System.out.println("Node does not exist in the graph\n");
                }
                break;
            default:break;
        }
    }

    /*
    FUNCTION: edgeOperations
    PURPOSE: secondary menu prompt that prompts the user to select from
    two options, adding and deleting edges/follows from the graph
    */
    public void edgeOperations(DSAGraph graph)
    {
        int selection;

        try
        {
            selection = intInput("What operation would you like to do?"
                                 +"\n1. Add Follow"
                                 +"\n2. Delete Follow");
            while(selection < 0 || selection > 2)
            {
                selection = intInput("Error: Selection must be between 1 and 2"
                                      +"\nPlease Try Again");
            }
        }
        catch(InputMismatchException e)
        {
            System.out.println("Error: Invalid Input\nReturning to Menu");
            selection = -1;
        }
        switch (selection) {
            case 1:
                Operations.addFollow(graph);
                break;
            case 2:
                Operations.removeFollow(graph);
                break;
            default:break;
        }
    }

    /*
    FUNCTION: intInput
    PURPOSE: imports prompt to input an integer value, will catch any invalid
    inputs and is tailored for this part/section of importing
    */
    public int intInput(String prompt) throws InputMismatchException
    {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        int scan;

        do
        {
             try
             {
                 scan = sc.nextInt();
                 if (scan < 0)
                 {
                     System.out.println("ERROR: Negative values are invalid\n"+prompt);
                     scan = -1;
                 }
             }
             catch (InputMismatchException e)
             {
                 String flush = sc.nextLine();
                 System.out.println("ERROR: Invalid character detected\n"+prompt);
                 scan = -1;
             }
        } while (scan < 0);
        return scan;
    }

    /*
    FUNCTION: realInput
    PURPOSE: imports prompt to input an real value, will catch any invalid
    inputs and is tailored for this part/section of importing
    */
    public double realInput(String prompt) throws InputMismatchException
    {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        double scan;

        do
        {
            try
            {
                scan = sc.nextDouble();
                if (scan < (0)  || (scan > 1))
                {
                    System.out.println("ERROR: Invalid value outside of bounds\n"+prompt);
                    scan = -1;
                }
            }
            catch (InputMismatchException e)
            {
                String flush = sc.nextLine();
                System.out.println("ERROR: Invalid character detected\n"+prompt);
                scan = -1;
            }
        } while ((scan < (0)) || (scan > 1));
          return scan;
    }

    /*
    FUNCTION: stringInput
    PURPOSE: imports prompt to input a String value
    */
    public static String stringInput(String prompt)
    {
        Scanner sc = new Scanner(System.in);
        String scan;
        System.out.println(prompt);
        scan = sc.nextLine();
        return scan;
    }
}
