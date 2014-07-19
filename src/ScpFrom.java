


import com.jcraft.jsch.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class ScpFrom{
	
	
    final String user="pi";
    final static String password = "raspberry";
    final String host="10.109.23.91";
    final int port = 22;
    	
	Session session = null;
	OutputStream out = null;
	InputStream in = null;
	Channel channel = null;
	
  public ScpFrom(){
    
    try{
            
      JSch jsch=new JSch();
      session=jsch.getSession(user, host, port);

      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
      session.connect();
      	  	  
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
 
  
  public String getData(String command){
	  	  
	  try{
		  
		  channel=session.openChannel("exec");
		  ((ChannelExec)channel).setCommand(command);
		  
		  out=channel.getOutputStream();
		  in=channel.getInputStream();
		  channel.connect();
			  
		  byte[] buf=new byte[1024];
	
	      // send '\0'
	      buf[0]=0;
	      out.write(buf, 0, 1);
	      out.flush();
		  
		  while(true){
		    	int c=checkAck(in);
		    	if(c!='C'){break;}
	
		        // read '0644 '
		        in.read(buf, 0, 5);
	
		        long filesize=0L;
		        
		        while(true){
		          if(in.read(buf, 0, 1)<0){break;}
		          if(buf[0]==' '){break;}
		          filesize=filesize*10L+(long)(buf[0]-'0');
		        }
	
		        for(int i=0;;i++){
		          in.read(buf, i, 1);
		          if(buf[i]==(byte)0x0a){break;}
		        }
	
		        // send '\0'
		        buf[0]=0;
		        out.write(buf, 0, 1);
		        out.flush();
		        
		        // read a content of lfile
		        int foo;
		        
		        while(true){
		          if(buf.length<filesize){ foo=buf.length;}
		          else{foo=(int)filesize;}
		          
		          foo=in.read(buf, 0, foo);
		   
		          if(foo<0){ break;}
		          
		          String s1 = new String(buf, 0, foo);
		          for(int i = 0; i < s1.length(); i++){
		        	  char c1 = s1.charAt(i);
		        	  if((c1 >= 'a' || c1 >= 'A') && (c1 <= 'z' || c1 <= 'Z')){
		        		  return s1;
		        	  }
		          }
		        	  
		          
		          filesize-=foo;
		          if(filesize==0L){break;}
		        }
		        
		        // ukonceni programu pri preneseni souboru
		        if(checkAck(in)!=0){ break;}
		    	
		        // send '\0'
		    	buf[0]=0; 
		    	out.write(buf, 0, 1); 
		    	out.flush();
		  }
	}catch(Exception e){
	   System.out.println(e);
	}
	  
	return null; 
  }

  static int checkAck(InputStream in) throws IOException{
    int b=in.read();
    // b may be 0 for success,
    //          1 for error,
    //          2 for fatal error,
    //          -1
    if(b==0) return b;
    if(b==-1) return b;

    if(b==1 || b==2){
      StringBuffer sb=new StringBuffer();
      int c;
      do {
	c=in.read();
	sb.append((char)c);
      }
      while(c!='\n');
      if(b==1){ // error
//	System.out.print(sb.toString());
      }
      if(b==2){ // fatal error
//	System.out.print(sb.toString());
      }
    }
    return b;
  }

  public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
	String passwd = ScpFrom.password;
	
    public String getPassword(){
    	return passwd; 
    }
    
    public boolean promptYesNo(String str){
        return true;
      }
  
    public String getPassphrase(){ 
    	return null; 
    }
    
    public boolean promptPassphrase(String message){
    	return true; 
    }
    
    public boolean promptPassword(String message){
        return true;
      }
    
    
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    
    final GridBagConstraints gbc = new GridBagConstraints(0,0,1,1,1,1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0,0,0,0),0,0);
    private Container panel;
    
    public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt, boolean[] echo){
      panel = new JPanel();
      panel.setLayout(new GridBagLayout());

      gbc.weightx = 1.0;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridx = 0;
      panel.add(new JLabel(instruction), gbc);
      gbc.gridy++;

      gbc.gridwidth = GridBagConstraints.RELATIVE;

      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(prompt[i]),gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        panel.add(texts[i], gbc);
        gbc.gridy++;
      }

      if(JOptionPane.showConfirmDialog(null, panel, destination+": "+name, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION){
        String[] response=new String[prompt.length];
        
        for(int i=0; i<prompt.length; i++){
          response[i]=texts[i].getText();
        }
        return response;
      }
      else{
        return null;  // cancel
      }
    }
  }
}
