
public class DistComparator implements Comparator<Vertex> {
	
	public int compare(Vertex node1, Vertex node2) {
		if (node1.distance < node2.distance) {
			return 1;
		} else if (node1.distance > node2.distance) {
			return -1;
		} else {
			return 0;
		}
	}

}
