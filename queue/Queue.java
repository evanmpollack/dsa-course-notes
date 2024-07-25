import java.util.NoSuchElementException;

public class Queue {
    private class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return String.valueOf(data);
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

        T deque() {
            if (head == null) {
                throw new NoSuchElementException("Cannot deque an empty queue");
            }
            Node<T> next = head.next;
            T data = head.data;
            head.next = null;
            head = next;
            // Not technically needed.
            // Formal reset of tail pointer, otherwise will be reset next enqueue if head is null
            tail = (head != null) ? tail : null;
            size--;
            return data;
        }

        // My implementation was slightly different than in class
        T inClassDeque() {
            if (head == null) {
                throw new NoSuchElementException("Cannot deque from empty queue");
            }
            // By storing a reference to the current head instead of next, we can avoid creating a data variable
            Node<T> head = this.head;
            // setting head.next to null before setting this.head to next modifies the original reference and causes NPE on next deque
            this.head = this.head.next;
            head.next = null;
            tail = (this.head != null) ? tail : null;
            size--;
            return head.data;
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
            myQueue.deque();
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
            System.out.println(myQueue.inClassDeque());
            System.out.println(myQueue);
        }

        System.out.println(myQueue.tail);
    }
}