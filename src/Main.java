

import java.util.Timer;
import java.util.TimerTask;


public class Main {
	
	static Time time;
	static ScpFrom ssh;
	Canvas cvs;
	
	private static String parseData(String data){
		String [] parts = data.split(" ");
		String temperatur = null;
				
		if(parts[11].split("\n")[0].trim().equals("NO")){
			temperatur = null;
		}else{
			temperatur = parts[20];
		}
		return temperatur;
	}
	
	private static String celsius(String data){
		String tmp = parseData(data);
		double temper = 0.0;
		
		if(tmp == null){ return "null";} // return null (error)

		String [] parts = tmp.split("=");
		temper = Double.parseDouble(parts[1]);
		temper /= 100;
		temper = (double)Math.round(temper)/10;
		tmp = String.valueOf(temper);

		return tmp;	// return temperature
	}
	
	private void run(){
		cvs = new Canvas();
		cvs.run();
	}
	
	public static void time(){
		ssh = new ScpFrom();
		String data = ssh.getData();
		String inside = celsius(data);
		String outside = "off";
		Widget.setInside(inside);
		Widget.setOutside(outside);
		Canvas.drawArea.repaint();
	}
	
	


	public static void main(String[] args) {		
		Main mn = new Main();
		mn.run();
		
		time = new Time();
		time.start();	
	}
}

class MyTask extends TimerTask {
	public MyTask() {}
	
	public void run() {		
		Main.time();
	}
}

class Time {
	  private MyTask task;
	  private Timer timer;
	  public Time() {
	    this.timer = new Timer(true);
	  }

	  public void start() {
	    task = new MyTask();
	    timer.scheduleAtFixedRate(task, 0, 120000);
	  }

	  public void stop() {
	    task.cancel();
	  }
}
		
		