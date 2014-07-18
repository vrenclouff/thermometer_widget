package temperature;


public class Main {
	
	private String parseData(String data){
		String [] parts = data.split(" ");
		String temperatur = null;
				
		if(parts[11].split("\n")[0].trim().equals("NO")){
			temperatur = null;
		}else{
			temperatur = parts[20];
		}
		return temperatur;
	}
	
	private String celsius(String data){
		String tmp = parseData(data);
		double temper = 0.0;
		
		if(tmp == null){ return tmp;} // return null (error)

		String [] parts = tmp.split("=");
		temper = Double.parseDouble(parts[1]);
		temper /= 10;
		temper = (double)Math.round(temper)/100;
		tmp = String.valueOf(temper);

		return tmp;	// return temperature
	}


	public static void main(String[] args) {
		Main mn = new Main();
		ScpFrom ssh = new ScpFrom();
		
		String data = ssh.getData();
		String temperature = mn.celsius(data);
		
		Canvas cvs = new Canvas();
		cvs.run();
		cvs.repaint();
		
		System.out.println(temperature+" °C");

	}
}
		
		