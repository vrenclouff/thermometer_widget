

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Canvas extends JFrame {
	
	
	public static Widget drawArea;
	private JFrame frame;
	private JLabel label;
	
	private static final long serialVersionUID = 1L;
	
	public Canvas(){
		label = new JLabel(new ImageIcon(getClass().getResource("widget.png")));
		Canvas.setDrawArea(new Widget());
	}
		
	public void run(){
		frame = new JFrame();
		
		frame.setTitle("Temperature");
		frame.setLayout(new BorderLayout());
		
		frame.add(getDrawArea(), BorderLayout.CENTER);
		frame.getContentPane().add(label);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        	
	}

	public Widget getDrawArea() {
		return drawArea;
	}

	public static void setDrawArea(Widget drawArea) {
		Canvas.drawArea = drawArea;
	}
}
