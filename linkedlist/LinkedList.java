package linkedlist;

public class LinkedList {
    private class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
    
    // Basic implmentation (messy)

    /**
     * Note: not bug free, but LL works and captures the basic idea. There are unaccounted edge cases, such as the NPE that happens when removing the item at index == size
     */
    private class LinkedListImpl<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size;
        
        public LinkedListImpl() {
            head = null;
            tail = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public void append(T value) {
            Node<T> node = new Node<>(value);
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            // duplicated
            size++;
        }

        public void prepend(T value) {
            Node<T> node = new Node<>(value);
            if (head == null) {
                head = tail = node;
            } else {
                node.next = head;
                head = node;
            }
            // Duplicated
            size++;
        }

        public void insert(int index, T value) {
            validateIndex(index);
            if (index == 0) {
                prepend(value);
            } else if (index == size) {
                append(value);
            } else {
                Node<T> node = new Node<>(value);
                Node<T> prev = traverse(index - 1);
                Node<T> next = prev.next;
                prev.next = node;
                node.next = next;
                // duplicated
                size++;
            }
        }

        public T remove(int index) {
            validateIndex(index);
            T data;
            if (index == 0) {
                data = head.data;
                Node<T> next = head.next;
                head.next = null;
                head = next;
            } else if (index == size - 1) {
                data = tail.data;
                // Duplicated
                Node<T> prev = traverse(index - 1);
                prev.next = null;
                tail = prev;
            } else {
                // Duplicated
                Node<T> prev = traverse(index - 1);
                Node<T> toRemove = prev.next;
                data = toRemove.data;
                prev.next = toRemove.next;
                toRemove.next = null;
            }
            size--;
            return data;
        }

        public T get(int index) {
            validateIndex(index);
            Node<T> node;
            if (index == 0) {
                node = head;
            } else if (index == size - 1) {
                node = tail;
            } else {
                node = traverse(index);
            }
            return node.data;
        }

        private Node<T> traverse(int index) {
            Node<T> node = head;
            for (int i=0; i<index; i++) {
                node = node.next;
            }
            return node;
        }

        private void validateIndex(int index) {
            if (index > size || index < 0) {
                throw new IllegalArgumentException("You cheeky bastard");
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node<T> curr = head;
            while (curr != null) {
                sb.append(curr.data).append("->");
                curr = curr.next;
            }
            sb.append("NULL");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        LinkedListImpl<Integer> nums = new LinkedList().new LinkedListImpl<>();

        System.out.println(nums);

        nums.prepend(0);
        System.out.println(nums.tail.data);
        System.out.println(nums.size());
        System.out.println(nums);

        try {
            nums.get(3);
        } catch (Exception e) {
            System.out.println(e);
        }

        nums.insert(1, 1);
        System.out.println(nums);
        nums.append(3);
        System.out.println(nums);
        nums.insert(1, 2);
        System.out.println(nums);

        nums.remove(0);
        System.out.println(nums);
        int num = nums.remove(1);
        System.out.println(nums);
        System.out.println(num);
    }
}