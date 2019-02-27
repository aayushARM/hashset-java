/*
 * MyHashSet.java
 *
 * Version:
 *     1.0
 *
 * Revisions:
 *     0
 */

/**
 * This class implements a HashSet using a Binary Search Tree as underlying data structure.
 * @param <E> the generic data type that implements Comparable interface.
 * @author Ayush Soni - as2425@g.rit.edu
 */

class MyHashSet<E extends Comparable<E>> implements SetI<E> {

    private Object[] hashTable;
    private int tableSize = 128;    // The size of HashTable.
    private int size = 0;

    MyHashSet() {
        hashTable = new Object[tableSize];
    }

    /**
     * Generates an index to be used in hashTable using object's hashcode.
     * @param o the object whose index has to be found.
     * @return the index in hash table corresponding to this object.
     */
    private int custHashCode(Object o) {

        if(o == null)
            return 0;

        int hashCode = o.hashCode();
        if (hashCode < 0)
            hashCode = -hashCode;
        return hashCode % tableSize;
    }

    /**
     * Adds an element to the hash set.
     * @param e the element to be added.
     * @return true if it could be added, false otherwise.
     */
    @Override
    public boolean add(E e) {
        boolean added = true;
        int index = custHashCode(e);

        if (hashTable[index] == null)
            hashTable[index] = new BST<>(e);
        else if (((BST<E>) hashTable[index]).contains(e))
            added = false;
        else
            ((BST<E>) hashTable[index]).addNode(e);

        if (added)
            size++;
        return added;
    }

    /**
     * Add all given elements to this hash set.
     * @param c the hash set whose elements need to be added.
     * @return  true if the set changed as a result of this operation.
     */
    @Override
    public boolean addAll(SetI<? extends E> c) {
        boolean setChanged = false;
        Object[] arr = c.toArray();
        for (Object o : arr) {
            setChanged |= this.add((E) o);
        }
        return setChanged;
    }

    /**
     * Checks of this hash set contains all the given elements.
     * @param c the hash set whose elements are to be checked in this set.
     * @return true if the set contains all the elements, false otherwise.
     */
    @Override
    public boolean containsAll(SetI<?> c) {
        Object[] arr = c.toArray();
        for (Object o : arr) {
            if (!this.contains((E) o))
                return false;
        }
        return true;
    }

    /**
     * Remove all the given elements from this set.
     * @param c the hash set containing the elements to be removed.
     * @return  true if the set changed as a result of this operation.
     */
    @Override
    public boolean removeAll(SetI<?> c) {
        Object[] arr = c.toArray();
        boolean setChanged = false;
        for (Object o : arr) {
            setChanged |= this.remove((E) o);
        }
        return setChanged;
    }

    /**
     * Clear the hash set.
     */
    @Override
    public void clear() {
        hashTable = new Object[tableSize];
    }

    /**
     * Check if an element exists in the set.
     * @param o the element to be checked.
     * @return true if the element exists, false otherwise.
     */
    @Override
    public boolean contains(Object o) {

        int index = custHashCode(o);
        if (hashTable[index] != null && ((BST<E>) hashTable[index]).contains((E) o))
            return true;
        else
            return false;
    }

    /**
     * Check if the set is empty.
     * @return true if its empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Remove an element from this set.
     * @param o the element to be removed.
     * @return true if the element could be removed, false otherwise.
     */
    @Override
    public boolean remove(Object o) {
        int index = custHashCode(o);
        if (hashTable[index] != null && ((BST<E>) hashTable[index]).contains((E) o)) {
            hashTable[index] = ((BST<E>) hashTable[index]).remove((E) o);
            size--;
            return true;
        }
        return false;
    }

    /**
     * Finds the size of given set.
     * @return the total number of elements in the set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Convert the given set into array.
     * @return the Object array containing all the elements of this set.
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Object[] bstArr;
        int index = 0;
        for (Object o : hashTable) {
            if (o != null) {
                bstArr = ((BST<E>) o).toArray();
                for (Object e : bstArr)
                    arr[index++] = e;
            }
        }
        return arr;
    }

    /**
     * Create a string representation of all the elements in the set.
     * @return the String containing comma-separated elements of the set.
     */
    @Override
    public String toString() {
        Object[] arr = toArray();
        String str = "";
        int i;
        for (i = 0; i < arr.length - 1; i++)
            str += ((E) arr[i]) + ", ";
        str += ((E) arr[i]).toString();
        return str;
    }
}
