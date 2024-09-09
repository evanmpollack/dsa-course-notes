package tree;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class TreeTraversal {
    static class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        TreeNode(T data) {
            this.data = data;
        }
    }

    // Depth
    static <T> void preOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }
        
        target.add(root.data);
        preOrder(root.left, target);
        preOrder(root.right, target);
    }

    // Depth
    static <T> void inOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }

        inOrder(root.left, target);
        target.add(root.data);
        inOrder(root.right, target);
    }

    // Depth
    static <T> void postOrder(TreeNode<T> root, List<T> target) {
        if (root == null) {
            return;
        }
        
        postOrder(root.left, target);
        postOrder(root.right, target); 
        target.add(root.data);
    }

    // Breadth
    static <T> void levelOrder(TreeNode<T> root, List<T> target) {
        Queue<TreeNode<T>> queue = new LinkedList<>();

        if (root == null) {
            return;
        } else {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            TreeNode<T> curr = queue.remove();
            
            if (curr.left != null) {
                queue.add(curr.left);
            }

            if (curr.right != null) {
                queue.add(curr.right);
            }

            target.add(curr.data);
        }
    }

    static <T> boolean bfs(TreeNode<T> root, T needle) {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        boolean found = false;

        if (root == null) {
            return found;
        } else {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            TreeNode<T> curr = queue.remove();
            
            // .equals because we are working with boxed types in generics
            if (curr.data.equals(needle)) {
                found = true;
                break;
            }

            if (curr.left != null) {
                queue.add(curr.left);
            }

            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

        return found;
    }

    public static void main(String[] args) {
        List<Integer> preOrder = new ArrayList<>();
        List<Integer> inOrder = new ArrayList<>();
        List<Integer> postOrder = new ArrayList<>();
        List<Integer> levelOrder = new ArrayList<>();
        
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
        levelOrder(root, levelOrder);
        System.out.println(preOrder);
        System.out.println(inOrder);
        System.out.println(postOrder);
        System.out.println(levelOrder);
        System.out.println(bfs(root, 16));

        BST<Integer> bst = new BST<>();
        bst.insert(50);
        bst.insert(20);
        bst.insert(30);
        bst.insert(60);
        bst.insert(70);
        bst.insert(80);
        bst.insert(40);
        System.out.println(bst);
        System.out.println(bst.find(10));
        System.out.println(bst.find(20));
        bst.delete(50);
        System.out.println(bst.find(50));
    }
}