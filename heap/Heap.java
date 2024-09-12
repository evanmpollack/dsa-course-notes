package heap;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public Heap() {
        this(new ArrayList<>(), null);
    }

    public Heap(ArrayList<T> heap) {
        this(heap, null);
    }

    public Heap(Comparator<T> comparator) {
        this(new ArrayList<>(), comparator);
    }

    public Heap(ArrayList<T> heap, Comparator<T> comparator) {
        this.heap = heapify(heap, comparator);
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public void push(T data) {
        heap.add(data);
        siftUp(heap, comparator);
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int lastIndex = heap.size() - 1;
        swap(heap, 0, lastIndex);
        T data = heap.remove(lastIndex);
        siftDown(heap, comparator, 0);
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    private static <T extends Comparable<T>> ArrayList<T> heapify(ArrayList<T> heap, Comparator<T> comparator) {
        int lastInternalIndex = (heap.size() / 2) - 1;
        for (int i = lastInternalIndex; i >= 0; i--) {
            siftDown(heap, comparator, i);
        }
        return heap;
    }

    private static <T extends Comparable<T>> void siftDown(ArrayList<T> heap, Comparator<T> comparator, int index) {
        while (leftChildIndex(index) < heap.size()) {
            int nextChild = leftChildIndex(index);
            if (rightChildIndex(index) < heap.size() && compare(comparator, heap.get(nextChild), heap.get(rightChildIndex(index))) > 0) {
                nextChild = rightChildIndex(index);
            }

            if (compare(comparator, heap.get(nextChild), heap.get(index)) < 0) {
                swap(heap, nextChild, index);
                index = nextChild;
                continue;
            }
            
            // Prevent infinte loop if there are no more swaps to be made -- totally forgot, did not pay attention to tracing
            break;
        }
    }

    private static <T extends Comparable<T>> void siftUp(ArrayList<T> heap, Comparator<T> comparator) {
        int index = heap.size() - 1;
        while (parentIndex(index) >= 0 && compare(comparator, heap.get(index), heap.get(parentIndex(index))) < 0) {
            swap(heap, index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    private static <T> void swap(ArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static <T extends Comparable<T>> int compare(Comparator<T> comparator, T a, T b) {
        return comparator == null ? a.compareTo(b) : comparator.compare(a, b);
    }

    private static int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private static int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private static int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    public static void main(String[] args) {
        Heap<Integer> minHeap = new Heap<>(Comparator.<Integer>naturalOrder());
        minHeap.push(1);
        System.out.println(minHeap);
        minHeap.push(50);
        System.out.println(minHeap);
        minHeap.push(-12);
        System.out.println(minHeap);
        minHeap.push(2);
        System.out.println(minHeap);
        minHeap.push(-1001);
        System.out.println(minHeap);

        System.out.println(minHeap.pop());
        System.out.println(minHeap.pop());
        System.out.println(minHeap.pop());
        System.out.println(minHeap.pop());
        System.out.println(minHeap.pop());
    }
}