import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	private static final int NODE_RADIUS = 24;
	private static final int FONT_SIZE = 20;
	private static final Font NODE_FONT = new Font("TimesRoman", Font.BOLD, FONT_SIZE);
	
	private Graph _graph;
	private int[][] _nodePositions;   // 2D array from nodeIds to vector of x,y of the node

	public GraphPanel() {
		initGraph();
        addMouseListener(new Listener());
	}

	private void initGraph() {
		_nodePositions = new int[Graph.NUM_POSSIBLE_NODES][2];

		char nodes[] = {'B', 'D'};
		char edges[][] = {{'B', 'D'}};
		try {
			_graph = new Graph(nodes, edges);
			_nodePositions[Graph.charToNodeId('B')][0] = 20;
			_nodePositions[Graph.charToNodeId('B')][1] = 30;
			_nodePositions[Graph.charToNodeId('D')][0] = 120;
			_nodePositions[Graph.charToNodeId('D')][1] = 80;
		} catch (Graph.GraphException e) {
			_graph = new Graph();
		}
		
//		_graph = new Graph();
//		_points = new int[Graph.NUM_POSSIBLE_NODES][2];
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintEdges(g);
        paintNodes(g);
	}

	private void paintEdges(Graphics g) {
		char[] nodeNames = _graph.getNodes();
	
    	try {
	        for (int pos1=0; pos1<nodeNames.length; ++pos1) {
	        	char nodeName1 = nodeNames[pos1];
	            for (int pos2=pos1; pos2<nodeNames.length; ++pos2) {
		        	char nodeName2 = nodeNames[pos2];
					if (_graph.hasEdge(nodeNames[pos1], nodeNames[pos2])) {
						paintEdge(g, _nodePositions[Graph.charToNodeId(nodeName1)], _nodePositions[Graph.charToNodeId(nodeName2)]);
					}
	            }
	        }
		} catch (Graph.GraphException e) {
			// Should not happen
		}
	}

	private void paintEdge(Graphics g, int[] position1, int[] position2) {
        Graphics2D g2 = (Graphics2D)g;
    	g.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(position1[0], position1[1], position2[0], position2[1]);
	}
	
	private void paintNodes(Graphics g) {
        for (char nodeName : _graph.getNodes()) {
        	try {
				if (_graph.hasNode(nodeName)) {
					paintNode(g, nodeName, _nodePositions[Graph.charToNodeId(nodeName)]);
				}
			} catch (Graph.GraphException e) {
				// Should not happen
			}
        }
	}
	
	private void paintNode(Graphics g, char nodeName, int[] position) {
    	g.setColor(Color.RED);
    	g.fillOval(position[0]-NODE_RADIUS, position[1]-NODE_RADIUS, NODE_RADIUS*2, NODE_RADIUS*2);
    	g.setColor(Color.BLACK);
    	g.setFont(NODE_FONT);
    	g.drawString(""+nodeName, (int)(position[0]-FONT_SIZE/3.5), (int)(position[1]+FONT_SIZE/2.5));
	}
	
	public void clearGraph() {
		_graph = new Graph();
		repaint();
	}

    private class Listener implements MouseListener
    {
		@Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            try {
            	String newName = JOptionPane.showInputDialog(null, "Enter new node name (one character)", "Input", JOptionPane.QUESTION_MESSAGE);
            	if (newName.length() != 1) {
            		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	char nodeName = newName.toUpperCase().charAt(0);
            	if (!Character.isLetter(nodeName)) {
            		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if (_graph.hasNode(nodeName)) {
            		JOptionPane.showMessageDialog(null, "Node already exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
				_graph.addNode(nodeName);
				int nodeId = Graph.charToNodeId(nodeName);
				_nodePositions[nodeId][0] = x;
				_nodePositions[nodeId][1] = y;
			} catch (Graph.GraphException e1) {
			}
            repaint();
        }

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
    }

	public Graph getGraph() {
		return _graph;
	}
	
	
//	@Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        int w = getWidth();
//        int h = getHeight();
//        int diameter = (int) (0.8 * Math.min(h,w));
//        int radius = diameter / 2;
//        
//        int w_center = w/2; 
//        int h_center = h/2; 
//        
//        int numNodes = _graph.;
//        float anglePart = 360 / (float) numNodes;
//        for (int i=0; i<numNodes; ++i) {
//        	float angle = i * anglePart;
//        	int delta_x = (int) (radius * Math.cos(Math.toRadians(angle)));
//        	int delta_y = (int) (radius * Math.sin(Math.toRadians(angle)));
//        	
//        	// delta_x, delta_y are related to normal Cartesian axes.
//        	// We want to add them relative to the window center.
//        	int x = w_center + delta_x;
//        	int y = h_center - delta_y;   // in Java, positive y axis is down, not up   
//        	
//        	int node_r = 20;
//        	g.setColor(Color.RED);
//        	g.fillOval(x-node_r, y-node_r, node_r*2, node_r*2);
//        }
//    }
}
