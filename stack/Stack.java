package stack;

import java.util.NoSuchElementException;

public class Stack {
    private class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // Personal Implementation
    private class MyStack<T> {
        // top
        Node<T> head;
        int size;

        // Default constructor

        int size() {
            return size;
        }

        void push(T data) {
            Node<T> node = new Node<>(data);
            if (head == null) {
                head = node;
            } else {
                node.next = head;
                head = node;
            }
            size++;
        }

        T pop() {
            if (head == null) {
                throw new NoSuchElementException("Cannot pop an empty stack");
            }
            Node<T> next = head.next;
            T data = head.data;
            head.next = null;
            head = next;
            size--;
            return data;
        }

        T peek() {
            if (head == null) {
                throw new NoSuchElementException("Cannot peek an empty stack");
            }
            return head.data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node<T> curr = head;
            while (curr != null) {
                sb.append(curr.data).append("->");
                curr = curr.next;
            }
            return sb.append("NULL").toString();
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new Stack().new MyStack<>();

        System.out.println(myStack);
        System.out.println(myStack.size());

        try {
            myStack.pop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myStack.peek();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int[] nums = new int[] { 1, 2, 3, 4, 5, 6 };

        for (int num : nums) {
            myStack.push(num);
            System.out.println(myStack);
        }

        for (int i=0; i<nums.length; i++) {
            System.out.println(myStack.pop());
            System.out.println(myStack);
        }
    }
}