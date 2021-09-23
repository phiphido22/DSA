/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 *****************************************************************************/
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;

public class Operations
{
    /*
    FUNCTION: findNode
    PURPOSE: takes the graph and a string of the node to be found, then if
    the node exists in the graph it will print to the user a message notifying
    them the node exists already.
    */
    public static void findNode(DSAGraph graph, String label)
    {
        if(graph.getVertices().contains(label))
        {
            System.out.println("Node is present within the graph\n");
        }
        else
        {
            System.out.println("Node does not exist in this graph\n");
        }
    }

    /*
    FUNCTION: insertNode
    PURPOSE: inserts a new person into the graph, with name 'label', and default
    clickbait factor of 1 as they are a person, boolean true as they are a person
    not a post
    */
    public static void insertNode(DSAGraph graph, String label)
    {
        graph.addVertex(label, true, 1);
    }

    /*
    FUNCTION: deleteNode
    PURPOSE: a function that will iterate through the graph, finding all nodes
    which have a follower called 'label', deleting that node from the list and
    removing them from all posts' "liked by" list, before removing the node
    from the graph entirely.
    */
    public static void deleteNode(DSAGraph graph, String label)
    {
        DSALinkedList<DSAGraph.DSAGraphVertex> vertices = graph.getVerticeList();
        Iterator<DSAGraph.DSAGraphVertex> verticesItr = vertices.iterator();
        DSAGraph.DSAGraphVertex tempVertex, tempNext;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks, tempLikes;

        DSAGraph.DSAGraphVertex vertex1 = graph.getVertex(label);
        //remove the node from all other node's followers lists
        while(verticesItr.hasNext())
        {
            //iterate through all the nodes list of followers to remove the node
            tempVertex = verticesItr.next();
            tempLinks = tempVertex.getAdjacent();
            Iterator<DSAGraph.DSAGraphVertex> linksItr = tempLinks.iterator();
            while(linksItr.hasNext())
            {
                tempNext = linksItr.next();
                if(tempNext.getLabel().equals(label))
                {
                    graph.removeEdge(tempVertex, tempNext);
                }
            }
            //iterate all nodes' list of likes to remove the node to be removed
            tempLikes = tempVertex.getLikedPosts();
            Iterator<DSAGraph.DSAGraphVertex> likesItr = tempLikes.iterator();
            while(likesItr.hasNext())
            {
                tempNext = likesItr.next();
                if(tempNext.getLabel().equals(label))
                {
                    /*removes the post from the to-be deleted nodes liked list,
                    and removes the to-be deleted node from the posts' liked
                    by list*/
                    graph.removeLiked(tempVertex, tempNext);
                    graph.removeLiked(tempNext, tempVertex);
                }
            }
        }
        int index = vertices.indexOf(vertex1);
        vertices.deleteNode(index);
        //delete the node from others followers lists
        System.out.println(vertex1.getLabel()+" has been deleted");
    }

    /*
    FUNCTION: addFollow
    PURPOSE: prompts the user to input 2 strings, of two existing nodes within
    the graph. If either of the two nodes do not exist then the function will
    return to the menu.
    */
    public static void addFollow(DSAGraph graph)
    {
        DSAGraph.DSAGraphVertex vertex1 = graph.getVertex(stringInput("What is the label of the person being followed?\n"));
        if(graph.getVertices().contains(vertex1.getLabel()))
        {
            DSAGraph.DSAGraphVertex vertex2 = graph.getVertex(stringInput("What is the label of the person following?\n"));
            if(graph.getVertices().contains(vertex2.getLabel()))
            {
                graph.addEdge(vertex1, vertex2);
            }
            else
            {
                System.out.println("Node does not exist within the graph\n");
            }
        }
        else
        {
            System.out.println("Node does not exist within the graph\n");
        }
    }

    /*
    FUNCTION: removeFollow
    PURPOSE: prompts the user to input 2 strings of 2 existing nodes, then
    deletes the edge if the two nodes exist, if they do not exist then the
    function returns to menu
    */
    public static void removeFollow(DSAGraph graph)
    {
      DSAGraph.DSAGraphVertex vertex1 = graph.getVertex(stringInput("What is the label of the person being followed?\n"));
      if(graph.getVertices().contains(vertex1.getLabel()))
      {
          DSAGraph.DSAGraphVertex vertex2 = graph.getVertex(stringInput("What is the label of the person following?\n"));
          if(graph.getVertices().contains(vertex2.getLabel()))
          {
              graph.removeEdge(vertex1, vertex2);
          }
          else
          {
              System.out.println("Node does not exist within the graph\n");
          }
      }
      else
      {
          System.out.println("Node does not exist within the graph\n");
      }
    }

    /*
    FUNCTION: newPost
    PURPOSE: prompts the user to input the name/contents of the post, then
    prompts the user for the person posting. if the person does not exist then
    the function will return to the menu.
    */
    public static void newPost(DSAGraph graph)
    {
        String post = stringInput("What is the post to be added?\n");
        DSAGraph.DSAGraphVertex poster = graph.getVertex(stringInput("Who is posting this post/event?\n"));
        if(graph.getVertices().contains(poster.getLabel()))
        {
            graph.addVertex(post, false, 1);
            graph.addEdge(poster, graph.getVertex(post));
        }
        else
        {
            System.out.println("Node does not exist within the graph\n");
        }
    }

    /*
    FUNCTION: displayFollowers
    PURPOSE: function to print all people and their followers and follow count
    to the terminal
    */
    public static void displayFollowers(DSAGraph graph)
    {
        Iterator<DSAGraph.DSAGraphVertex> verticesItr = graph.getVerticeList().iterator();
        DSAGraph.DSAGraphVertex tempVertex;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks;
        String output = "";

        System.out.println("\nFOLLOWING LIST");
        System.out.println("==============\n");
        // iterates through the list of vertices
        while(verticesItr.hasNext())
        {
            tempVertex = verticesItr.next();
            // if the vertex is a person
            if(tempVertex.isPerson() == true)
            {
                tempLinks = tempVertex.getAdjacent();
                Iterator<DSAGraph.DSAGraphVertex> linksItr = tempLinks.iterator();
                output = tempVertex.getLabel()+": ";
                /* iterate through the person's list of followers and check if
                the followers are people, printing the person followed by all
                their followers */
                while(linksItr.hasNext())
                {
                    DSAGraph.DSAGraphVertex tempNext = linksItr.next();
                    if(tempNext.isPerson() == true)
                    {
                        output = output + tempNext.getLabel() + " ";
                    }
                }
                // prints the person and the amount of followers they have
                System.out.println("\n"+tempVertex.getLabel()+" has "+tempVertex.getNumFollows()+" followers");
                System.out.println(output);
            }
        }
    }

    /*
    FUNCTION: displayPosts
    PURPOSE: function that prints all the posters, the post they posted and the
    amount of people who liked the post
    */
    public static void displayPosts(DSAGraph graph)
    {
        Iterator<DSAGraph.DSAGraphVertex> verticesItr = graph.getVerticeList().iterator();
        DSAGraph.DSAGraphVertex tempVertex;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks;
        String output = "";

        System.out.println("\nPOSTS");
        System.out.println("=====");
        while(verticesItr.hasNext())
        {
            tempVertex = verticesItr.next();
            if(tempVertex.isPerson() == true)
            {
                tempLinks = tempVertex.getAdjacent();
                Iterator<DSAGraph.DSAGraphVertex> linksItr = tempLinks.iterator();
                output = tempVertex.getLabel()+": ";
                while(linksItr.hasNext())
                {
                    DSAGraph.DSAGraphVertex tempNext = linksItr.next();
                    if(tempNext.isPerson() == false)
                    {
                        System.out.println(tempVertex.getLabel()+" posted: "+tempNext.getLabel());
                        System.out.println(tempNext.getLikedPosts().getCount()+" people have liked this post.\n");
                    }
                }
            }
        }
    }

    /*
    FUNCTION: displayLikedPosts
    PURPOSE: displays a list of people and the posts that each person has liked
    */
    public static void displayLikedPosts(DSAGraph graph)
    {
        Iterator<DSAGraph.DSAGraphVertex> verticesItr = graph.getVerticeList().iterator();
        DSAGraph.DSAGraphVertex tempVertex;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks;
        String output = "";

        System.out.println("\nPEOPLE'S LIKED POSTS");
        System.out.println("====================\n");
        while(verticesItr.hasNext())
        {
            tempVertex = verticesItr.next();
            if(tempVertex.isPerson() == true)
            {
                tempLinks = tempVertex.getLikedPosts();
                Iterator<DSAGraph.DSAGraphVertex> linksItr = tempLinks.iterator();
                output = tempVertex.getLabel()+":\n";
                while(linksItr.hasNext())
                {
                    DSAGraph.DSAGraphVertex tempNext = linksItr.next();
                    if(tempNext.isPerson() == false)
                    {
                        output = output + tempNext.getLabel() + "\n";
                    }
                }
                System.out.println(output);
            }
        }
    }

    /*
    FUNCTION: displayStats
    PURPOSE: displays the statistics of the graph, the people in order of
    popularity and the posts in order of popularity
    */
    public static void displayStats(DSAGraph graph)
    {
        DSAGraph.DSAGraphVertex[] convertedList = Sorting.toArray(graph);
        Sorting.peopleSort(convertedList);
        Sorting.postSort(convertedList);
    }

    /*
    FUNCTION: timeStep
    PURPOSE: function that emulates a real life timestep, where if one person
    posts, theres a chance for their followers to like the post, exposing their
    followers to the post. If the post is liked, there is a chance that if the
    person liking the post, they will follow the original poster.
    */
    public static void timeStep(DSAGraph graph, double likeProb, double followProb)
    {
        DSALinkedList<DSAGraph.DSAGraphVertex> vertices = graph.getVerticeList();
        Iterator<DSAGraph.DSAGraphVertex> verticesItr, followItr, followfollowItr, linksItr;
        DSAGraph.DSAGraphVertex tempVertex, postersFollowers, tempPost;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks, PostersFollowersList;

        // iterate through all people and posts
        followItr = vertices.iterator();
        while(followItr.hasNext())
        {
            //tempVertex is the original person
            tempVertex = followItr.next();
            tempLinks = tempVertex.getAdjacent();
            //iterator to iterate the list of followers/posts
            linksItr = tempLinks.iterator();
            while(linksItr.hasNext())
            {
                //tempNext is the original person's post
                tempPost = linksItr.next();
                //if the original person has a post in their follower list
                if(tempPost.isPerson() == false)
                {
                    PostersFollowersList = tempVertex.getAdjacent();
                    followfollowItr = PostersFollowersList.iterator();
                    //iterate through the original person's followers for people this time
                    while(followfollowItr.hasNext())
                    {
                        //postersFollowers is the original poster's followers
                        postersFollowers = followfollowItr.next();
                        if(Math.random() < likeProb)
                        {
                            //if the follower/post is a person the follower does not like the post currently
                            if(postersFollowers.isPerson() == true && graph.isAdjacent(postersFollowers, tempPost) == false)
                            {
                                graph.addToLiked(postersFollowers, tempPost);
                                if(Math.random() < followProb && graph.isAdjacent(tempVertex, postersFollowers) == false)
                                {
                                    graph.addEdge(tempVertex, postersFollowers);
                                    //graph.addEdge(postersFollowers, tempVertex);
                                }
                                timeStepRecursive(graph, likeProb, followProb, tempVertex, postersFollowers, tempPost);
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    FUNCTION: timeStepRecursive
    PURPOSE: recursive function that will repeat the timeStep from the
    followers of the poster's followers. Used in the case that the posters'
    followers like the post this function will recurse and iterate through the
    followers' own followers.
    */
    public static void timeStepRecursive(DSAGraph graph, double likeProb, double followProb, DSAGraph.DSAGraphVertex tempVertex, DSAGraph.DSAGraphVertex followersFollowers, DSAGraph.DSAGraphVertex tempPost)
    {
        Iterator<DSAGraph.DSAGraphVertex> followfollowItr;
        DSALinkedList<DSAGraph.DSAGraphVertex> PostersFollowersList;

        PostersFollowersList = followersFollowers.getAdjacent();
        followfollowItr = PostersFollowersList.iterator();
        while(followfollowItr.hasNext())
        {
            DSAGraph.DSAGraphVertex followers = followfollowItr.next();
            if(Math.random() < likeProb)
            {
                //if the follower/post is a person the follower does not like the post currently
                if(followers.isPerson() == true && graph.isAdjacent(followers, tempPost) == false)
                {
                    graph.addToLiked(followers, tempPost);
                    if(Math.random() < followProb && graph.isAdjacent(tempVertex, followers) == false)
                    {
                        graph.addEdge(tempVertex, followers);
                        //graph.addEdge(followers, tempVertex);
                    }
                    timeStepRecursive(graph, likeProb, followProb, tempVertex, followers, tempPost);
                }
            }
        }
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
