package datastructures.sorting;

import static org.junit.Assert.assertTrue;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;
import static org.junit.Assert.fail;
import misc.exceptions.EmptyContainerException;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    public void testOneSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertEquals(3, heap.removeMin());
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testMultiple() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(4);
        heap.insert(3);
        heap.insert(4);
        heap.insert(4);
        heap.insert(7);
        assertEquals(6, heap.size());
        assertEquals(3, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(7, heap.removeMin());
        
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testPeek() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        assertEquals(2, heap.size());
        assertEquals(2, heap.peekMin());
        assertEquals(2, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(5);
        heap.insert(1);
        assertEquals(5, heap.size());
        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.removeMin());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveWithResize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(30);
        heap.insert(20);
        heap.insert(40);
        heap.insert(50);
        heap.insert(10);
        heap.insert(5);
        assertEquals(6, heap.size());
        assertEquals(5, heap.peekMin());
        assertEquals(5, heap.removeMin());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testRemoveAll() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(5);
        heap.insert(1);
        assertEquals(5, heap.size());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(0, heap.size());
        try {
            heap.removeMin();
            fail("expected EmptyContainerException");
        } catch(EmptyContainerException ex) {
            //do nothing
        }
        
        try {
            heap.peekMin();
            fail("expected EmptyContainerException");
        } catch(EmptyContainerException ex) {
            //do nothing
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(5);
        heap.insert(1);
        assertEquals(5, heap.size());
        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.removeMin());
        try {
            heap.insert(null);
            fail("expected IllegalArgumentException");
        } catch(IllegalArgumentException ex) {
            //do nothing
        }
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(4);
        heap.insert(5);
        heap.insert(1);
        assertEquals(5, heap.size());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testString() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("F");
        heap.insert("B");
        heap.insert("D");
        heap.insert("E");
        heap.insert("C");
        heap.insert("A");
        assertEquals(6, heap.size());
        assertEquals("A", heap.removeMin());
        assertEquals("B", heap.removeMin());
        assertEquals("C", heap.removeMin());
        assertEquals("D", heap.removeMin());
        assertEquals("E", heap.removeMin());
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=1*SECOND)
    public void testHeapCreation() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 1000000; i++) {
            heap.insert(i);
        }
        assertEquals(1000000, heap.size());
        assertTrue(!heap.isEmpty());
    }
}
