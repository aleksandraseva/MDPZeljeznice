package mdp.zsmdp.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import javafx.scene.paint.Color;
import mdp.zsmdp.gui.MainWindowController;
import mdp.zsmdp.logger.ZSMDPLogger;

public class CitanjePorukeThread extends Thread {
	private int port;
	private MainWindowController mwc;
	
	
	public CitanjePorukeThread(int port, MainWindowController mwc) {
		super();
		this.port = port;
		//System.out.println(port);
		this.mwc = mwc;
		start();
	}
	
	public void run() {
		try (ServerSocket server=new ServerSocket(port)){
			while(true){                                    
				Socket socket=server.accept();
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String poruka=in.readLine();
				String sadrzaj=mwc.chatArea.getText();
				sadrzaj+=poruka+"\n";
				mwc.chatArea.setText(sadrzaj);
				mwc.porukaCircle.setFill(Color.RED);
				in.close();
				socket.close();
			}
			
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
	
}
