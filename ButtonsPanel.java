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
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearButtonListener());
		add(btnAddEdge);
		add(btnClear);
	}

    private class AddEdgeButtonListener implements ActionListener
    {
		@Override
        public void actionPerformed(ActionEvent e) {
        	String input = JOptionPane.showInputDialog(null, "Enter new edge in the format: X,Y", "Input", JOptionPane.QUESTION_MESSAGE);
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

    private class ClearButtonListener implements ActionListener
    {
		@Override
        public void actionPerformed(ActionEvent e) {
			_parent.clearGraph();
		}
    }
}
