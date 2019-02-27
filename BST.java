/*
 * BST.java
 *
 * Version:
 *     1.0
 *
 * Revisions:
 *     0
 */

/**
 * This class represents a single node of a Binary search tree.
 * @param <E> the generic data type that implements Comparable interface.
 * @author Ayush Soni - as2425@g.rit.edu
 */
class BST<E extends Comparable<E>> {

    private static Object[] elems;
    private static int index;
    private int numElements = 1;
    private E data;
    private BST<E> left;
    private BST<E> right;

    BST(E e) {
        data = e;
        left = right = null;
    }

    /**
     * Search for the appropriate location for the new element recursively,
     * and add node at that location.
     * @param e the element to be added.
     */
    void addNode(E e) {
        if (e.compareTo(data) < 0) {
            // Element belongs to left sub-tree
            if (left != null) {
                left.addNode(e);
            } else {
                left = new BST<>(e);
            }

        } else {
            // Element belongs to right sub-tree
            if (right != null) {
                right.addNode(e);
            } else {
                right = new BST<>(e);
            }

        }
        numElements++;
    }

    /**
     * Get all the elements in the tree.
     * @return a string representing all the elements.
     */
    Object[] toArray(){
        elems = new Object[numElements];
        index = 0;
        inOrderTrav();
        return elems;
    }

    /**
     * Traverse the tree in In-order fashion(adds the elements to elems[] array
     * in sorted order).
     */
    private void inOrderTrav() {

        if(left != null) {
            left.inOrderTrav();
        }
        elems[index++] = data;
        if(right != null) {
            right.inOrderTrav();
        }
    }

    /**
     * Remove an element from the tree.
     * @param e the element to be removed
     * @return the reference to root node of modified tree.
     */
    BST<E> remove(E e){
        BST<E> root = this;
        if(e == null && root.data == null)      // Special case when e == null
            root = null;
        else if(e.compareTo(data) < 0)          // e is in left sub-tree, hence call remove on it.
            root.left = root.left.remove(e);
        else if(e.compareTo(data) > 0)          // e is in right sub-tree, hence call remove on it.
            root.right = root.right.remove(e);
        else{
            // e is at this node.
            if(root.left == null && root.right == null) // deletion of leaf node.
                root = null;
            else if(root.left == null)                  // deletion of node w/ only right child.
                root = root.right;
            else if(root.right == null)                 // deletion of node w/ only left child.
                root = root.left;
            else{                                       // deletion of node w/ both left and right children.
                BST<E> temp = root.right.getMin();
                root.data = temp.data;
                root.right = root.right.remove(temp.data);
            }
        }
        numElements--;
        return root;
    }

    /**
     * Traverse the tree in Pre-order fashion, used for searching an element in
     * the tree.
     * @param e the element to be searched.
     * @return true if the element exists, false otherwise.
     */
    boolean contains(E e){
        boolean b1 = false, b2 = false;
        if( (e == null && data == null) || data.equals(e))
            return true;
        else {
            if (left != null)
                b1 = left.contains(e);

            if (right != null)
                b2 = right.contains(e);

            return b1 || b2;
        }
    }

    /**
     * Get the node with minimum data in this tree.
     * @return the reference to the node with minimum data.
     */
    BST<E> getMin(){
        if(left != null)
            return left.getMin();
        else
            return this;
    }

}