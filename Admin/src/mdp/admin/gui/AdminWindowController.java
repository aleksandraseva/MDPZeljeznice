package mdp.admin.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import mdp.admin.logger.AdminLogger;
import mdp.admin.threads.CitanjeObavjestenjaThread;
import mdp.azsmdp.model.FilePodaci;
import mdp.azsmdp.server.ArhiviranjeIzvjestajaInterface;

public class AdminWindowController implements Initializable {
	
	private static final String PATH_POLICYFILE=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"client_policyfile.txt";
	
	public Button kreirajKorisnikaButton;
	public Button brisanjeKorisnikaButton;
	public Button pregledKorisnikaButton;
	
	public Button kreiranjeLinijeButton;
	public Button brisanjeLinijeButton;
	public Button pregledLinijaButton;
	
	public TextArea obavjestenjeArea;
	public Circle obavjestenjeCircle;
	
	public ComboBox<String> izvjestajiBox;
	public Button pogledajIzvjestajButton;
	public Button preuzmiIzvjestajButton;
	public TextArea pregledIvjestajaArea;
	
	public Button zatvoriButton;
	
	
	public void kreirajKorisnikaClic() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("KreiranjeKorisnika.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void brisanjeKorisnikaClick() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("BrisanjeKorisnika.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void pregledKorisnikaClick() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PregledKorisnika.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void dodajLinijuClick() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("DodavanjeLinije.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void obrisiLinijuClick() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("BrisanjeLinije.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void pogledajRedVoznjeClick() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PregledRedaVoznje.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void popuniIzvjestajBoxClick() {
		System.setProperty("java.security.policy",PATH_POLICYFILE);
		if(System.getSecurityManager()==null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registar=LocateRegistry.getRegistry(1099);
			
			ArhiviranjeIzvjestajaInterface arhiviranje=(ArhiviranjeIzvjestajaInterface) registar.lookup("ArhiviranjeIzvjestaja");
			ArrayList<String> lista=arhiviranje.dohvatiImena();
			ObservableList<String> listaImena=FXCollections.observableArrayList(lista);
			izvjestajiBox.getItems().clear();
			izvjestajiBox.setItems(listaImena);
			
		}
		catch(RemoteException | NotBoundException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void pogledajIzvjestaj() {
		System.setProperty("java.security.policy",PATH_POLICYFILE);
		if(System.getSecurityManager()==null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registar=LocateRegistry.getRegistry(1099);
			
			ArhiviranjeIzvjestajaInterface arhiviranje=(ArhiviranjeIzvjestajaInterface) registar.lookup("ArhiviranjeIzvjestaja");
			FilePodaci podaci=arhiviranje.dohvatiPodatke(izvjestajiBox.getValue());
			pregledIvjestajaArea.clear();
			pregledIvjestajaArea.setText(podaci.toString());
		}
		catch(RemoteException | NotBoundException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void preuzmiClick() {
		File dest=new File(System.getProperty("user.dir")+File.separator+"izvjestaji");
		if(!dest.exists())
			dest.mkdir();
		
		System.setProperty("java.security.policy",PATH_POLICYFILE);
		if(System.getSecurityManager()==null)
			System.setSecurityManager(new SecurityManager());
		try {
			Registry registar=LocateRegistry.getRegistry(1099);
			
			ArhiviranjeIzvjestajaInterface arhiviranje=(ArhiviranjeIzvjestajaInterface) registar.lookup("ArhiviranjeIzvjestaja");
			arhiviranje.download(izvjestajiBox.getValue(), dest);
		}
		catch(RemoteException | NotBoundException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}
	
	public void zatvoriClick() {
		System.exit(0);
	}
	
	public void procitanoObavjestenje() {
		obavjestenjeCircle.setFill(Color.WHITE);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new CitanjeObavjestenjaThread(this);
		
	}
}
