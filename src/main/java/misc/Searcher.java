package misc;

import datastructures.interfaces.IList;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IPriorityQueue;

public class Searcher {
    /**
     * This method takes the input list and returns the top k elements
     * in sorted order.
     *
     * So, the first element in the output list should be the "smallest"
     * element; the last element should be the "biggest".
     *
     * If the input list contains fewer then 'k' elements, return
     * a list containing all input.length elements in sorted order.
     *
     * This method must not modify the input list.
     *
     * @throws IllegalArgumentException  if k < 0
     */
    public static <T extends Comparable<T>> IList<T> topKSort(int k, IList<T> input) {
        // Implementation notes:
        //
        // - This static method is a _generic method_. A generic method is similar to
        //   the generic methods we covered in class, except that the generic parameter
        //   is used only within this method.
        //
        //   You can implement a generic method in basically the same way you implement
        //   generic classes: just use the 'T' generic type as if it were a regular type.
        //
        // - You should implement this method by using your ArrayHeap for the sake of
        //   efficiency.
        /* list iterator, insert n = n log n
         * remove + add k = k log k + k
         * or remove n-k and add + remove k = n log n + k
         * 
         */
        if (k < 0 || input == null) {
            throw new IllegalArgumentException();
        }
        IPriorityQueue<T> temp = new ArrayHeap<T>();
        IList<T> result = new DoubleLinkedList<T>();
        
        if(k == 0) {
            return result;
        }
        if(input.size() < k) {
            k = input.size();
        }
        
        if (k != 0){
            for (T each : input) {
                if (each == null) {
                    throw new IllegalArgumentException();
                }
                if (temp.size() < k) {
                    temp.insert(each);
                }else {
                    if (temp.peekMin().compareTo(each) < 0) {
                        temp.removeMin();
                        temp.insert(each);
                    }
                }
            }
            
            for (int i = 0; i < k; i++){
                result.add(temp.removeMin());
            }
        }
        return result;
    }
}
