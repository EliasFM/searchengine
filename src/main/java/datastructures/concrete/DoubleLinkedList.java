package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;
//import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see
 * the source code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        Node<T> addition = new Node<T>(item);
        if (this.isEmpty()) {
            this.front = addition;
            this.back = addition;
        }else {
            this.back.next = addition;
            addition.prev = this.back;
            this.back = addition;
        }
        this.size++;
    }

    @Override
    public T remove() {
        if (this.isEmpty()) {
            throw new EmptyContainerException();
        }
        Node<T> current = this.back;
        this.back = this.back.prev;
        if (this.size == 1) {
            this.front = null;
        }else {
            this.back.next = null;
        }
        this.size--;
        return current.data;
    }

    @Override
    public T get(int index) {
        if (this.isEmpty()) {
            throw new EmptyContainerException();
        }if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = this.findNode(index);
        return current.data;
    }

    @Override
    public void set(int index, T item) {
        if (this.isEmpty()) {
            throw new EmptyContainerException();
        }if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> current = new Node<T>(item);
        if (index == 0) {
            current.next = this.front.next;
            this.front = current;
            if (this.size!=1) {
                this.front.next.prev = this.front;
            }else {
                this.back = current;
            }
        }else if (index == this.size-1) {
            current.prev = this.back.prev;
            this.back = current;
            this.back.prev.next = this.back;
        }else {
            Node<T> temp = this.findNode(index);
            current.prev = temp.prev;
            current.next = temp.next;
            current.prev.next = current;
            current.next.prev = current;
        }
    }

    @Override
    public void insert(int index, T item) {
        if (index < 0 || index >= this.size + 1) {
            throw new IndexOutOfBoundsException();
        } if (index == this.size || this.isEmpty()) {
            this.add(item);
        }else {
            Node<T> addition = new Node<T>(item);
            if (index == 0) {
                addition.next = this.front;
                this.front = addition;
                addition.next.prev = this.front;
            }else {
                Node<T> current = this.findNode(index);
                addition.next = current;
                addition.prev = current.prev;
                current.prev.next = addition;  
                current.prev = addition;
            }
            this.size++;
        }
    }

    @Override
    public T delete(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T result;
        if (index == this.size - 1) {
            result = this.remove();
        } else {
            Node<T> current = this.findNode(index);
            result = current.data;
            if (index == 0){
                this.front = current.next;
            }else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
            this.size--;
        }
        
        return result;
    }
   
    @Override
    public int indexOf(T item) {
        Node<T> current = this.front;
        for (int i = 0; i < size; i++) {
            if (current.data == item) {
                return i;
            } else if (current.data != null) {
                if (current.data.equals(item)) {
                    return i;
                }
            }
            current = current.next;
            }
        return -1;
        
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T other) {
        if (this.indexOf(other) == -1) {
            return false;
        }
        return true;
    }
    
    //create helper method that returns node at said index by halving n.
    private Node<T> findNode(int index){
        Node<T> current;
        if (index < this.size/2) {
            current = this.front;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }  
        }else {
            current = this.back;
            for (int i = 0; i < this.size-index-1; i++) {
                current = current.prev;
            } 
        }
        return current;
    }
    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }
        
        
        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return current!=null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T cur  = current.data;
            current = current.next;
            return cur;
        }
    }
}
