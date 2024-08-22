package tree;

import java.util.List;
import java.util.ArrayList;

public class TreeTraversal {
    static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
        }
    }

    static <T> void preOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }
        
        target.add(root.data);
        preOrder(root.left, target);
        preOrder(root.right, target);
    }

    static <T> void inOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }

        inOrder(root.left, target);
        target.add(root.data);
        inOrder(root.right, target);
    }

    static <T> void postOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }
        
        postOrder(root.left, target);
        postOrder(root.right, target); 
        target.add(root.data);
    }

    public static void main(String[] args) {
        List<Integer> preOrder = new ArrayList<>();
        List<Integer> inOrder = new ArrayList<>();
        List<Integer> postOrder = new ArrayList<>();
        
        // Root
        TreeNode<Integer> root = new TreeNode<>(1);
        
        // Level 1
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);

        // Level 2
        root.left.left = new TreeNode<>(4);
        root.left.right = new TreeNode<>(5);
        root.right.left = new TreeNode<>(6);
        root.right.right = new TreeNode<>(7);

        // Level 3
        root.left.left.left = new TreeNode<>(8);
        root.left.right.right = new TreeNode<>(9);
        root.right.left.right = new TreeNode<>(10);
        root.right.right.left = new TreeNode<>(17);
        preOrder(root, preOrder);
        inOrder(root, inOrder);
        postOrder(root, postOrder);
        System.out.println(preOrder);
        System.out.println(inOrder);
        System.out.println(postOrder);
    }
}