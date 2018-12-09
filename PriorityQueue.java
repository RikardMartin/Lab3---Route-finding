import java.util.*;

/**
 * A class that creates and store a priority queue if bids with the biggest or
 * smallest bid at the top depending on what comparator that it uses. Explantion
 * of class invariant follows at the method Invariant().
 * 
 * Complexity: O(log(n))
 * 
 * @author Alexander Wölfinger
 * @author Richard Martin
 *
 * @param <E> The object that in this case is the different bids.
 */
public class PriorityQueue<E> {

	/**
	 * hmap: HashMap containing bid object and index integer that is the same as the
	 * index: from heap. heap Arraylist containing bid objects sorted with binary
	 * heap. comparator: A comparator that compares the bid objects.
	 */
	private HashMap<E, Integer> hmap = new HashMap<E, Integer>();
	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;

	/**
	 * Declares the specific comparator.
	 * 
	 * @param comparator The different comparators for sell or buy.
	 */
	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Finds the object oldBid in the priority queue and replaces it with the object
	 * newBid. Then controls if the new bid is bigger or smaller then the old and
	 * calls for either siftUp or siftDown to keep the priority queue sorted.
	 * 
	 * Complexity: O(log(n))
	 * 
	 * @param oldBid Object to search for.
	 * @param newBid Object to replace with.
	 */
	public void update(E oldBid, E newBid) {
		assert invariant() : showHeap();

		/*
		 * Old code for finding object with linear search.
		 * 
		 * for (int i = 0; i < size(); i++) { if (oldBid.equals(heap.get(i))) {
		 * heap.set(i, newBid); if (comparator.compare(oldBid, newBid) < 0) {
		 * siftDown(i); } else { siftUp(i); } break; } }
		 */

		int index = hmap.get(oldBid);
		hmap.remove(oldBid);
		hmap.put(newBid, index);
		heap.set(index, newBid);
		if (comparator.compare(oldBid, newBid) < 0) {
			siftDown(index);
		} else {
			siftUp(index);
		}

		assert invariant() : showHeap();
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @return an integer with the sizes of the priority queue.
	 */
	public int size() {
		return heap.size();
	}

	/**
	 * Adds a new object to the priority queue and sends it to siftUp to it stays
	 * sorted.
	 * 
	 * Complexity: O(log(n))
	 * 
	 * @param newBid The object to add into the priority queue.
	 */
	public void add(E newBid) {
		assert invariant() : showHeap();

		heap.add(newBid);
		hmap.put(newBid, size() - 1);
		siftUp(size() - 1);

		assert invariant() : showHeap();
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @return The minimum object of the priority queue.
	 */
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	/**
	 * A method that removes the object on the top of the priority queue (minimum)
	 * and replaces it with the last object and calls siftDown to keep the priority
	 * queue sorted.
	 * 
	 * Complexity: O(log(n))
	 */
	public void deleteMinimum() {
		assert invariant() : showHeap();

		if (size() == 0)
			throw new NoSuchElementException();

		hmap.remove(heap.get(0));
		hmap.put(heap.get(heap.size() - 1), 0);

		heap.set(0, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);

		if (heap.size() > 0)
			siftDown(0);

		assert invariant() : showHeap();
	}

	/**
	 * A method that compares if the object "above" it, (the parent object) is
	 * larger or smaller and replaces if needed to and does this recursively.
	 * 
	 * Complexity: O(log(n))
	 * 
	 * @param index The index of the "child" object to compare.
	 */
	private void siftUp(int index) {
		if (size() > 1) {
			int parentIndex = parent(index);
			E parentValue = heap.get(parentIndex);
			E childValue = heap.get(index);
			if (comparator.compare(parentValue, childValue) > 0) {
				heap.set(parentIndex, childValue);
				heap.set(index, parentValue);
				hmap.put(childValue, parentIndex);
				hmap.put(parentValue, index);
				siftUp(parentIndex);
			}
		}
	}

	/**
	 * A method that compares a "parent" object with the "children" object and
	 * changes them if needed to and keeps going in a loop until no changes need to
	 * be done.
	 * 
	 * Complexity: O(log(n))
	 * 
	 * @param index The index of the "parent" object.
	 */
	private void siftDown(int index) {
		E value = heap.get(index);
		while (leftChild(index) < heap.size()) {
			int left = leftChild(index);
			int right = rightChild(index);
			int child = left;
			E childValue = heap.get(left);

			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}
			if (comparator.compare(value, childValue) > 0) {
				heap.set(index, childValue);
				hmap.put(childValue, index);
				index = child;
			} else
				break;
		}
		heap.set(index, value);
		hmap.put(value, index);
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @param index "parent" index.
	 * @return an integer of the left "childs" index
	 */
	private final int leftChild(int index) {
		return 2 * index + 1;
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @param index "parent" index.
	 * @return an integer of the right "childs" index
	 */
	private final int rightChild(int index) {
		return 2 * index + 2;
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @param index "child" index.
	 * @return an integer of the right "parent" index
	 */
	private final int parent(int index) {
		return (index - 1) / 2;
	}

	/**
	 * Complexity: O(1)
	 * 
	 * @param index The index of the object we want to get the string of.
	 * @return a string that represents the object.
	 */
	public String getString(int index) {
		return heap.get(index).toString();
	}

	/**
	 * A method that controls if the invariant is true or false in the priority
	 * queue, controls both heap and hmap.
	 * 
	 * Class invariants: heap property (every child is greater then their parent)
	 * and completeness (the tree is always balanced).
	 * 
	 * Complexity: O(n)
	 * 
	 * @return true if the invariant is ok otherwise false.
	 */
	private boolean invariant() {
		for (int i = 0; i < size(); i++) {
			if (hmap.get(heap.get(i)) != i)
				return false;
			if (leftChild(i) < size())
				if (comparator.compare(heap.get(leftChild(i)), heap.get(parent(i))) < 0)
					return false;
			if (rightChild(i) < size())
				if (comparator.compare(heap.get(rightChild(i)), heap.get(parent(i))) < 0)
					return false;
		}
		return true;
	}

	/**
	 * Complexity: O(nlog(n))
	 * 
	 * @return A string containing the complete heap.
	 */
	private String showHeap() {
		String str = "\n";
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < Math.pow(2, i); j++) {
				if (i * j > size())
					break;
				str = str + getString(i) + " ";
			}
			str = str + "\n";
		}
		return str;
	}
}