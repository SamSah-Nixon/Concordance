/*
 * Created: 5/2/2023
 * Last Modified: 5/31/2023
 * Sam Sah-Nixon
 * AVL tree implementation
 * Binary search tree that balances itself after each insertion or deletion
 */
import java.util.ArrayDeque;

public class AVLTree<E extends Comparable<? super E>>{

    private TreeNode<E> root;
    private int size;
    private boolean printParent;

    public AVLTree(){
        root = null;
        size = 0;
        printParent = true;
    }

    public AVLTree(boolean printParent){
        root = null;
        size = 0;
        this.printParent = printParent;
    }

    /**
     * Inserts item to tree
     * @param item item to insert
     * @return true if item is inserted, false otherwise
     */
    public boolean insert(E item){
        size++;
        if(isEmpty()){
            root = new TreeNode<E>(item);
            return true;
        }
        TreeNode<E> current = root;
        while(current != null){
            if(item.compareTo(current.data) < 0){
                if(current.leftChild == null){
                    current.leftChild = new TreeNode<E>(item, current);
                    break;
                }
                else
                    current = current.leftChild;
            }
            else if(item.compareTo(current.data) > 0){
                if(current.rightChild == null){
                    current.rightChild = new TreeNode<E>(item, current);
                    break;
                }
                else
                    current = current.rightChild;
            }
            else{
                size--;
                return false;
            }
        }
        balance(current);
        return true;
    }

    /**
     * Deletes item from tree
     * @param item item to delete
     */
    public void delete(E item){
        TreeNode<E> current = root;
        while(!item.equals(current.data)){
            if(item.compareTo(current.data) < 0)
                current = current.leftChild;
            else if(item.compareTo(current.data) > 0)
                current = current.rightChild;
            else
                return;
            if(current == null)
                return;
        }
        size--;
        //Leaf - No Children
        if(current.leftChild == null && current.rightChild == null){
            if(current.parent.leftChild == current)
                current.parent.leftChild = null;
            else
                current.parent.rightChild = null;
            current = current.parent;
        }
        //Only Right Child - One Child
        else if(current.leftChild == null){
            if(current.parent.leftChild == current)
                current.parent.leftChild = current.rightChild;
            else
                current.parent.rightChild = current.rightChild;
            current.rightChild.parent = current.parent;
            //Sever ties
            current = current.parent;
        }
        //Only Left Child - One Child
        else if(current.rightChild == null){
            if(current.parent.leftChild == current)
                current.parent.leftChild = current.leftChild;
            else
                current.parent.rightChild = current.leftChild;
            current.leftChild.parent = current.parent;
            //Sever ties
            current = current.parent;
        }
        //Two Children
        else{
            TreeNode<E> smallest = current.rightChild;
            //Find smallest on right side
            while(smallest.leftChild != null)
                smallest = smallest.leftChild;
            current.data = smallest.data;
            //Sever ties
            if(smallest.parent.leftChild == smallest)
                smallest.parent.leftChild = smallest.rightChild;
            else
                smallest.parent.rightChild = smallest.rightChild;
            //If it has a right child, set its parent to current's parent
            if(smallest.rightChild != null)
                smallest.rightChild.parent = smallest.parent;
            current = smallest.parent;
        }

        balance(current);
    }

    /**
     * Updates the balance factor of a node
     * @param node node to update
     * @return balance factor
     */
    public int getBF(TreeNode<E> node){
        int bf;
        if(node.leftChild == null && node.rightChild == null)
            bf = 0;
        else if(node.leftChild == null)
            bf = -node.rightChild.height;
        else if(node.rightChild == null)
            bf = node.leftChild.height;
        else
            bf = node.leftChild.height - node.rightChild.height;
        return bf;
    }

    /**
     * Updates the height of a node
     * @param node node to update
     */
    public void updateHeight(TreeNode<E> node){
        if(node.leftChild == null && node.rightChild == null)
            node.height = 1;
        else if(node.leftChild == null)
            node.height = node.rightChild.height + 1;
        else if(node.rightChild == null)
            node.height = node.leftChild.height + 1;
        else
            node.height = Math.max(node.leftChild.height,node.rightChild.height) + 1;
    }

    /**
     * Checks to see if the tree needs to be balanced and balances it if it does
     * @param node node to check
     */
    public void balance(TreeNode<E> node){
        if(node == null)
            return;

        updateHeight(node);

        if(getBF(node) > 1){
            if(getBF(node.leftChild) > 0){
                //Right Rotation
                rightRotation(node);
            }
            else{
                //Left Right Rotation
                TreeNode<E> y = leftRotation(node.leftChild);
                rightRotation(y.parent);
            }
        }
        else if(getBF(node) < -1){
            if(getBF(node.rightChild) < 0){
                //Left Rotation
                leftRotation(node);
            }
            else{
                //Right Left Rotation
                TreeNode<E> y = rightRotation(node.rightChild);
                leftRotation(y.parent);
            }
        }
        balance(node.parent);
    }
    
    /**
     * Performs a left rotation
     * @param node node to rotate
     * @return new root of the subtree
     */
    public TreeNode<E> leftRotation(TreeNode<E> node){
        //Set variables for rotation
        TreeNode<E> y = node.rightChild;
        TreeNode<E> z = node;

        //Perform rotation
        z.rightChild = y.leftChild;
        y.leftChild = z;
        
        //Update parents
        y.parent = z.parent;
        z.parent = y;

        //Update parent of z's children
        if(z.rightChild != null)
            z.rightChild.parent = z;

        //If parent is not the root
        if(y.parent != null){
            if(y.parent.leftChild != null && y.parent.leftChild.equals(z))
                y.parent.leftChild = y;
            else
                y.parent.rightChild = y;
        }
        else
            root = y;

        //Update heights
        updateHeight(z);
        updateHeight(y);

        return y;
    }

    /**
     * Performs a right rotation
     * @param node node to rotate
     * @return new root of the subtree
     */
    public TreeNode<E> rightRotation(TreeNode<E> node){
        //See leftRotation for comments
        TreeNode<E> y = node.leftChild;
        TreeNode<E> z = node;

        z.leftChild = y.rightChild;
        y.rightChild = z;
        y.parent = z.parent;
        z.parent = y;

        if(z.leftChild != null)
            z.leftChild.parent = z;

        //If parent is not the root
        if(y.parent != null){
            if(y.parent.leftChild != null && y.parent.leftChild.equals(z))
                y.parent.leftChild = y;
            else
                y.parent.rightChild = y;
        }
        else
            root = y;

        updateHeight(z);
        updateHeight(y);

        return y;
    }

    public boolean set(E item, E item2){
        TreeNode<E> current = find(item);
        if(current == null)
            return false;
        current.data = item2;
        return true;

    }

    /**
     * Finds item in tree
     * @param item item to find
     * @return the item if it is found, null otherwise
     */
    public TreeNode<E> find(E item){
        TreeNode<E> current = root;
        while(current != null){
            if(item.compareTo(current.data) == 0)
                return current;
            if(item.compareTo(current.data) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
        }
        return null;
    }

    /**
     * Finds an item in the tree
     * @param item the item to find
     * @return the data of the item if it is found, null otherwise
     */
    public E findData(E item){
        return find(item).data;
    }

    /**
     * Checks if tree is empty
     * @return true if tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Returns the size of tree
     * @return the size of tree
     */
    public int size(){
        return size;
    }

    /**
     * Returns a string representation of tree
     * @return a string representation of tree
     */
    public String toString(){
        if(root == null)
            return "[]";
        ArrayDeque<TreeNode<E>> deque = new ArrayDeque<TreeNode<E>>();
        TreeNode<E> current;
        StringBuilder string = new StringBuilder("[");
        deque.addLast(root);
        while(!deque.isEmpty()){
            current = deque.removeFirst();
            if(!printParent)
                string.append("("+current.data+"),\n");
            else if(current.parent == null)
                string.append("(null, "+current.data+")");
            else
                string.append(", ("+current.parent.data+", "+current.data+")");
            if(current.leftChild != null)
                deque.addLast(current.leftChild);
            if(current.rightChild != null)
                deque.addLast(current.rightChild);
        }
        return string+"]";
    }


    private static class TreeNode<E>{
        private E data;
        private int height;
        private TreeNode<E> parent;
        private TreeNode<E> leftChild;
        private TreeNode<E> rightChild;

        public TreeNode(E data){
            this.data = data;
            this.parent = null;
            leftChild = null;
            rightChild = null;
            height = 1;
        }
        public TreeNode(E data, TreeNode<E> parent){
            this.data = data;
            this.parent = parent;
            leftChild = null;
            rightChild = null;
            height = 1;
        }
        
    }
}