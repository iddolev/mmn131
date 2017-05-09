/* Class for representing a Character graph (assuming only uppercase characters A-Z) */
public class Graph {

	private static final int NUM_POSSIBLE_NODES = 'Z'-'A'+1;
	
	private boolean _nodes[] = new boolean[NUM_POSSIBLE_NODES];
	private boolean _edges[][] = new boolean[NUM_POSSIBLE_NODES][NUM_POSSIBLE_NODES];

	public static class GraphException extends Exception {
		public GraphException(String message) {
			super(message);
		}
	}
	
	public static class IllegalCharacterNode extends GraphException {
		public IllegalCharacterNode(char nodeId) {
			super("Illegal character id: "+nodeId);
		}
	}
	
	public static class NodeAlreadyExistsException extends GraphException {
		
		private char _nodeId;
		
		public NodeAlreadyExistsException(char nodeId) {
			super("Node "+nodeId+" already in the graph");
			_nodeId = nodeId;
		}
		
		public char getNodeId() {
			return _nodeId;
		}
	}
	
	public static class NodeDoesNotExistException extends GraphException {
		public NodeDoesNotExistException(char nodeId) {
			super("Node "+nodeId+" does not exist in the graph");
		}
	}

	public static class EdgeAdditionForNonExistingNode extends GraphException {
		public EdgeAdditionForNonExistingNode(char nodeId) {
			super("Cannot add edge on no-existing node "+nodeId);
		}
	}
	
	public static class EdgeAlreadyExistsException extends GraphException {
		public EdgeAlreadyExistsException(char nodeId1, char nodeId2) {
			super("Edge ("+nodeId1+","+nodeId2+") already exists in the graph");
		}
	}

	public static class EdgeDoesNotExistException extends GraphException {
		public EdgeDoesNotExistException(char nodeId1, char nodeId2) {
			super("Edge ("+nodeId1+","+nodeId2+") does not exist in the graph");
		}
	}
	
	public static class IllegalEdgeInput extends GraphException {
		public IllegalEdgeInput(char[] vec) {
			super("Edge vector should have exactly two characters");
		}
	}

	public Graph() {
		// All nodes and edges are automatically initialized to false, so no need to do anything
	}
	
	public Graph(char[] nodeIds, char[][] edges) throws IllegalCharacterNode, NodeAlreadyExistsException, 
														IllegalEdgeInput, EdgeAdditionForNonExistingNode, 
														EdgeAlreadyExistsException {
		for (char nodeId : nodeIds) {
			addNode(nodeId);
		}
		for (char[] pair : edges) {
			if (pair.length != 2) {
				throw new IllegalEdgeInput(pair);
			}
			addEdge(pair[0], pair[1]);
		}
	}
	
	private int charToPos(char nodeId) throws IllegalCharacterNode {
		if (nodeId >= 'A' && nodeId <= 'Z')
			return nodeId - 'A';
		throw new IllegalCharacterNode(nodeId);
	}

	public boolean hasNode(char nodeId) throws IllegalCharacterNode {
		int pos = charToPos(nodeId);
		return _nodes[pos];
	}

	public boolean hasEdge(char nodeId1, char nodeId2) throws IllegalCharacterNode {
		int pos1 = charToPos(nodeId1);
		int pos2 = charToPos(nodeId2);
		return _edges[pos1][pos2];
	}

	public void addNode(char nodeId) throws IllegalCharacterNode, NodeAlreadyExistsException {
		int pos = charToPos(nodeId);
		if (_nodes[pos]) {
			throw new NodeAlreadyExistsException(nodeId);
		}
		_nodes[pos] = true;
	}
	
	public void deleteNode(char nodeId) throws IllegalCharacterNode, NodeDoesNotExistException {
		int pos = charToPos(nodeId);
		if (!_nodes[pos]) {
			throw new NodeDoesNotExistException(nodeId);
		}
		_nodes[pos] = false;
		for (int p=0; p < NUM_POSSIBLE_NODES; ++p) {
			_edges[pos][p] = false;
			_edges[p][pos] = false;
		}
	}
	
	public void addEdge(char nodeId1, char nodeId2) throws IllegalCharacterNode, EdgeAdditionForNonExistingNode, EdgeAlreadyExistsException {
		int pos1 = charToPos(nodeId1);
		int pos2 = charToPos(nodeId2);
		if (!(_nodes[pos1])) {
			throw new EdgeAdditionForNonExistingNode(nodeId1);
		}
		if (!(_nodes[pos2])) {
			throw new EdgeAdditionForNonExistingNode(nodeId2);
		}
		if (_edges[pos1][pos2]) {
			throw new EdgeAlreadyExistsException(nodeId1, nodeId2);
		}
		_edges[pos1][pos2] = true;
		_edges[pos2][pos1] = true;
	}

	public void deleteEdge(char nodeId1, char nodeId2) throws IllegalCharacterNode, EdgeDoesNotExistException {
		int pos1 = charToPos(nodeId1);
		int pos2 = charToPos(nodeId2);
		if (!_edges[pos1][pos2]) {
			throw new EdgeDoesNotExistException(nodeId1, nodeId2);
		}
		_edges[pos1][pos2] = false;
		_edges[pos2][pos1] = false;
	}
	
	@Override
	public String toString() {
		String nodesStr = "";
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			if (_nodes[i]) {
				if (!nodesStr.isEmpty()) {
					nodesStr += ",";
				}
				nodesStr += (char)('A'+i);
			}
		}
		nodesStr = "{"+nodesStr+"}";
		
		String edgesStr = "";
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			for (int j=i; j<NUM_POSSIBLE_NODES; ++j) {
				if (_edges[i][j]) {
					if (!edgesStr.isEmpty()) {
						edgesStr += ",";
					}
					edgesStr += "(" + (char)('A'+i) + "," + (char)('A'+j) + ")";
				}
			}
		}
		edgesStr = "{"+edgesStr+"}";
		
		return "Graph<"+nodesStr+" "+edgesStr+">";
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Graph))
			return false;
		Graph graph = (Graph) other;
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			if (_nodes[i] != graph._nodes[i])
				return false;
		}
		for (int i=0; i<NUM_POSSIBLE_NODES; ++i) {
			for (int j=i; j<NUM_POSSIBLE_NODES; ++j) {
				if (_edges[i][j] != graph._edges[i][j])
					return false;
			}
		}
		return true;
	}

}
