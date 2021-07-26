package mdp.admin.threads;

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
import mdp.admin.gui.AdminWindowController;
import mdp.admin.logger.AdminLogger;

public class CitanjeObavjestenjaThread extends Thread {

	private static int PORT_MULTICAST;
	private static String IP_HOST;
	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";

	private AdminWindowController awc;

	public CitanjeObavjestenjaThread(AdminWindowController awc) {
		super();
		try(InputStream is=new FileInputStream(PATH_CONFIG)) {
			Properties properties=new Properties();
			properties.load(is);
			
			String portValue=properties.getProperty("PORT_MULTICAST");
			if(portValue!=null)
				PORT_MULTICAST=Integer.parseInt(portValue);
			else
				throw new MissingFormatArgumentException("Port nije definisan u config fajlu!");
			String ipValue=properties.getProperty("IP_HOST");
			if(ipValue!=null)
				IP_HOST=ipValue;
			else
				throw new MissingFormatArgumentException("IP adresa nije definisana u config fajlu!");
		}
		catch(IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		this.awc = awc;
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
					awc.obavjestenjeArea.clear();
					awc.obavjestenjeArea.setText(obavjestenje);
					awc.obavjestenjeCircle.setFill(Color.DARKRED);
				}
			} catch (IOException ex) {
				AdminLogger.getInstance();
				AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
			}
		} catch (UnknownHostException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
}
