package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;

    // Feel free to add more fields and constants.
    private int size;

    public ArrayHeap() {
        heap = makeArrayOfT(NUM_CHILDREN + (NUM_CHILDREN * NUM_CHILDREN) + 1);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if(this.size() <= 0) {
            throw new EmptyContainerException();
        }
        
        T min = peekMin();
        T last = this.heap[size - 1];
        this.heap[size - 1] = null;
        this.heap[0] = last;
        
        this.size--;
        
        percolateDown(0);
        
        return min;
    }

    @Override
    public T peekMin() {
        if(this.size() <= 0) {
            throw new EmptyContainerException();
        }
        
        return this.heap[0];
    }

    @Override
    public void insert(T item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        
        checkResize();
        this.heap[size] = item;
        percolateUp(size);
        size++;
    }

    @Override
    public int size() {
        return this.size;
    }
    
    private void checkResize() {
        if (this.heap.length == this.size) {
            T[] newHeap = makeArrayOfT(size * 2);
            
            for (int i = 0; i < this.heap.length; i++) {
                newHeap[i] = this.heap[i];
            }
            
            this.heap = newHeap;
        }
    }
    
    private void percolateUp(int startIndex) {
        if (startIndex > 0 && this.heap[startIndex].compareTo(getParent(startIndex)) < 0) {
            int parentIndex = getParentIndex(startIndex);
            T temp = this.heap[startIndex];
            this.heap[startIndex] = this.heap[parentIndex];
            this.heap[parentIndex] = temp;
            
            percolateUp(parentIndex);
        }
    }
    
    private void percolateDown(int startIndex) {
        if (numChildren(startIndex) > 0) {
            int lowestChildIndex = startIndex;
            T lowestChildValue = this.heap[startIndex];
            for (int i = 1; i <= numChildren(startIndex); i++) {
                T thisChildValue = getChildByNumber(startIndex, i);
                if(thisChildValue.compareTo(lowestChildValue) < 0) {
                    lowestChildIndex = getChildIndexByNumber(startIndex, i);
                    lowestChildValue = thisChildValue;
                }
            }
            
            if (lowestChildIndex != startIndex) {
                this.heap[lowestChildIndex] = this.heap[startIndex];
                this.heap[startIndex] = lowestChildValue;
                percolateDown(lowestChildIndex);
            }
        }
    }
    
    private T getChildByNumber(int myIndex, int childNum) {
        return this.heap[getChildIndexByNumber(myIndex, childNum)];
    }
    
    private T getParent(int index) {
        return this.heap[getParentIndex(index)];
    }
    
    private int getChildIndexByNumber(int myIndex, int childNum) {
        return (NUM_CHILDREN * myIndex) + childNum;
    }
    
    private int getParentIndex(int index) {
        return (index - 1) / NUM_CHILDREN;
    }
    
    private int numChildren(int index) {
        for (int i = NUM_CHILDREN; i > 0; i--) {
            if (getChildIndexByNumber(index, i) < this.size()) {
                return i;
            }
        }
        
        return 0;
    }
}

//package datastructures.concrete;
//
//import datastructures.interfaces.IPriorityQueue;
//import misc.exceptions.EmptyContainerException;
//
//
///**
// * See IPriorityQueue for details on what each method must do.
// */
//public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
//    // See spec: you must implement a implement a 4-heap.
//    private static final int NUM_CHILDREN = 4;
//
//    // You MUST use this field to store the contents of your heap.
//    // You may NOT rename this field: we will be inspecting it within
//    // our private tests.
//    private T[] heap;
//    private int capacity;
//    private int currentSize;
//
//    // Feel free to add more fields and constants.
//
//    public ArrayHeap() {
//        capacity = 1;
//        heap = makeArrayOfT(capacity);
//        currentSize = 0;
//        
//    }
//
//    /**
//     * This method will return a new, empty array of the given currentSize
//     * that can contain elements of type T.
//     *
//     * Note that each element in the array will initially be null.
//     */
//    @SuppressWarnings("unchecked")
//    private T[] makeArrayOfT(int size){
//        // This helper method is basically the same one we gave you
//        // in ArrayDictionary and ChainedHashDictionary.
//        //
//        // As before, you do not need to understand how this method
//        // works, and should not modify it in any way.
//        return (T[]) (new Comparable[size]);
//    }
//
//    @Override
//    public T removeMin() {
//        if (currentSize <= 0) {
//            throw new EmptyContainerException();
//        }
//        T result = heap[0];
//        currentSize--;
//        heap[0] = heap[currentSize];
//        heap[currentSize] = null;
//        percolateDown(0);
//        
//        return result;
//    }
//
//    @Override
//    public T peekMin() {
//        if (currentSize <= 0) {
//            throw new EmptyContainerException();
//        }
//        return heap[0];
//    }
//
//    @Override
//    public void insert(T item) {
//        if (item == null) {
//            throw new IllegalArgumentException();
//        } 
//        if (currentSize == capacity) {
//            resize();
//        }
//        heap[currentSize] = item;
//        if (currentSize != 0) {
//            percolateUp(currentSize);
//        }
//        currentSize++;
//    }
//
//    @Override
//    public int size() {
//        return this.currentSize;
//    }
//    
//    private void resize() {
//        capacity = capacity * NUM_OF_CHILDREN;
//        T[] replacement = makeArrayOfT(capacity);
//        int i = 0;
//        for (T each : heap) {
//            replacement[i] = each;
//            i++;
//        }
//        heap = replacement;
//    }
//
//    
//    private void percolateUp(int index) {
//        T temp;
//        int i = index;
//        int j = (i - 1)/NUM_CHILDREN;
//        while (i > 0 && !leq(heap[j], heap[i])) {
//            temp = heap[j];
//            heap[j] = heap[i];
//            heap[i] = temp;
//            i = j;
//            j = (j - 1) / NUM_CHILDREN;
//        }
//        
////        if (index > 0 && leq(heap[index], getParent(index))){
////            int parent = getParentIndex(index);
////            T temp = heap[index];
////            heap[index] = heap[parent];
////            heap[parent] = temp;
////            
////            percolateUp(parent);
////        }
//    }
//    
//    private void percolateDown(int index) {
//        if (index <= currentSize/NUM_CHILDREN) {
//            int children = currentSize - index * NUM_CHILDREN;
//            if (children > NUM_CHILDREN) {
//                children = NUM_CHILDREN;
//            }
//            int minimum = index;
//            for (int i = 0; i < children; i++) {
//                int place = index * NUM_CHILDREN + i;
//                if (!leq(heap[minimum], heap[place])) {
//                    minimum = place;
//                }
//            }
//            if (minimum != index) {
//                T temp = heap[minimum];
//                heap[minimum] = heap[index];
//                heap[index] = temp;
//                percolateDown(minimum);
//            }
//        }
//    }
//    
//    private boolean leq(T a, T b) {
//        return a.compareTo(b) <= 0;
//    }
//}
