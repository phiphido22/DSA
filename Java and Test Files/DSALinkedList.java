/*****************************************************************************
 * Author: Phi Do                                                            *
 * Creation Date: 13/10/2019                                                 *
 * Date Last Modified: 22/10/2019                                            *
 * CITATION: Format for this class was a slightly formatted version of       *
 * linked list created in DSA Prac3, submitted in week 3 of the unit         *
 *****************************************************************************/
import java.util.*;

public class DSALinkedList<E> implements Iterable<E>
{
    private class DSAListNode<E>
    {
        public E value;
        public DSAListNode<E> next;
				public DSAListNode<E> prev;

        public DSAListNode(E inValue)
        {
            value = inValue;
            next = null;
        }

        public E getValue()
        {
            return value;
        }

        public void setValue(E inValue)
        {
            value = inValue;
        }

        public DSAListNode<E> getNext()
        {
            return next;
        }

        public void setNext(DSAListNode<E> newNext)
        {
            next = newNext;
        }

				public DSAListNode<E> getPrev()
        {
            return prev;
        }

				public void setPrev(DSAListNode<E> newPrev)
        {
            prev = newPrev;
        }
    }

    private DSAListNode<E> head, tail;

    /*
    FUNCTION: default constructor of a linkedlist
    */
    public DSALinkedList()
    {
        head = null;
        tail = null;
    }

    /*
    FUNCTION: getCount
    PURPOSE: counts the number of nodes within the linkedlist, returning the
    result as an integer
    */
    public int getCount()
    {
        DSAListNode<E> temp = head;
        int count = 0;
        while (temp != null)
        {
            count++;
            temp = temp.next;
        }
        return count;
    }

    /*
    FUNCTION: insertFirst
    PURPOSE: creates a new node, and inserts it at the head of the linkedlist
    */
    public void insertFirst(E newValue)
    {
        DSAListNode<E> newNode = new DSAListNode<E>(newValue);

				if(isEmpty())
				{
						head = tail = newNode;
				}
				else
				{
						newNode.setNext(head);
						newNode.setPrev(null);
						head = newNode;
				}
		}

    /*
    FUNCTION: insertLast
    PURPOSE: creates a node, and inserts it at the end of the linked list
    */
		public void insertLast(E newValue)
		{
				DSAListNode<E> newNode = new DSAListNode<E>(newValue);

        try
        {
    				if(isEmpty())
    				{
    						head = tail = newNode;
    				}
    				else
    				{
    						tail.setNext(newNode);
    						tail.setPrev(tail);
    						tail = newNode;
    				}
        }
        catch(NullPointerException e)
        {
            System.out.print("Caught NullPointerException");
        }
		}

    /*
    FUNCTION: isEmpty
    PURPOSE: checks if the linkedlist is empty, if so will return true
    */
    public boolean isEmpty()
    {
        boolean empty = (head == null);
        return empty;
    }

    /*
    FUNCTION: peekFirst
    PURPOSE: not used in the actual simulation, just purely for testharness
    purposes, will return a copy of the value of the head node.
    */
    public E peekFirst()
    {
        E nodeValue;

        if(isEmpty())
        {
            throw new IllegalArgumentException("Linked List is empty, no values found\n");
        }

        nodeValue = head.getValue();

        return nodeValue;
    }

    /*
    FUNCTION: peekLast
    PURPOSE: not used in the actual simulation, just purely for testharness
    purposes, will return a copy of the value of the tail node.
    */
    public E peekLast()
    {
        E nodeValue = null;

        if(isEmpty())
        {
            throw new IllegalArgumentException("Linked List is empty, no values found\n");
        }

        nodeValue = tail.getValue();

        return nodeValue;
    }

    /*
    FUNCTION: removeFirst
    PURPOSE: removes the head node of the list, returning said value
    */
    public Object removeFirst()
    {
        Object nodeValue = null;

        if(isEmpty()) //empty list
        {
            throw new RuntimeException("List is empty\n");
        }
				else //not empty
				{
						if(head != tail) //more than 1 node
						{
								nodeValue = head.getValue();
								head = head.getNext();
								head.setPrev(null);
						}
	        	else //single node
        		{
								nodeValue = head.getValue();
								head = tail = null;
						}
				}
        return nodeValue;
    }

    /*
    FUNCTION: removeLast
    PURPOSE: removes the tail node of the list, returning said value
    */
    public Object removeLast()
    {
        Object nodeValue = null;

				if(head == null)
				{
						System.out.print("List is empty\n");
				}
				else
				{
						if(head != tail)
						{
								nodeValue = head.getValue();
								tail = tail.getPrev();
								tail.setNext(null);
						}
						else
						{
								nodeValue = tail.getValue();
								head = tail = null;
						}
				}
				return nodeValue;
    }

    /*
    FUNCTION: deleteNode
    PURPOSE: deletes a node within the list, depending on the index/position
    of the node within the list
    */
    public void deleteNode(int index)
    {
        if(index == -1 || head == null)
        {
            System.out.print("Linked List is empty\n");
        }
        else
        {
            DSAListNode<E> temp = head;
            if(index == 0)
            {
                head = temp.next;
                return;
            }

            for(int i = 0; temp!=null && i<index-1; i++)
            {
                temp = temp.next;
            }

            if(temp == null || temp.next == null)
            {
                return;
            }

            DSAListNode<E> nextTemp = temp.next.next;
            temp.next = nextTemp;
        }
    }

    /*
    FUNCTION: indexOf
    PURPOSE: used to find the index of a node within the list, then usually
    used to delete a node from anywhere within the list using deleteNode()
    */
    public int indexOf(E newValue)
    {
        DSAListNode<E> current = head;
        int count = 0;
        int index = -1;
        while(current != null)
        {
            if(newValue == current.getValue())
            {
                index = count;
            }
            count++;
            current = current.next;
        }
        return index;
    }

		public Iterator<E> iterator()
    {
        return new DSALinkedListIterator<E>(this);
    }

    private class DSALinkedListIterator<E> implements Iterator<E>
    {
        private DSALinkedList<E>.DSAListNode<E> iterNext;
        public DSALinkedListIterator(DSALinkedList<E> theList)
        {
            iterNext = theList.head;
        }

        public boolean hasNext()
        {
            return (iterNext != null);
        }

        public E next()
        {
            E value;
            if(iterNext == null)
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }
            return value;
        }
        public void remove()
        {
            throw new UnsupportedOperationException("Not Supported");
        }
    }
}
