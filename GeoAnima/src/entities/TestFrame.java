package entities;

import javax.swing.JFrame;

public class TestFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestPanel testPanel;
	public TestFrame(Canvas canvas, int delay, int runtime) {
		
		TestPanel testPanel = new TestPanel(canvas, delay, runtime);
		add(testPanel);
		setResizable(false);
        pack();
        
        setTitle("TEST");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
	}
	public TestPanel getTestPanel() {
		return testPanel;
	}

}
