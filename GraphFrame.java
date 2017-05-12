import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphFrame extends JFrame {

	private GraphPanel _graphPanel;
	
	public GraphFrame() {
		super("Graph Application");
		initFrame();
	}
	
	private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setResizable(false);
		setLayout(new BorderLayout());        
        _graphPanel = new GraphPanel();
        add(_graphPanel, BorderLayout.CENTER);
        add(new ButtonsPanel(this), BorderLayout.SOUTH);
    	JLabel lblInstr = new JLabel("Press on the screen to add a node");
    	JPanel top = new JPanel();
    	top.setLayout(new FlowLayout());
    	top.add(lblInstr);
    	add(top, BorderLayout.NORTH);
	}
	
	public void clearGraph() {
		_graphPanel.clearGraph();
	}

	public Graph getGraph() {
		return _graphPanel.getGraph();
	}
	
	public static boolean isValidEdgeName(String input)
	{
    	if (input == null) {
    		return false;
    	}
		
		if (input.length() != 3 || input.charAt(1) != ',') {
    		JOptionPane.showMessageDialog(null, "Illegal format", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	} 
		
    	char nodeName1 = input.charAt(0);
    	char nodeName2 = input.charAt(2);
    	input = input.toUpperCase();
    	
    	if (!Character.isLetter(nodeName1) || !Character.isLetter(nodeName2)) {
    		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	return true;
	}

	public static boolean isValidNodeName(String input){
		if (input == null){
    		return false;
    	}
		if (input.length() != 1) {
    		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	char nodeName = input.toUpperCase().charAt(0);
    	if (!Character.isLetter(nodeName)) {
    		JOptionPane.showMessageDialog(null, "Node name must be one alphabetic letter", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	return true;
	}
}
