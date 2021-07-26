package mdp.zsmdp.threads;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import mdp.zsmdp.gui.MainWindowController;
import mdp.zsmdp.logger.ZSMDPLogger;

public class SlanjePorukeThread extends Thread {
	
	private int port;
	private String ime;
	private MainWindowController mwc;
	
	public SlanjePorukeThread(String ime,int port,MainWindowController mwc) {
		this.port=port;
		this.ime=ime;
		this.mwc=mwc;
		start();
	}
	
	public void run() {
		try {
			Socket socket=new Socket("localhost",port);
			
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			String poruka=ime+"#"+mwc.porukaArea.getText();
			mwc.porukaArea.clear();
			out.println(poruka);
			out.close();
			socket.close();			
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}

}
