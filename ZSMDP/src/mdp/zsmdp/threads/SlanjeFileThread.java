package mdp.zsmdp.threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

import mdp.zsmdp.logger.ZSMDPLogger;


public class SlanjeFileThread extends Thread {
	private int port;
	private String ime;
	private File file;
	
	public SlanjeFileThread(int port,String ime,File file) {
		this.port=port;
		this.ime=ime;
		this.file=file;
		start();
	}
	
	public void run() {
		try {
			Socket socket=new Socket("localhost",port);
			
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			String poruka=ime+"# file:"+file.getName();
			out.println(poruka);
			
			Path dest=Paths.get(System.getProperty("user.dir")).resolve(file.getName());
			//System.out.println(dest);
			Path source=file.toPath();
			//System.out.println(source);
			Files.copy(source,dest,StandardCopyOption.REPLACE_EXISTING);
			
			out.close();
			socket.close();
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
	

}
