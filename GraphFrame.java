import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GraphFrame extends JFrame {

	private GraphPanel _graphPanel;
	
	public GraphFrame() {
		super("Graph Application");
		initFrame();
	}
	
	private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
		setLayout(new BorderLayout());        
        _graphPanel = new GraphPanel();
        add(_graphPanel, BorderLayout.CENTER);
        add(new ButtonsPanel(this), BorderLayout.SOUTH);
	}
	
	public void clearGraph() {
		_graphPanel.clearGraph();
	}

	public Graph getGraph() {
		return _graphPanel.getGraph();
	}
}
