/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 *****************************************************************************/
import java.util.*;
import java.io.*;

public class SocialSim
{
    public static void main(String[] args)
    {
        if(args.length > 0)
        {
            //String mode = args[0];
            if(args[0].equals("-i"))
            {
                //interactive testing environment
                UserInterface ui = new UserInterface();
                ui.menu();
            }
            else if(args[0].equals("-s"))
            {
                //simulation mode
                String netfile = args[1];
                String eventfile = args[2];
                double likeProb = Double.parseDouble(args[3]);
                double followProb = Double.parseDouble(args[4]);
                SimulationMode sm = new SimulationMode();
                sm.menu(netfile, eventfile, likeProb, followProb);
            }
            else
            {
                System.out.println("Unknown command line argument please enter again.\n");
            }
        }
        else if(args.length == 0)
        {
            System.out.println("SocialSim is a program that simulates a social network.\n"
                              +"There are two modes, interactive and simulation mode:\n\n"
                              +"Interactive mode will allow the user to add multiple event files to the simulation,\n"
                              +"as well as being able to read the status of the network and statisics of the network\n"
                              +"in between timesteps.\n"
                              +"This mode can be used by inputting <java SocialSim -i>\n\n"
                              +"Simulation mode will add the test and event files directly from the command line\n"
                              +"and take a probability of liking a post and following the poster\n"
                              +"and will initiate a timestep, printing to a file before prompting to exit or repeat the process again\n"
                              +"This mode can be used by inputting <java SocialSim -s fileName eventFileName likeProb followProb>\n"
                              +"Where fileName is the base file, eventFile name is the name of the event file,\n"
                              +"likeProb is the probability of liking a post, and followProb is the probability of following the poster.\n");

        }
    }
}
