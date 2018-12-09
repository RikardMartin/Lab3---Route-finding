
public class test {

	public static void main(String[] args) {
		Graph g = new Graph();
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		g.addVertex("F");
		g.addEdge("A", "B", 2);
		g.addEdge("B", "E", 3);
		g.addEdge("A", "D", 7);
		g.addEdge("D", "E", 1);
		g.addEdge("B", "C", 1);
		System.out.println(g.shortestPath("A", "C"));  // totalDist: 3, vertices: [A, B, C]
		System.out.println(g.shortestPath("D", "A"));  // totalDist: 6, vertices: [D, E, B, A]
		//System.out.println(g.shortestPath("E", "F"));  // null

	}

}
