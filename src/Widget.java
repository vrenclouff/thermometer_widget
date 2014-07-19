

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Widget extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static String inside = "";
	private static String outside = "";
	
	public Widget(){
		this.setSize(50, 80);
		this.setLocation(120, 50);
		this.setOpaque(false);		
		
	}
	
	public static void setInside(String in){
		inside = in;
	}
	
	public static void setOutside(String out){
		outside = out;
	}


	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawString(inside, 10, 26);
		g2.drawString(outside, 10, 61);
	}
}
