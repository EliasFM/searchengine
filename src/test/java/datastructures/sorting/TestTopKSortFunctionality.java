package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testZeroKUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());
        assertTrue(top.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testOverKUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(30, list);
        assertEquals(20, top.size());
        assertTrue(!top.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testNullInput() {
        IList<Integer> list = null;
        /*try{
            Searcher.topKSort(0, list);
            fail("expected IllegalArgumentException");
        } catch(IllegalArgumentException ex){
            //do nothing
        }
        The professor answer on piazza says to not test for null list*/
        list = new DoubleLinkedList<>();
        list.add(0);
        list.add(1);
        list.add(null);
        try {
            Searcher.topKSort(1, list);
            fail("expected IllegalArgumentException");
        } catch(IllegalArgumentException ex) {
            //do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testNull() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        list.add(null);
        
        try {
            Searcher.topKSort(21, list);
            fail("IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            //do nothing
        }

    }
    
    @Test(timeout=SECOND)
    public void testDoubleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
        IList<Integer> twoTop = Searcher.topKSort(2, top);
        assertEquals(2, twoTop.size());
        for (int i = 0; i < twoTop.size(); i++) {
            assertEquals(18 + i, twoTop.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testKBelowZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        try {
            Searcher.topKSort(-1, list);
            fail("IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            //do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testRandomUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(2);
        list.add(20);
        list.add(15);
        list.add(89);
        list.add(114);
        list.add(5);
        list.add(82);
        list.add(200);
        list.add(42);
        list.add(220);

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        assertEquals(82, top.get(0));
        assertEquals(89, top.get(1));
        assertEquals(114, top.get(2));
        assertEquals(200, top.get(3));
        assertEquals(220, top.get(4));
        
    }
    
    @Test(timeout=SECOND)
    public void testAll() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(20, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
}
