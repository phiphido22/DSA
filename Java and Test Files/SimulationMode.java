/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 *****************************************************************************/
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class SimulationMode
{
    /*
    FUNCTION: menu
    PURPOSE: in simulation mode, will import files and probabilities, and
    automatically creates a graph, imports the file contents, initiates a
    timestep and will print the results to a file. pressing 1 will initiate
    another timestep and print to another file. Pressing 0 will break the do
    while loop.
    */
    public void menu(String netfile, String eventfile, double likeProb, double followProb)
    {
          int selection;
          String fileOutName;
          DSAGraph graph = new DSAGraph();
          int timeStepCount = 1;

          FileManager.loadNetwork(graph, netfile);
          FileManager.loadNetwork(graph, eventfile);
          Operations.timeStep(graph, likeProb, followProb);
          fileOutName = netfile+eventfile+likeProb+followProb+timeStepCount;
          FileManager.printStorage(graph, fileOutName);
          Operations.displayStats(graph);
          do{
              selection = intInput("Enter 1 to continue the simulation, or 0 to close the program.");
              switch (selection) {
                //case 1 will initiate a timestep, then print the results to a file
                //this will create multiple files to see the changes between each timestep
                case 1:
                   Operations.timeStep(graph, likeProb, followProb);
                   timeStepCount++;
                   fileOutName = netfile+"-"+eventfile+"_"+likeProb+"_"+followProb+"_timestep:"+timeStepCount;
                   FileManager.printStorage(graph, fileOutName);
                   Operations.displayStats(graph);
                   System.out.println("Timestep has been output to file.\n");
                   break;
                case 0:
                   System.out.println("Exitting simulation...");
                   break;
                default: System.out.println("Error: Selection must be either 0 or 1.");
                   break;
                }
          }while(selection != 0);
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
                     scan = 0;
                 }
             }
             catch (InputMismatchException e)
             {
                 String flush = sc.nextLine();
                 System.out.println("ERROR: Invalid character detected\n"+prompt);
                 scan = 0;
             }
        } while (scan < 0);
        return scan;
    }
}
