import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	// Hejtest
	
	private ArrayList<LinkedList<Vertex>> graph;
	private Comparator<Path> path_Comp = new PathComparator();
	private PriorityQueue<Path> path_pq = new PriorityQueue<Path>(path_Comp);
	private ArrayList<String> verticesNow = new ArrayList<String>();
	private int totaldist = 0;

	public Graph() {
		graph = new ArrayList<LinkedList<Vertex>>();
	}

	public void addVertex(String label) {
		Vertex adding = new Vertex(label, 0);
		LinkedList<Vertex> newList = new LinkedList<Vertex>();
		newList.add(adding);
		graph.add(newList);
	}

	public void addEdge(String node1, String node2, int dist) {
		for (LinkedList<Vertex> l : graph) {
			if (l.getFirst().label.equals(node1)) {
				l.add(new Vertex(node2, dist));
			}
		}
	}



	public static class PathComparator implements Comparator<Path> {

		public int compare(Path path1, Path path2) {
			if (path1.totalDist < path2.totalDist) {
				return 1;
			} else if (path1.totalDist > path2.totalDist) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public Path shortestPath(String start, String dest) {
		System.out.println(start + " " + dest);
		Path minPath = null;
		String send = "";
		System.out.println(graph.toString());
		for (LinkedList<Vertex> l : graph) {
			if (l.getFirst().label.equals(start)) {

				LinkedList<Vertex> temp = new LinkedList<Vertex>();
				temp = l;
				temp.removeFirst();
				System.out.println(graph.toString());
				while (true) {
					//System.out.println(start + " " + dest);
					//System.out.println(l.toString());

					Vertex verTemp = temp.pollFirst();
					if (verTemp == null)
						break;
					int newdist = totaldist + verTemp.distance;
					ArrayList<String> newvertex = verticesNow;
					newvertex.add(verTemp.label);
					Path newPath = new Path(newdist, newvertex);
					path_pq.add(newPath);
				}
				minPath = path_pq.minimum();
				//send = minPath.vertices.get(minPath.vertices.size()-1);
				send = minPath.vertices.get(0);
				totaldist = totaldist + minPath.totalDist;
				verticesNow.add(start);
				break;
			}

		}
		System.out.println("hit");
		if (!start.equals(dest)) {
			System.out.println(send);
			shortestPath(send,dest);
		}
		return minPath;
	}

	public static class Path {
		public int totalDist;
		public List<String> vertices;

		public Path(int totalDist, List<String> vertices) {
			this.totalDist = totalDist;
			this.vertices = vertices;
		}

		@Override
		public String toString() {
			return "totalDist: " + totalDist + ", vertices: " + vertices.toString();
		}
	}
}
