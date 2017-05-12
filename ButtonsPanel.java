import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {

	GraphFrame _parent;
	
	public ButtonsPanel(GraphFrame parent) {
		_parent = parent;
		JButton btnAddEdge = new JButton("Add Edge");
		btnAddEdge.addActionListener(new AddEdgeButtonListener());
		JButton btnDelEdge = new JButton("Delete Edge");
		btnDelEdge.addActionListener(new DeleteEdgeButtonListener());
		JButton btnDelNode = new JButton("Delete Node");
		btnDelNode.addActionListener(new DeleteNodeButtonListener());
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearButtonListener());
		add(btnAddEdge);
		add(btnDelEdge);
		add(btnDelNode);
		add(btnClear);
	}

    private class AddEdgeButtonListener implements ActionListener
    {
		@Override
        public void actionPerformed(ActionEvent e) {
        	String input = JOptionPane.showInputDialog(null, "Enter new edge in the format: X,Y", "Input", JOptionPane.QUESTION_MESSAGE);
        	if (input != null && (input.length() != 3 || input.charAt(1) != ',')) {
        		JOptionPane.showMessageDialog(null, "Illegal format", "Error", JOptionPane.ERROR_MESSAGE);
        		return;
        	} 
        	else if (input == null){
        		return;
			}
        	input = input.toUpperCase();
        	char nodeName1 = input.charAt(0);
        	char nodeName2 = input.charAt(2);
        	if (!Character.isLetter(nodeName1) || !Character.isLetter(nodeName2)) {
        		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	Graph graph = _parent.getGraph();
        	try {
				if (!graph.hasNode(nodeName1) || !graph.hasNode(nodeName2)) {
					JOptionPane.showMessageDialog(null, "Node does not exist in the graph", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
	        	if (graph.hasEdge(nodeName1, nodeName2)) {
	        		JOptionPane.showMessageDialog(null, "Edge already exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
	        		return;
	        	}
	        	graph.addEdge(nodeName1, nodeName2);
	        	_parent.repaint();
			} catch (Graph.GraphException e1) {
				// Should not happen
			}
		}
    }
    
    private class DeleteEdgeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = JOptionPane.showInputDialog(null, "Enter edge to delete in the format: X,Y", "Input", JOptionPane.QUESTION_MESSAGE);
			if (input == null){
        		return;
			}
			if (input.length() != 3 || input.charAt(1) != ',') {
        		JOptionPane.showMessageDialog(null, "Illegal format", "Error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	input = input.toUpperCase();
        	char nodeName1 = input.charAt(0);
        	char nodeName2 = input.charAt(2);
        	if (!Character.isLetter(nodeName1) || !Character.isLetter(nodeName2)) {
        		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	Graph graph = _parent.getGraph();
        	try {
				if (!graph.hasNode(nodeName1) || !graph.hasNode(nodeName2)) {
					JOptionPane.showMessageDialog(null, "Node does not exist in the graph", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
	        	if (!graph.hasEdge(nodeName1, nodeName2)) {
	        		JOptionPane.showMessageDialog(null, "Edge does not exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
	        		return;
	        	}
	        	graph.deleteEdge(nodeName1, nodeName2);
	        	_parent.repaint();
			} catch (Graph.GraphException e1) {
				// Should not happen
			}
		}
    	
    }
    
    private class DeleteNodeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
            try {
            	String input = JOptionPane.showInputDialog(null, "Enter node name to delete (one character)", "Input", JOptionPane.QUESTION_MESSAGE);
            	if (input == null){
            		return;
            	}
            	if (input.length() != 1) {
            		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	char nodeName = input.toUpperCase().charAt(0);
            	Graph _graph = _parent.getGraph();
            	if (!Character.isLetter(nodeName)) {
            		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	if (!_graph.hasNode(nodeName)) {
            		JOptionPane.showMessageDialog(null, "Node does not exists in the graph", "Error", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
				_graph.deleteNode(nodeName);
			} catch (Graph.GraphException e1) {
			}
            _parent.repaint();
		}
    	
    }

    private class ClearButtonListener implements ActionListener
    {
		@Override
        public void actionPerformed(ActionEvent e) {
			_parent.clearGraph();
		}
    }
}
