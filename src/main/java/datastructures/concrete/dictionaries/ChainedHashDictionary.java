package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private int capacity;
    private int load;
    

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        this.capacity = 31;
        this.load = 0;
        this.chains = makeArrayOfChains(capacity);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }
    
    /* pre: key 
     * post:returns value
     * checks index associated with hashcode of key
     * iterates through dictionary associated with that key
     */
    @Override
    public V get(K key) {
        int index = getIndex(key);
        IDictionary<K, V> dict = chains[index];
        if (dict!=null) {
            if (dict.containsKey(key)) {
                return dict.get(key);
            }
        }       
        throw new NoSuchKeyException();
    }
    /*pre: key
     * post: index = 0
     * if key is not null key's hashcode modulus capacity
     * if index negative, negate
     */
    private int getIndex(K key) {
        int index = -1;
        if (key != null) {
            index = key.hashCode()%capacity;
        }
        if (index < 0) {
            index *= -1;
        }
        return index;
    }
    /*pre: key and value
     * post: key and value are added to dictionary at array index designated from hashcode of key
     * resize if load factor greater than .75
     */
    @Override
    public void put(K key, V value) {
        if ((load + 1.0) / capacity > .75) {
            resize();
        }
        putKV(new KVPair<K, V>(key, value));
        
        
    }
    /*post: double size of array
     * 
     */
    private void resize() {
        capacity = capacity*2;
        IDictionary<K, V>[] temp = chains;
        chains = makeArrayOfChains(capacity);
        for (int i = 0; i < capacity/2; i++) {
            if (temp[i]!=null) {
                IDictionary<K, V> each = temp[i];
                for (KVPair<K, V> element: each) {
                    putKV(element);
                    load--;
                }
            }
        }
        
    }
    //puts KVPair into chains
    //increase load
    private void putKV(KVPair<K, V> element) {
        int index = getIndex(element.getKey());
        if (chains[index] == null) {
            chains[index] = new ArrayDictionary<>(); 
            //load++;
        }
        if (!chains[index].containsKey(element.getKey())) {
            load++;
        }
        chains[index].put(element.getKey(), element.getValue());
        
        
    }
    /* pre: key
     * post: removes key and value and decrease load
     * (non-Javadoc)
     * @see datastructures.interfaces.IDictionary#remove(java.lang.Object)
     */
    @Override
    public V remove(K key) {
        int index = getIndex(key);
        IDictionary<K, V> temp = chains[index];
        if (temp!=null) {
            if (temp.containsKey(key)) {
                V result = temp.get(key);
                temp.remove(key);
                load--;
                return result;
            }
        }
        throw new NoSuchKeyException();
    }
    /* pre: key
     * post: checks for key
     * (non-Javadoc)
     * @see datastructures.interfaces.IDictionary#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        if (index != -1) {
            IDictionary<K, V> temp = chains[index];
            if (temp!= null && temp.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return load;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains, load);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     *    We STRONGLY recommend you spend some time doing this before
     *    coding. Getting the invariants correct can be tricky, and
     *    running through your proposed algorithm using pencil and
     *    paper is a good way of helping you iron them out.
     *
     * 3. Think about what exactly your *invariants* are. As a
     *    reminder, an *invariant* is something that must *always* be 
     *    true once the constructor is done setting up the class AND 
     *    must *always* be true both before and after you call any 
     *    method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int current;
        private int size;
        private int load;
        private Iterator<KVPair<K, V>> temp;

        public ChainedIterator(IDictionary<K, V>[] chain, int siz) {
            this.chains = chain;
            this.current = 0;
            this.size = siz;
            this.load = 0;
            if (hasNext()) {
                while (this.chains[current]==null) {
                    current++;
                }
                this.temp = this.chains[current].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return load + 1 <= size;
        }

        @Override
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
          
            if (temp==null || chains[current].isEmpty() || !temp.hasNext()) {
                current++;
                while (chains[current] == null || chains[current].isEmpty()) {
                    current++;
                }
                temp = chains[current].iterator();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            load++;
            return temp.next();
        }
    }
}
