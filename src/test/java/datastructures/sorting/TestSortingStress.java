package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    
    @Test(timeout=10*SECOND)
    public void testTopKSmall() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(100, list);
        assertEquals(100, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(999900 + i, top.get(i));
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testTopKLarge() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(10000, list);
        assertEquals(10000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(990000 + i, top.get(i));
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testTopKAllLarge() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(100000, list);
        assertEquals(100000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testTopKSortNoGet() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(1000000, list);
        assertEquals(1000000, top.size());
        
    }
    
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapCreation() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000000; i++) {
            heap.insert(i);
        }
        assertEquals(10000000, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapRemoveSmall() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000000; i++) {
            heap.insert(i);
        }
        assertEquals(10000000, heap.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(9999900, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapRemoveLarge() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000000; i++) {
            heap.insert(i);
        }
        assertEquals(10000000, heap.size());
        for (int i = 0; i < 5000000; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(5000000, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapRemoveAll() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 10000000; i++) {
            heap.insert(i);
        }
        assertEquals(10000000, heap.size());
        for (int i = 0; i < 10000000; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
}
