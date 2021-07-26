package mdp.azsmdp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;

import com.google.gson.Gson;

import mdp.azsmdp.model.KorisnikFile;
import mdp.azsmdp.logger.AZSMDPLogger;
import mdp.azsmdp.model.FilePodaci;

public class ArhiviranjeIzvjestajaServer implements ArhiviranjeIzvjestajaInterface{
	
	public static final String PATH_ARHIVA=System.getProperty("user.dir")+File.separator+"arhiva";
	public static final String PATH_PODACI=System.getProperty("user.dir")+File.separator+"podaci";
	public static final String PATH_RESOURCES=System.getProperty("user.dir")+File.separator+"resources";

	@Override
	public boolean upload(File file,KorisnikFile korisnik) throws RemoteException {
		String extension="";
		String fileName=file.getName();
		String[] podaci=fileName.split("\\.");
		extension=podaci[1].toLowerCase();
		//System.out.println(extension);
		if(extension.equals("pdf")) {
			File dir=new File(PATH_ARHIVA);
			if(!dir.exists())
				dir.mkdir();
			Path source=file.toPath();
			Path dest=Paths.get(PATH_ARHIVA).resolve(fileName);
			try {
				Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				AZSMDPLogger.getInstance();
				AZSMDPLogger.logger.log(Level.ALL,e.getMessage());
				return false;
			}
			Gson gson=new Gson();
			FilePodaci podaciFile=new FilePodaci(korisnik,fileName,LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),file.length());  //file.length()-u bajtovima
			String jsonPodaci=gson.toJson(podaciFile);
			//System.out.println(jsonPodaci);
			String jsonFileName=podaci[0]+".json";
			dir=new File(PATH_PODACI);
			if(!dir.exists())
				dir.mkdir();
			try(PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(new File(PATH_PODACI+File.separator+jsonFileName))))){
				pw.println(jsonPodaci);
			}
			catch(IOException ex) {
				AZSMDPLogger.getInstance();
				AZSMDPLogger.logger.log(Level.ALL,ex.getMessage());
			}
			return true;
		}
		else
			return false;

	}

	@Override
	public boolean download(String nameFile,File dest) throws RemoteException {
		File file=new File(PATH_ARHIVA+File.separator+nameFile);
		if(file.exists()) {
			Path source=file.toPath();
			Path destination=dest.toPath().resolve(nameFile);
			try {
				Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				AZSMDPLogger.getInstance();
				AZSMDPLogger.logger.log(Level.ALL,e.getMessage());
				return false;
			}
			return true;
		}
		else
			return false;
		
	}
	
	@Override
	public ArrayList<String> dohvatiImena() throws RemoteException {
		ArrayList<String> listaImena=new ArrayList<>();
		File pathFile=new File(PATH_ARHIVA);
		File[] files=pathFile.listFiles();
		for(File file:files) {
				listaImena.add(file.getName());
		}
		return listaImena;
	} 
	
	@Override
	public FilePodaci dohvatiPodatke(String fileIme) throws RemoteException {
		String[] pom=fileIme.split("\\.");
		String ime=pom[0]+".json";
		File pathFile=new File(PATH_PODACI+File.separator+ime);
			try {
				BufferedReader br=new BufferedReader(new FileReader(pathFile));
				String podaci=br.readLine();
				
				Gson gson=new Gson();
				
				FilePodaci podaciFile=gson.fromJson(podaci, FilePodaci.class);
						
				br.close();
				return podaciFile;
			} catch (FileNotFoundException e) {
				AZSMDPLogger.getInstance();
				AZSMDPLogger.logger.log(Level.ALL,e.getMessage());
				return null;
			} catch(IOException ex) {
				AZSMDPLogger.getInstance();
				AZSMDPLogger.logger.log(Level.ALL,ex.getMessage());
				return null;
			}
		}

	public static void main(String[] args) throws RemoteException {
		//System.out.println(PATH_RESOURCES+File.separator+"server_policyfile.txt");
		System.setProperty("java.security.policy",PATH_RESOURCES+File.separator+"server_policyfile.txt");
		if(System.getSecurityManager()==null)
			System.setSecurityManager(new SecurityManager());
		try {
			ArhiviranjeIzvjestajaServer server=new ArhiviranjeIzvjestajaServer();
			ArhiviranjeIzvjestajaInterface stub=(ArhiviranjeIzvjestajaInterface) UnicastRemoteObject.exportObject(server,0);
			Registry registar=LocateRegistry.createRegistry(1099);
			registar.rebind("ArhiviranjeIzvjestaja", stub);
		}
		catch(Exception ex) {
			AZSMDPLogger.getInstance();
			AZSMDPLogger.logger.log(Level.ALL,ex.getMessage());
		}
	}
	
}
