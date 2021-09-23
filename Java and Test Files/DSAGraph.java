/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 * CITATION: Format for this class was a largely formatted version of        *
 * graph implementation created in DSA Prac5, submitted in week 5 of the unit*
 *****************************************************************************/
import java.util.*;
import java.io.*;

/* Many of the original DSAGraph's functions were removed due to not being used
within the scope of this program, such as value in vertices, matrix displays,
BFS and DFS*/
public class DSAGraph
{
    /*single type of vertex used for both people and posts, done to allow edges
    to be created between posts and people. Led to inefficient/large amount of
    class fields within each node, some of which aren't used*/
    protected class DSAGraphVertex
    {
        //label is the name of the person/post
        protected String label;
        //follows is the list of followers of a person
        protected DSALinkedList<DSAGraphVertex> follows;
        //likes is the posts a person likes, for a post it is the people who like the post
        protected DSALinkedList<DSAGraphVertex> likes;
        //if true, the vertex is a person, if false the vertex is a post
        protected boolean person;
        /*needed to determine a person's number of followers, unincluding posts
        as when a person posts a post it is considered a follow from the poster*/
        protected int numFollows;
        //a numerical multiplier of a post, default is 1 and will always be 1 for a person
        protected int clickbait;

        public DSAGraphVertex(String inLabel, boolean inPerson, int inClickBait)
        {
            label = inLabel;
            follows = new DSALinkedList<DSAGraphVertex>();
            likes = new DSALinkedList<DSAGraphVertex>();
            person = inPerson;
            numFollows = 0;
            clickbait = inClickBait;
        }

        public String getLabel()
        {
            return label;
        }

        public boolean isPerson()
        {
            return person;
        }

        public DSALinkedList<DSAGraphVertex> getAdjacent()
        {
            return follows;
        }

        public DSALinkedList<DSAGraphVertex> getLikedPosts()
        {
            return likes;
        }

        public int getNumFollows()
        {
            return numFollows;
        }

        public void addLike(DSAGraphVertex vertex)
        {
            likes.insertLast(vertex);
        }

        public void addEdge(DSAGraphVertex vertex)
        {
            follows.insertLast(vertex);
            // if the edge being added connects to a person, then increase the number of human follows
            if(vertex.isPerson())
            {
                numFollows++;
            }
        }
    }

    private DSALinkedList<DSAGraphVertex> vertices;

    /*
    FUNCTION: Default constructor for a DSAGraph
    */
    public DSAGraph()
    {
        vertices = new DSALinkedList<DSAGraphVertex>();
    }

    /*
    FUNCTION: getVerticeList
    PURPOSE: getter function to return vertices to the menu/operations classes
    due to vertice being a private class
    */
    public DSALinkedList<DSAGraphVertex> getVerticeList()
    {
        return vertices;
    }

    /*
    FUNCTION: addVertex
    PURPOSE: adds vertex to list of vertices, going through checks to ensure
    validity
    */
    public void addVertex(String inLabel, boolean inPerson, int inClickBait)
    {
        DSAGraphVertex inVertex = new DSAGraphVertex(inLabel, inPerson, inClickBait);
        Iterator<DSAGraphVertex> verticesItr;
        DSAGraphVertex tempVertex = null;
        boolean validVertex = true;
        //if the list of vertices is empty the vertex being added will be valid
        if(vertices.isEmpty())
        {
            validVertex = true;
        }
        else
        {
            verticesItr = vertices.iterator();
            /*iterates through the non-empty list of vertices, if the vertex
            already exists then the vertex is deemed invalid*/
            while(verticesItr.hasNext())
            {
                tempVertex = verticesItr.next();
                if(tempVertex.getLabel().equals(inVertex.getLabel()))
                {
                    validVertex = false;
                }
            }
        }
        // if the vertex is valid, then insert it into the list of vertices
        if(validVertex)
        {
            vertices.insertLast(inVertex);
        }
    }

    /*
    FUNCTION: addEdge
    PURPOSE: adds an edge between 2 vertices, only if they aren't already
    connected
    */
    public void addEdge(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        if(!isAdjacent(vertex1, vertex2))
        {
            vertex1.addEdge(vertex2);
        }
    }

    /*
    FUNCTION: addToLiked
    PURPOSE: adds an edge between two vertices both ways, adding to the linked
    list 'likes', within the nodes
    */
    public void addToLiked(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        if(!isLiked(vertex1, vertex2))
        {
            vertex1.addLike(vertex2);
        }
        if(!isLiked(vertex2, vertex1))
        {
            vertex2.addLike(vertex1);
        }
    }

    /*
    FUNCTION: removeLiked
    PURPOSE: removes a 'liked' edge between two vertices
    */
    public void removeLiked(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        int index = vertex1.getLikedPosts().indexOf(vertex2);
        vertex1.getLikedPosts().deleteNode(index);
    }

    /*
    FUNCTION: removeEdge
    PURPOSE: removes an edge between 2 vertices, and decrements the number of
    follows that vertex1 has by 1.
    */
    public void removeEdge(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        int index = vertex1.getAdjacent().indexOf(vertex2);
        vertex1.getAdjacent().deleteNode(index);
        vertex1.numFollows--;
    }

    /*
    FUNCTION: getVertexCount
    PURPOSE: counts the number of vertices (both posts and people) in the list
    returning the count as an integer
    */
    public int getVertexCount()
    {
        Iterator<DSAGraphVertex> itr = vertices.iterator();
        int count = 0;

        while(itr.hasNext())
        {
            itr.next();
            count ++;
        }
        return count;
    }

    /*
    FUNCTION: getVertex
    PURPOSE: returns the vertex with a label that is identical to the String
    input
    */
    public DSAGraphVertex getVertex(String label)
    {
        Iterator<DSAGraphVertex> verticesItr = vertices.iterator();
        DSAGraphVertex tempVertex = null;
        if(verticesItr.hasNext())
        {
            tempVertex = verticesItr.next();
            while((label.equals(tempVertex.getLabel())) == false)
            {
                tempVertex = verticesItr.next();
            }
        }
        return tempVertex;
    }

    /*
    FUNCTION: getAdjacent
    PURPOSE: returns the list of follows that an input vertex has
    */
    public DSALinkedList<DSAGraphVertex> getAdjacent(DSAGraphVertex vertex)
    {
        return vertex.getAdjacent();
    }

    /*
    FUNCTION: isAdjacent
    PURPOSE: a boolean check for whether vertex1 has vertex2 as one of it's
    followers, if there is, then adjacent will return true.
    */
    public boolean isAdjacent(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        boolean adjacent = false;
        DSALinkedList<DSAGraphVertex> links = vertex1.getAdjacent();
        Iterator<DSAGraphVertex> linksItr = links.iterator();
        DSAGraphVertex tempVertex;

        while(linksItr.hasNext())
        {
            tempVertex = linksItr.next();
            if(vertex2.getLabel().equals(tempVertex.getLabel()))
            {
                adjacent = true;
            }
        }
        return adjacent;
    }

    /*
    FUNCTION: isLiked
    PURPOSE: boolean check for whether vertex1 has vertex2 in it's list of likes
    (used between a post and a person, e.g whether a post has been liked by a
    person or a person has liked a post)
    */
    public boolean isLiked(DSAGraphVertex vertex1, DSAGraphVertex vertex2)
    {
        boolean liked = false;
        DSALinkedList<DSAGraphVertex> links = vertex1.getLikedPosts();
        Iterator<DSAGraphVertex> linksItr = links.iterator();
        DSAGraphVertex tempVertex;

        while(linksItr.hasNext())
        {
            tempVertex = linksItr.next();
            if(vertex2.getLabel().equals(tempVertex.getLabel()))
            {
                liked = true;
            }
        }
        return liked;
    }

    /*
    FUNCTION: printVertices
    PURPOSE: outputs all vertices, both posts and people, mainly used for
    testing
    */
    public void printVertices()
    {
        Iterator<DSAGraphVertex> verticesItr = vertices.iterator();
        while(verticesItr.hasNext())
        {
            System.out.println(verticesItr.next().getLabel());
        }
    }

    /*
    FUNCTION: getVertices
    PURPOSE: returns all vertices as a String, used to compare between an input
    string(name of a node being found) and the list of vertices
    */
    public String getVertices()
    {
        Iterator<DSAGraphVertex> verticesItr = vertices.iterator();
        String output = "";
        while(verticesItr.hasNext())
        {
            output = output + verticesItr.next().getLabel();
        }
        return output;
    }
}
