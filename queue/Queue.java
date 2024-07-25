import java.util.NoSuchElementException;

public class Queue {
    private class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // Personal Implementation
    private class MyQueue<T> {
        Node<T> head;
        Node<T> tail;
        int size;

        // Default constructor used

        int size() {
            return size;
        }

        void enqueue(T data) {
            Node<T> node = new Node<>(data);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = tail.next;
            }
            size++;
        }

        T dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Cannot dequeue an empty queue");
            }
            Node<T> next = head.next;
            T data = head.data;
            head.next = null;
            head = next;
            tail = (head != null) ? tail : null; 
            size--;
            return data;
        }

        T peek() {
            if (head == null) {
                throw new NoSuchElementException("Cannot peek an empty queue");
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
        MyQueue<Integer> myQueue = new Queue().new MyQueue<>();

        System.out.println(myQueue);

        System.out.println(myQueue.size());

        try {
            myQueue.dequeue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            myQueue.peek();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int[] nums = new int[] { 1, 2, 3, 4, 5, 6 };

        for (int num : nums) {
            myQueue.enqueue(num);
            System.out.println(myQueue);
        }

        for (int i=0; i<nums.length; i++) {
            System.out.println(myQueue.dequeue());
            System.out.println(myQueue);
        }
    }
}