package temperature;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Canvas extends JFrame {
	
	
	public static Widget drawArea;
	private static JFrame frame;
	private static JLabel label;
	
	private static final long serialVersionUID = 1L;
	
	public Canvas(){
		drawWidget();
		Canvas.setDrawArea(new Widget());
	}
	
	private void drawWidget(){
		String path = "img/widget.png";
        File file = new File(path);
        BufferedImage image = null;
        
		try { image = ImageIO.read(file);} 
		catch (IOException e) { e.printStackTrace(); }
		
        label = new JLabel(new ImageIcon(image));
 
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
