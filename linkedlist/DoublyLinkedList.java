package linkedlist;

public class DoublyLinkedList<T> {
	private static class Node<T> {
		T data;
		Node<T> prev;
		Node<T> next;

		Node(T data) {
			this.data = data;
		}	
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;

	private DoublyLinkedList() {
		head = tail = null;
		size = 0;
	}

	private int getSize() {
		return size;
	}

	private void insertAt(T item, int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("Invalid Index");
		}

		if (index == 0) {
			prepend(item);
			return;
		}

		if (index == size) {
			append(item);
			return;
		}

		Node<T> node = new Node<>(item);
		Node<T> curr = traverse(index);
		Node<T> prev = curr.prev;
		node.next = curr;
		curr.prev = node;
		node.prev = prev;
		prev.next = node;
		size++;		
	}

	private T remove(T item) {
		if (size == 0) {
			throw new IllegalStateException("Cannot remove when list is empty");
		}
		Node<T> node = search(item);
		if (node == null) {
			return null;
		}
		removeNode(node);
		return node.data; 
	}

	private T removeAt(int index) {
		if (size == 0) {
			throw new IllegalStateException("Cannot remove when list is empty");
		}

		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("Invalid Index");
		}
		Node<T> node = traverse(index);
		removeNode(node);
		return node.data;
	}

	// Internal method to remove node
	private void removeNode(Node<T> node) {
		Node<T> next = node.next;
		Node<T> prev = node.prev;

		// Reconnect List
		if (next != null) {
			next.prev = prev;
		} 

		if (prev != null) {
			prev.next = next;
		}

		// Update head and tail if they are affected by removal operation	
		if (node == head) {
			head = head.next;
		}

		if (node == tail) {
			tail = tail.prev;
		}

		// Formally remove node from list
		node.next = null;
		node.prev = null;

		// Decrement Size
		size--;
	}

	private void append(T item) {
		Node<T> node = new Node<>(item);
		if (tail == null) {
			tail = head = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
	}

	private void prepend(T item) {
		Node<T> node = new Node<>(item);
		if (head == null) {
			head = tail = node;
		} else {
			node.next = head;
			head.prev = node;
			head = node;
		}
		size++;
	}

	private T get(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("Invalid Index");
		}
		return traverse(index).data;
	}

	// Internal method to get node at index
	private Node<T> traverse(int index) {
		Node<T> node = head;
		for (int i=0; i<index && node != null; i++) {
			node = node.next;
		}
		return node;
	}

	// Internal method to search for node containing item
	private Node<T> search(T item) {
		Node<T> node = head;
		while (node != null) {
			if (node.data == item) {
				break;
			}
			node = node.next;
		}
		return node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<T> node = head;
		while (node != null) {
			sb.append(node.data).append("<->");
			node = node.next;
		}
		return sb.append("NULL").append(" ").append(size).toString();
	}

	public static void main(String[] args) {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
		System.out.println(list);
		try {
			list.remove(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			list.removeAt(10);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		list.append(1);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.insertAt(2, 1);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.insertAt(3, 1);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.prepend(22);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		System.out.println("-----");
		// System.out.println(list.get(2));
		Node<Integer> node = list.tail;
		while (node != null) {
			System.out.println(node.data);
			node = node.prev;
		}
		System.out.println("-----");
		list.remove(1);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.removeAt(0);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.removeAt(0);
		System.out.println(list.head.data);
		System.out.println(list.tail.data);
		System.out.println(list);
		list.removeAt(0);
		System.out.println(list.head);
		System.out.println(list.tail);
		System.out.println(list);
	}
}
