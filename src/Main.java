

import java.util.Timer;
import java.util.TimerTask;

import com.jcraft.jsch.JSchException;


public class Main {
	
	static Time time;
	static ScpFrom ssh;
	Canvas cvs;
	
	final static String file_in="/sys/bus/w1/devices/28-00000609dbe0/w1_slave";
    final static String command_inside="scp -f "+file_in;
    
    final static String file_out="";
    final static String command_outside="scp -f "+file_out;
	
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
		
		if(data == null){ return "null";}
		
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
		ssh = new ScpFrom();
		cvs = new Canvas();
		cvs.run();
	}
	
	public static void time(){
		
		if(!ssh.session.isConnected()){
			try { ssh.session.connect();} 
			catch (JSchException e) { e.printStackTrace();}
		}
		
		String data_in = ssh.getData(command_inside);
		String data_out = ssh.getData(command_outside);
				
		String inside = celsius(data_in);
		String outside = celsius(data_out);
		
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
		
		