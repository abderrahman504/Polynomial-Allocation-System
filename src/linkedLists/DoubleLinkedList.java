import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface ILinkedList {
/**
* Inserts a specified element at the specified position in the list.
* @param index
* @param element
*/
public void add(int index, Object element);
/**
* Inserts the specified element at the end of the list.
* @param element
*/
public void add(Object element);
/**
* @param index
* @return the element at the specified position in this list.
*/
public Object get(int index);

/**
* Replaces the element at the specified position in this list with the
* specified element.
* @param index
* @param element
*/
public void set(int index, Object element);
/**
* Removes all of the elements from this list.
*/
public void clear();
/**
* @return true if this list contains no elements.
*/
public boolean isEmpty();
/**
* Removes the element at the specified position in this list.
* @param index
*/
public void remove(int index);
/**
* @return the number of elements in this list.
*/
public int size();
/**
* @param fromIndex
* @param toIndex
* @return a view of the portion of this list between the specified fromIndex and toIndex, inclusively.
*/
public ILinkedList sublist(int fromIndex, int toIndex);
/**
* @param o
* @return true if this list contains an element with the same value as the specified element.
*/
public boolean contains(Object o);
}


public class DoubleLinkedList implements ILinkedList {
    
    static Node first, last;
    
    static class Node{
        int data;
        Node next, prev;

        Node(int s)
        {
            this.data = s;
            this.next = this.prev = null;
        }
    }

    public void add(int index, Object element) throws NullPointerException{
        if (index < 0 || index >= size())
            throw new NullPointerException();
        else
        {
            Node newNode = new Node((Integer) element);
            newNode.data = (Integer) element;
            if (index == 0)
            {
                newNode.next = first;
                newNode.prev = null;
                first.prev = newNode;
                first = newNode;
            }
            else
            {
                Node temp = first;
                for (int i = 0; i < index - 1; i++)
                    temp = temp.next;
                newNode.prev = temp;
                newNode.next = temp.next;
                temp.next = newNode;
                temp.next.prev = newNode;
            }
        }
    }

    public void add(Object element){
        Node newNode = new Node( (Integer) element);
        newNode.data = (Integer) element;
        if (size() == 0)
        {
            first = last = newNode;
            newNode.next = newNode.prev = null;
        }
        else
        {
            newNode.next = null;
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
    }

    public Object get(int index) throws NullPointerException{
        if (index < 0 || index > size() || size() == 0)
            throw new NullPointerException();
        Node temp = first;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return temp.data;
    }

    public void set(int index, Object element) throws NullPointerException{
        if (index < size() && index >= 0 || size() == 0)
        {
            Node newNode = new Node((Integer) element);
            newNode.data = (Integer) element;

            Node temp = first;
            for (int i = 0; i < index; i++)
                temp = temp.next;
            temp.data = newNode.data;
        }
        else
        {
            throw new NullPointerException();
        }

    }

    public void clear(){
        first = null;
    }

    public boolean isEmpty(){
        return (first == null);
    }

    public void remove(int index) throws NullPointerException{
        if (index < 0 || index >= size() || size() == 0)
            throw new NullPointerException();
        else
        {
            Node currentNode = first, prevNode = null;
            if (index == 0 && currentNode != null)
            {
                first = currentNode.next;
                first.prev = null;
                return;
            }
            int counter = 0;
            while (currentNode != null)
            {
                if (counter == index)
                {
                    prevNode.next = currentNode.next;
                    currentNode.prev = prevNode;
                    break;
                }
                else
                {
                    counter ++;
                    prevNode = currentNode;
                    currentNode.prev = currentNode;
                    currentNode = currentNode.next;
                    prevNode.next = currentNode;
                }
            }
        }
    }

    public int size(){
        int counter = 0;
        Node temp = first;
        while (temp != null)
        {
            counter ++;
            temp = temp.next;
        }
        return counter;
    }

    public ILinkedList sublist(int fromIndex, int toIndex){
        if (fromIndex > toIndex || (fromIndex < 0 || toIndex >= size() || fromIndex >= size()))
            throw new NullPointerException();

        Node newNode = first;
        for (int i = 0; i < fromIndex; i++)
            newNode = newNode.next;

        DoubleLinkedList sub = new DoubleLinkedList();
        first = newNode;
        for (int j = fromIndex; j < toIndex; j++)
        {
            sub.add(newNode.data);
            newNode = newNode.next;
        }
        newNode.next = null;
        return sub;
    }

    public boolean contains(Object o){
        Node temp = first;
        for (int i = 0; i < size(); i++)
        {
            if (temp.data ==(Integer) o)
                return true;
            temp = temp.next;
        }
        return false;
    }

    public void printList()
    {
        Node currNode = first;
        int counter = 0;
        System.out.print("[");
        
        while (currNode != null) {  // Traverse 
            if (counter < size() - 1)
                System.out.print(currNode.data + ", ");
            else
                System.out.print(currNode.data);
            // Go to next node
            currNode = currNode.next;
            counter++;
        }
        System.out.print("]");
    }


    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        String sin = input.nextLine().replaceAll("\\[|\\]", "");
        String[] s = sin.split(", ");

        DoubleLinkedList obj = new DoubleLinkedList();
        if (s.length >= 1 && !(s[0].isEmpty()))
        {
            for(String i: s)
                obj.add(Integer.parseInt(i));
        }

        // The Option --
        String option = input.nextLine();
        int index, value;
        switch (option)
        {
            case "add":
                int in = input.nextInt();
                obj.add(in);
                obj.printList();
                break;

            case "addToIndex":
                index = input.nextInt();
                value = input.nextInt();
                try {
                    obj.add(index, value);
                    obj.printList();
                }
                catch (NullPointerException e)
                {
                    System.out.println("Error");
                }
                break;

            case "get":
                index = input.nextInt();
                try {
                    value = (Integer) obj.get(index);
                    System.out.println(value);
                }
                catch (NullPointerException e)
                {
                    System.out.println("Error");
                }
                break;

            case "clear":
                obj.clear();
                obj.printList();
                break;

            case "isEmpty":
                if (obj.isEmpty())
                    System.out.println("True");
                else
                    System.out.println("False");
                break;

            case "remove":
                index = input.nextInt();
                try {
                    obj.remove(index);
                    obj.printList();
                }
                catch (NullPointerException e)
                {
                    System.out.println("Error");
                }
                break;

            case "contains":
                value = input.nextInt();
                if (obj.contains(value))
                    System.out.println("True");
                else
                    System.out.println("False");
                break;

            case "set":
                index = input.nextInt();
                value = input.nextInt();
                try {
                    obj.set(index, value);
                    obj.printList();
                }
                catch (NullPointerException e)
                {
                    System.out.println("Error");
                }
                break;

            case "size":
                System.out.println(obj.size());
                break;

            case "sublist":
                int fromIndex = input.nextInt();
                int toIndex = input.nextInt();
                try {
                    obj.sublist(fromIndex, toIndex);
                    obj.printList();
                }
                catch (NullPointerException e)
                {
                    System.out.println("Error");
                }
                break;

            default:
                System.out.println("Error");
                break;
        }

    }
}
