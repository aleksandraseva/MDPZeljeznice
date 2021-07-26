package mdp.zsmdp.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.logging.Level;

import mdp.zsmdp.logger.ZSMDPLogger;

public class SlanjeObavjestenjaThread extends Thread {

	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	private static int PORT_MULTICAST;
	private static String IP_HOST;
	
	private String obavjestenje;
	
	public SlanjeObavjestenjaThread(String obavjestenje) {
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
		
		this.obavjestenje=obavjestenje;
		start();
	}
	
	@Override
	public void run() {
		try {
			InetAddress adresa=InetAddress.getByName(IP_HOST);
			try(DatagramSocket socket=new DatagramSocket()){
				DatagramPacket obavjestenjePaket=new DatagramPacket(obavjestenje.getBytes(), obavjestenje.getBytes().length,adresa,PORT_MULTICAST);
				socket.send(obavjestenjePaket);
			}
			catch(SocketException ex) {
				ZSMDPLogger.getInstance();
				ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
			}
		} catch (UnknownHostException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
}
