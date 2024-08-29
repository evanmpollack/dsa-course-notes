package tree;

import java.util.Comparator;

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
        this.root = new BSTNode<T>(root);
        this.comparator = comparator;
    }

    public void insert(T data) {
        BSTNode<T> node = new BSTNode<T>(data);
        
        if (root == null) {
            root = node;
            return;
        }

        recursiveInsert(root, node);
    }

    private void recursiveInsert(BSTNode<T> curr, BSTNode<T> node) {
        int comparison = compare(node.data, curr.data);
        boolean lteq = comparison < 0 || comparison == 0;
        boolean gt = comparison > 0;

        if (curr.left != null && lteq) {
            recursiveInsert(curr, node);
        }

        if (curr.right != null && gt) {
            recursiveInsert(curr, node);
        }

        if (lteq) {
            curr.left = node;
        } else {
            curr.right = node;
        }
    }

    public void delete(T data) {
        if (root == null) {
            throw new IllegalStateException("Empty Tree");
        }
    }

    private void recursiveDelete(T data) {
        
    }

    public boolean find(T data) {
        if (root == null) {
            throw new IllegalStateException("Empty Tree");
        }
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
}