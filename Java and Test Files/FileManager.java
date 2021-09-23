/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 *****************************************************************************/
import java.util.*;
import java.io.*;

public final class FileManager
{

    /*
    FUNCTION: loadNetwork
    PURPOSE: reads a file based on the input of the filename, then splits the
    values of the file and puts them into a graph that has been imported into
    the function.
    */
    public static void loadNetwork(DSAGraph graph, String fileName)
    {
          FileInputStream fis = null;
          InputStreamReader isr;
          BufferedReader br;
          String line, personOne, personTwo;
          try
          {
              fis = new FileInputStream(fileName);
              isr = new InputStreamReader(fis);
              br = new BufferedReader(isr);
              String[] procLine;

              line = br.readLine();
              while(line != null)
              {
                  procLine = line.split(":");
                  if(procLine.length > 3 && procLine.length < 1)
                  {
                       throw new IllegalArgumentException("Invalid File");
                  }
                  else if(procLine.length == 1)
                  {
                      //default form for adding a node/person to the graph
                      personOne = procLine[0];
                      graph.addVertex(personOne, true, 1);
                  }
                  else if(procLine.length == 2)
                  {
                      switch (procLine[0]) {
                        //case A is adding a person/node to the graph
                        case "A": personOne = procLine[1];
                                  graph.addVertex(personOne, true, 1);
                                  break;
                        //case R is to remove/delete a person from the graph
                        case "R": personOne = procLine[1];
                                  Operations.deleteNode(graph, personOne);
                                  break;
                        //if adding follows is in default form of person:person then add a follow
                        default:  personOne = procLine[0];
                                  personTwo = procLine[1];
                                  graph.addVertex(personOne, true, 1);
                                  graph.addVertex(personTwo, true, 1);
                                  graph.addEdge(graph.getVertex(personOne), graph.getVertex(personTwo));
                        }
                  }
                  else if(procLine.length == 3)
                  {
                      switch (procLine[0]) {
                        //case F reading an event file where F:person:followed by this person
                        case "F": personOne = procLine[1];
                                  personTwo = procLine[2];
                                  graph.addVertex(personOne, true, 1);
                                  graph.addVertex(personTwo, true, 1);
                                  graph.addEdge(graph.getVertex(personOne), graph.getVertex(personTwo));
                                  break;
                        //case P is creating a post, format P:poster:content of post
                        case "P": personOne = procLine[1];
                                  String post = procLine[2];
                                  graph.addVertex(personOne, true, 1);
                                  graph.addVertex(post, false, 1);
                                  graph.addEdge(graph.getVertex(personOne), graph.getVertex(post));
                                  break;
                        //case U is unfollow, U:person being unfollowed:person unfollowing
                        case "U": personOne = procLine[1];
                                  personTwo = procLine[2];
                                  graph.removeEdge(graph.getVertex(personOne), graph.getVertex(personTwo));
                                  break;
                        default:  System.out.println("Invalid line in file detected\n");
                                  break;
                        }
                  }
                  else if(procLine.length == 4)
                  {
                      //post, but including a different clickbait factor (not default of 1)
                      if(procLine[0].equals("P"))
                      {
                          personOne = procLine[1];
                          String post = procLine[2];
                          int clickbait = Integer.parseInt(procLine[3]);
                          graph.addVertex(personOne, true, 1);
                          graph.addVertex(post, false, clickbait);
                          graph.addEdge(graph.getVertex(personOne), graph.getVertex(post));
                      }
                      else
                      {
                          System.out.println("Invalid line in file detected\n");
                      }
                  }
                  line = br.readLine();
              }
          }
          catch(IOException e)
          {
              if(fis != null)
              {
                  try
                  {
                      fis.close();
                  }
                  catch(IOException ex2)
                  {}
              }
              throw new IllegalArgumentException("Unable to process file\n"
                                                    +"Closing Program");
          }
          catch(IllegalArgumentException e)
          {
             if(fis != null)
             {
                 try
                 {
                       fis.close();
                 }
                 catch(IOException ex2){}
             }
             System.out.println("Error in file processing: " + e.getMessage());
             System.out.println("Closing Program");
          }
    }

    /*
    FUNCTION: printStorage
    PURPOSE: outputs the results of the graph, in the same format as it was
    imported, (e.g A:person, P:person:postcontents) does not output the likes
    of a post due to no integration of reading likes on a post initially.
    */
    public static void printStorage(DSAGraph graph, String fileOutName)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        Scanner sc = new Scanner(System.in);
        DSALinkedList<DSAGraph.DSAGraphVertex> vertices = graph.getVerticeList();
        Iterator<DSAGraph.DSAGraphVertex> verticesItr, followItr;
        DSAGraph.DSAGraphVertex tempVertex, tempAdjacVertex, postOrPerson;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks;
        String output;

        try
        {
            fileStrm = new FileOutputStream(fileOutName);
            pw = new PrintWriter(fileStrm);

            verticesItr = vertices.iterator();
            //print the nodes of the list
            while(verticesItr.hasNext())
            {
                tempVertex = verticesItr.next();
                //if the tempVertex is a person then output Add:person
                if(tempVertex.isPerson() == true)
                {
                    output = tempVertex.getLabel();
                    pw.println("A:"+output);
                }
            }
            //printing the follows/edges
            //iterate through the nodes
            followItr = vertices.iterator();
            while(followItr.hasNext())
            {
                tempAdjacVertex = followItr.next();
                if(tempAdjacVertex.isPerson() == true)
                {
                    tempLinks = tempAdjacVertex.getAdjacent();
                    //iterator to iterate the list of links within each node
                    Iterator<DSAGraph.DSAGraphVertex> linksItr = tempLinks.iterator();
                    while(linksItr.hasNext())
                    {
                        postOrPerson = linksItr.next();
                        // if the node is a person, then print out the person and their followers
                        if(postOrPerson.isPerson() == true)
                        {
                            output = tempAdjacVertex.getLabel()+":"+postOrPerson.getLabel();
                            pw.println("F:"+output);
                        }
                        // if the node is a post, then output the person posting, then the contents of the post
                        else
                        {
                            output = tempAdjacVertex.getLabel()+":"+postOrPerson.getLabel();
                            pw.println("P:"+output);
                        }
                    }
                }
            }
            pw.close();
        }
        catch(IOException e)
        {
            if(fileStrm != null)
            {
                try
                {
                      fileStrm.close();
                }
                catch(IOException ex2){}
            }
            System.out.println("Error in file processing: " + e.getMessage());
        }
    }
}
