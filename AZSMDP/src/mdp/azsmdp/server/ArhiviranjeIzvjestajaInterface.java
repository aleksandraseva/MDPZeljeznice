package mdp.azsmdp.server;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import mdp.azsmdp.model.KorisnikFile;
import mdp.azsmdp.model.FilePodaci;

public interface ArhiviranjeIzvjestajaInterface extends Remote {

	public boolean upload(File file,KorisnikFile kornik) throws RemoteException;
	public boolean download(String nameFile,File dest) throws RemoteException;
	public ArrayList<String> dohvatiImena() throws RemoteException;
	public FilePodaci dohvatiPodatke(String fileIme) throws RemoteException;
}
