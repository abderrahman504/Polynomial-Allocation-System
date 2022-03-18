package linkedLists;

public class SingleLinkedList implements ILinkedList
{
    private Node head, tail;
    private int size;


    class Node
    {
        Object obj;
        Node next;

        Node(Object obj, Node next)
        {
            this.obj = obj;
            this.next = next;
        }
    }


    SingleLinkedList(Object[] elements)
    {
        //Add some stuff mate!
    }

    //Adds element to the end of the list.
    public void add(Object element)
    {
        Node newNode = new Node(element, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    
    //Adds element to the list at position index, pushing the element at that position forwards.
    public void add(Object element, int index) //Should throw exception if index is out of bounds.
    {
        Node newNode;
        if (index == 0)
        {
            newNode = new Node(element, head);
            head = newNode;
            size++;
            return;
        }
        Node currentNode = head, nextNode = head.next;
        
        while (index-- > 1)
        {
            //These 2 lines are a bit confusing.
            currentNode = nextNode;
            nextNode = nextNode.next;
        }
        newNode = new Node(element, nextNode);
        currentNode.next = newNode;
        size++;
    }

    public Object get(int index)
    {
        Node currentNode = head;
        while(index-- > 0)
        {
            currentNode = currentNode.next;
        }

        return currentNode.obj;
    }

    public void set(int index, Object element)
    {
        Node currentNode = head;
        while ()
    }

    public void remove(int index)
    {

    }

    public void clear()
    {

    }

    public int size()
    {
        return size;
    }

    





}

