package tree;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class BST<T extends Comparable<T>> {
    private static class BSTNode<T> {
        T data;
        BSTNode<T> left;
        BSTNode<T> right;

        public BSTNode(T data) {
            this.data = data;
        }
    }

    private BSTNode<T> root;
    private Comparator<T> comparator;

    public BST() {
        this(null, null);
    }

    public BST(Comparator<T> comparator) {
        this(null, comparator);
    }

    public BST(T root) {
        this(root, null);
    }

    public BST(T root, Comparator<T> comparator) {
        this.root = (root == null) ? null : new BSTNode<T>(root);
        this.comparator = comparator;
    }

    public void insert(T data) {
        // BSTNode<T> node = new BSTNode<T>(data);
        
        // Technically duplicated in recursiveInsert as base case
        // if (root == null) {
        //     root = node;
        //     return;
        // }

        // iterativeInsert(root, node);
        root = recursiveInsert(root, data);
    }

    /**
     * Even though this method uses recursion, it is still an iterative approach.
     * This is because I am looking ahead at the children and deciding my next move,
     * which is still moving through the tree in an interative fashion.
     * E.g. I am considering 3 trees/subtrees at once
     * 
     * "While I have children and can move there, move. 
     * If the new node should be one of those children, point curr at new node"
     */
    private void iterativeInsert(BSTNode<T> curr, BSTNode<T> node) {
        int comparison = compare(node.data, curr.data);
        boolean lteq = comparison < 0 || comparison == 0;
        boolean gt = comparison > 0;

        if (curr.left != null && lteq) {
            iterativeInsert(curr, node);
	        return;
        }

        if (curr.right != null && gt) {
            iterativeInsert(curr, node);
	        return;
        }

        if (lteq) {
            curr.left = node;
        } else {
            curr.right = node;
        }
    }

    /**
     * This method is a recursive approach, 
     * as I broke the problem into smaller parts 
     * where I only have to look at the current node.
     * 
     * "Is this slot available? Yes, return, no, move on"
     */
    private BSTNode<T> recursiveInsert(BSTNode<T> curr, T data) {
        if (curr == null) {
            return new BSTNode<>(data);
        }

        if (compare(data, curr.data) <= 0) {
            curr.left = recursiveInsert(curr.left, data);
            return curr;
        } else {
            curr.right = recursiveInsert(curr.right, data);
            return curr;
        }
    }

    public void delete(T data) {
        // root may be the one deleted, have to update instance
        root = recursiveDelete(root, data);
    }


    private BSTNode<T> recursiveDelete(BSTNode<T> curr, T data) {
        // target node could not be found
        if (curr == null) {
            return curr;
        }

        int comparison = compare(data, curr.data);

        // target node to left
        if (comparison < 0) {
            curr.left = recursiveDelete(curr.left, data);
        // target node to right
        } else if (comparison > 0) {
            curr.right = recursiveDelete(curr.right, data);
        // target node found
        } else {
            if (curr.left == null) {
                return curr.right;
            }

            if (curr.right == null) {
                return curr.left;
            }

            BSTNode<T> predecessor = getInorderPredecessor(curr);
            swapValues(curr, predecessor);
            curr.left = recursiveDelete(curr.left, data);
        }
        return curr;
    }

    private BSTNode<T> getInorderPredecessor(BSTNode<T> node) {
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void swapValues(BSTNode<T> a, BSTNode<T> b) {
        T temp = a.data;
        a.data = b.data;
        b.data = temp;
    }

    public boolean find(T data) {
        return recursiveFind(root, data);
    }

    private boolean recursiveFind(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }

        int comparison = compare(data, curr.data);

        if (comparison == 0) {
            return true;
        }

        if (comparison < 0) {
            return recursiveFind(curr.left, data);
        } else {
            return recursiveFind(curr.right, data);
        }
    }

    private int compare(T a, T b) {
        return (comparator == null) ? a.compareTo(b) : comparator.compare(a, b);
    }

    @Override
    public String toString() {
        List<T> out = new ArrayList<>();
        inOrderTraversal(out, root);
        return out.toString();
    }

    private void inOrderTraversal(List<T> out, BSTNode<T> curr) {
        if (curr == null) {
            return;
        }

        inOrderTraversal(out, curr.left);
        out.add(curr.data);
        inOrderTraversal(out, curr.right);
    }
}