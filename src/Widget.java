

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Widget extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static String inside = "";
	private static String outside = "";
	
	public Widget(){
		this.setSize(150, 100);
		this.setLocation(20, 50);
		this.setOpaque(false);		
		
	}
	
	public static void setInside(String in){
		inside = in;
	}
	
	public static void setOutside(String out){
		outside = out;
	}
	
	private void drawStatus(Graphics2D g2){
		g2.setColor(Color.white);
		if(!Main.conn){
			g2.drawString("SSH disconnect", 30, 91);
		}else{
			g2.drawString("SSH connect", 30, 91);
		}
		
		if(Main.conn_in){
			g2.setColor(new Color(205, 233, 205));
		}else{
			g2.setColor(new Color(233, 205, 213));
		}		
		g2.fillOval(75, 15, 10, 10);
		
		
		if(Main.conn_out){
			g2.setColor(new Color(205, 233, 205));
		}else{
			g2.setColor(new Color(233, 205, 213));
		}
		g2.fillOval(75, 50, 10, 10);
		
	}


	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		drawStatus(g2);		
		
		g2.setColor(Color.black);
		g2.drawString(inside, 110, 26);
		g2.drawString(outside, 110, 61);
	}
}
