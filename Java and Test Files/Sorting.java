/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 * CITATION: A large amount of the sorting for this assignment was referred  *
 * from the Sorts.java in practical1 of the Data Structures and Algorithms   *
 * unit.                                                                     *
 *****************************************************************************/
import java.util.*;

public class Sorting
{
    /*
    FUNCTION: toArray
    PURPOSE: converts the linkedlist/graph to an array, to allow for easier use
    of sorting by using a sort function from previous sorts discussed in DSA
    Prac 1 and 8.
    */
    public static DSAGraph.DSAGraphVertex[] toArray(DSAGraph graph)
    {
        DSALinkedList<DSAGraph.DSAGraphVertex> vertices = graph.getVerticeList();
        Iterator<DSAGraph.DSAGraphVertex> verticesItr = vertices.iterator();
        DSAGraph.DSAGraphVertex tempVertex;
        DSALinkedList<DSAGraph.DSAGraphVertex> tempLinks;
        int size = vertices.getCount();

        DSAGraph.DSAGraphVertex[] arr = new DSAGraph.DSAGraphVertex[size];
        for (int i = 0; i < size; i++)
        {
            tempVertex = verticesItr.next();
            arr[i] = tempVertex;
        }
        return arr;
    }

    /*
    FUNCTION: peopleSort
    PURPOSE: uses insertion sort to sort the array/graph by the number of
    followers a person has, from largest to smallest amount of followers.
    CITATION: This function is taken from the DSA Sorts.java in Practical1/8,
    and is altered to conpare a different set of values instead.
    */
    public static void peopleSort(DSAGraph.DSAGraphVertex[] A)
    {
        for(int nn = 0; nn < A.length; nn++) //start inserting at element 1
        {
            int ii = nn; //start from the last item and go backwards
            while(ii > 0 && (A[ii-1].getNumFollows() < A[ii].getNumFollows())) //insert into subarray to left of nn
            {
                DSAGraph.DSAGraphVertex temp = A[ii]; //move insert value up by one via swapping
                A[ii] = A[ii-1];
                A[ii-1] = temp;

                ii = ii - 1;
            }
        }
        System.out.println("PEOPLE IN ORDER OF POPULARITY:");
        System.out.println("==============================\n");
        for(int i = 0; i < A.length; i++)
        {
            if(A[i].isPerson() == true)
            {
                System.out.println(A[i].getLabel()+":\nFollowers: "+A[i].getNumFollows()+"\n");
            }
        }
    }

    /*
    FUNCTION: postSort
    PURPOSE: uses insertion sort to sort the array/graph by the number of
    likes a post has, from largest to smallest amount of likes.
    CITATION: This function is taken from the DSA Sorts.java in Practical1/8,
    and is altered to conpare a different set of values instead.
    */
    public static void postSort(DSAGraph.DSAGraphVertex[] A)
    {
        for(int nn = 0; nn < A.length; nn++) //start inserting at element 1
        {
            int ii = nn; //start from the last item and go backwards
            while(ii > 0 && (A[ii-1].getLikedPosts().getCount() < A[ii].getLikedPosts().getCount())) //insert into subarray to left of nn
            {
                DSAGraph.DSAGraphVertex temp = A[ii]; //move insert value up by one via swapping
                A[ii] = A[ii-1];
                A[ii-1] = temp;

                ii = ii - 1;
            }
        }
        // does not print the poster, only the name of the post and the number of likes
        System.out.println("POSTS IN ORDER OF POPULARITY:");
        System.out.println("=============================\n");
        for(int i = 0; i < A.length; i++)
        {
            if(A[i].isPerson() == false)
            {
                System.out.println(A[i].getLabel()+"\nLikes: "+A[i].getLikedPosts().getCount()+"\n");
            }
        }
    }

    /*
    FUNCTION: swap
    PURPOSE: swaps the two arrays positions given 2 imports
    CITATION: This function is taken from the DSA Sorts.java in Practical1/8,
    used for the sorts funtions.
    */
    private static void swap(int[] A, int idx1, int idx2)
    {
        int temp = A[idx1];
        A[idx1] = A[idx2];
        A[idx2] = temp;
    }
}
