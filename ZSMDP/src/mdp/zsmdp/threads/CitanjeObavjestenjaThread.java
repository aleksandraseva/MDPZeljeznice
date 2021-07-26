package mdp.zsmdp.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.logging.Level;

import javafx.scene.paint.Color;
import mdp.zsmdp.gui.MainWindowController;
import mdp.zsmdp.logger.ZSMDPLogger;

public class CitanjeObavjestenjaThread extends Thread {

	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	private static int PORT_MULTICAST;
	private static String IP_HOST;

	private MainWindowController mwc;

	public CitanjeObavjestenjaThread(MainWindowController mwc) {
		super();
		
		try {
			Properties properties=new Properties();
			InputStream is=new FileInputStream(PATH_CONFIG);
			properties.load(is);
			String portString=properties.getProperty("PORT_MULTICAST");
			if(portString==null)
				throw new MissingFormatArgumentException("Port nije definisan u config fajlu!");
			PORT_MULTICAST=Integer.parseInt(portString);
			
			String ipString=properties.getProperty("IP_HOST");
			if(ipString==null)
				throw new MissingFormatArgumentException("Ip adresa nije definisana u config fajlu!");
			IP_HOST=ipString;
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
		
		this.mwc = mwc;
		start();
	}

	@Override
	public void run() {
		try {
			InetAddress adresa = InetAddress.getByName(IP_HOST);
			byte[] buffer = new byte[512];
			try (MulticastSocket socket = new MulticastSocket(PORT_MULTICAST)) {
				socket.joinGroup(adresa);
				while (true) {
					DatagramPacket paket = new DatagramPacket(buffer, buffer.length);
					socket.receive(paket);
					String obavjestenje = new String(paket.getData(), paket.getOffset(), paket.getLength());
					mwc.obavjestenjaArea.clear();
					mwc.obavjestenjaArea.setText(obavjestenje);
					mwc.obavjestenjeCircle.setFill(Color.DARKRED);
				}
			} catch (IOException ex) {
				ZSMDPLogger.getInstance();
				ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
			}
		} catch (UnknownHostException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
}
