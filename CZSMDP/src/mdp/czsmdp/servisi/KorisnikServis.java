package mdp.czsmdp.servisi;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import mdp.czsmdp.logger.CZSMDPLogger;
import mdp.czsmdp.model.Korisnik;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class KorisnikServis {
	
	private static final String PATH=System.getProperty("user.dir")+File.separator+"MDPZeljeznice"+File.separator
			+"CZSMDP"+File.separator+"korisnici";
	
	public boolean registrovanjeKorisnika(Korisnik korisnik) {
		try {
			String imeFile=korisnik.getUsername()+".xml";
			String pathFile=PATH+File.separator+korisnik.getLokacija()+File.separator+imeFile;
			File dir=new File(PATH+File.separator+korisnik.getLokacija());
			if(!dir.exists())
				dir.mkdir();
			//System.out.println(pathFile);
			File file=new File(pathFile);
			if(file.exists()) {
				return false;
			}
			else {
				file.createNewFile();
				XMLEncoder xmlEncoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(pathFile)));
				xmlEncoder.writeObject(korisnik);
				xmlEncoder.close();
				return true;
			}
		}
		catch(IOException ex) {
			CZSMDPLogger.getInstance();
			CZSMDPLogger.logger.log(Level.SEVERE, ex.getMessage());
			return false;
		}
	}
	
	public boolean brisanjeKorisnika(String lokacija,String username) {
		String imeFile=username+".xml";
		String pathFile=PATH+File.separator+lokacija+File.separator+imeFile;
		//System.out.println(pathFile);
		File file=new File(pathFile);
		if(!file.exists()) {
			return false;
		}
		else {
			return file.delete();
		}
	}
	
	public boolean provjeraKorisnika(Korisnik korisnik) {
		try {
			String prijavljeniKorisnik=getOnlineKorisnika(korisnik.getLokacija());
			if(prijavljeniKorisnik.equals("")) {
				String imeFile=korisnik.getUsername()+".xml";
				String pathFile=PATH+File.separator+korisnik.getLokacija()+File.separator+imeFile;
				File file=new File(pathFile);
				if(!file.exists()) {
					return false;
				}
				else {
					XMLDecoder xmlDecoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(pathFile)));
					Korisnik pronadjeniKorisnik=(Korisnik) xmlDecoder.readObject();
					if(pronadjeniKorisnik.equals(korisnik)) {
						xmlDecoder.close();
					
						unesiOnlineKorisnika(pronadjeniKorisnik);
					
						return true;
					}
					else {
						xmlDecoder.close();
						return false;
					}
				}
			}
			return false;
		}
		catch(IOException ex) {
			CZSMDPLogger.getInstance();
			CZSMDPLogger.logger.log(Level.SEVERE, ex.getMessage());
			return false;
		}		
	}
	
	private void unesiOnlineKorisnika(Korisnik korisnik) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			jedis.set(korisnik.getLokacija()+":online",korisnik.getUsername());
		}
		jPool.close();
	}
	
	public String getOnlineKorisnika(String lokacija) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			String username=jedis.get(lokacija+":online");
			jPool.close();
			return username;
		}
		
	}
	
	public String getPort(String lokacija) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			jPool.close();
			return jedis.get(lokacija+":port");
		}
	}
	
	public void odjavaKorisnika(Korisnik korisnik) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			jedis.set(korisnik.getLokacija()+":online","");
			
		}
		jPool.close();
	}
	
	public Korisnik[] dohvatiKorisnike(String lokacija){
		ArrayList<Korisnik> korisnici=new ArrayList<>();
		String path=PATH+File.separator+lokacija;
		File pathFiles=new File(path);
		File[] files=pathFiles.listFiles();
		for(File f:files) {
			try {
				//System.out.println(f);
				XMLDecoder xmlDecoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(f)));
				Korisnik korisnik=(Korisnik) xmlDecoder.readObject();
				//System.out.println(korisnik);
				korisnici.add(korisnik);
				xmlDecoder.close();
			}
			catch(IOException ex) {
				CZSMDPLogger.getInstance();
				CZSMDPLogger.logger.log(Level.SEVERE, ex.getMessage());
			}
		}
		
		return korisnici.toArray(new Korisnik[korisnici.size()]);
	}
	

}
