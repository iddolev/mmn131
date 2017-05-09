public class Maman13 {

	public Maman13() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		char nodes[] = {'A', 'B', 'C', 'D', 'E'};
		char edges[][] = {{'A', 'C'}, {'A', 'D'}, {'C', 'D'}, {'C', 'E'}};
		
		try {
			Graph graph1 = new Graph(nodes, edges);
			System.out.println("Graph1: "+graph1);
		} catch (Graph.GraphException e) {
			e.printStackTrace();
		}

		Graph graph2 = new Graph();
		System.out.println("Graph2: "+graph2);
		
		try {
			graph2.addNode('X');
			graph2.addNode('Y');
			graph2.addNode('C');
			graph2.addEdge('C', 'Y');
			graph2.addEdge('Y', 'X');
			System.out.println("Graph2: "+graph2);
		} catch (Graph.GraphException e) {
			e.printStackTrace();
		}
	}

}
