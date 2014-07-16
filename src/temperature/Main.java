package temperature;

import java.util.Arrays;


public class Main {
	
	private String parseData(String data){
		String [] parts = data.split(" ");
		String temperatur = null;
				
		if(parts[11].equals("NO")){
			return null;
		}else{
			temperatur = parts[20];
		}
		return temperatur;
	}
	
	private double celsius(String data){
		String tmp = parseData(data);
		double temper = 0.0;
		
		if(tmp == null){
			checkData();
		}else{
			String [] parts = tmp.split("=");
			temper = Double.parseDouble(parts[1]);
			temper /= 100;
			temper = (double)Math.round(temper)/10;
		}
		return temper;
	}
	
	private void checkData(){
		System.out.println("Data nenactena");
	}

	public static void main(String[] args) {
		Main mn = new Main();
		ScpFrom ssh = new ScpFrom();
		
		String data = ssh.getData();
		double temperature = mn.celsius(data);
		
		System.out.println(temperature+" °C");

	}
}
		
		