package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */

public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int capacity;
    private int size;

    // You're encouraged to add extra fields (and helper methods) though!

    public ArrayDictionary() {
        this.capacity = 100;
        this.pairs = makeArrayOfPairs(capacity);
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    private int indexOf(K key) {
        for (int i = 0; i < this.size(); i++) {          
            if (this.pairs[i].getKey() == key) {
                return i;
            } else if (this.pairs[i].getKey() != null) {
                if (this.pairs[i].getKey().equals(key)) {
                    return i;
                }
            }            
        } 
        return -1;
    }
    
    @Override
    public V get(K key) {        
        int index = this.indexOf(key); 
        if (index == -1) {
            throw new NoSuchKeyException();
        }        
        return this.pairs[index].getValue();
    }

    @Override
    public void put(K key, V value) {
        if (this.size() == this.capacity) {
            Pair<K, V>[] big = makeArrayOfPairs(this.capacity * 2);
            for (int i = 0; i < this.capacity; i++) {
                big[i] = pairs[i];
            }
            this.pairs = big;
            this.capacity = this.capacity * 2;
        }
        int index = this.indexOf(key); 
        if (index != -1) {
            this.pairs[index].value = value;
        } else {
            this.pairs[this.size] = new Pair<K, V>(key, value);
            this.size++;
        }
    }

    @Override
    public V remove(K key) {
        int index = this.indexOf(key); 
        if (index == -1) {
            throw new NoSuchKeyException();
        }
        V result = this.get(key);
        this.pairs[index] = this.pairs[this.size() - 1];
        this.pairs[this.size() - 1] = null;
        this.size--;
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        return (this.indexOf(key) != -1);
    }

    @Override
    public int size() {
        return this.size;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return this.value;
        }

        public K getKey() {
            return this.key;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }






    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<>(pairs, size);
    }

    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        private int current;
        private Pair<K, V>[] pairs;
        private int max;
        

        public ArrayDictionaryIterator(Pair<K, V>[] pair, int siz) {
            this.current = 0;
            this.pairs = pair;
            this.max = siz;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return current+1 <= max;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            KVPair<K, V> cur = new KVPair<K, V>(pairs[current].getKey(), pairs[current].getValue());
            current++;
            return cur;
        }
    }
}
