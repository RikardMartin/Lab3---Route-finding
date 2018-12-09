/**
 * Interface for the comparators used in PriorityQueue.
 * 
 * @author Alexander Wölfinger
 * @author Richard Martin
 *
 * @param <T>
 */
public interface Comparator<T> {
	int compare(T a, T b);
}