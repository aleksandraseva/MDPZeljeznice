package mdp.zsmdp.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mdp.czsmdp.model.Korisnik;
import mdp.czsmdp.servisi.KorisnikServis;
import mdp.czsmdp.servisi.KorisnikServisServiceLocator;
import mdp.zsmdp.threads.CitanjePorukeThread;
import mdp.zsmdp.logger.ZSMDPLogger;
import mdp.zsmdp.threads.CitanjeObavjestenjaThread;
import mdp.zsmdp.threads.SlanjeFileThread;
import mdp.zsmdp.threads.SlanjePorukeThread;

public class MainWindowController implements Initializable {

	public Button pregledButton;
	public Button evidentiranjeButton;
	public Button obavjestenjeButton;
	public Button izvjestajButton;
	public Button odjavaButton;

	public ComboBox<String> lokacijaBox;
	public ComboBox<String> korisniciBox;
	public Button posaljiPorukuButton;
	public Button poslajiFileButton;
	public TextArea porukaArea;
	public TextArea chatArea;

	public Circle porukaCircle;
	public Circle obavjestenjeCircle;

	public TextArea obavjestenjaArea;

	public static Korisnik korisnik;

	public void onlineKorisniciClick() {
		String lokacija = lokacijaBox.getValue();

		try {
			KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
			KorisnikServis servis = locator.getKorisnikServis();

			String ime = servis.getOnlineKorisnika(lokacija);
			// System.out.println(ime);
			korisniciBox.getItems().clear();
			ObservableList<String> korisnici = FXCollections.observableArrayList(ime);
			korisniciBox.setItems(korisnici);
		} catch (ServiceException | RemoteException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}

	}

	public void odjavaClick() {
		try {
			KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
			KorisnikServis servis = locator.getKorisnikServis();

			servis.odjavaKorisnika(korisnik);
			System.exit(0);
		} catch (ServiceException | RemoteException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}

	}

	public void posaljiPorukuClick() {
		String port = "";
		if (lokacijaBox.getValue().equals(korisnik.getLokacija())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nevalidna opcija!");
			alert.setHeaderText("GRESKA");
			alert.showAndWait();

		} else {
			try {
				KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
				KorisnikServis servis = locator.getKorisnikServis();

				port = servis.getPort(lokacijaBox.getValue());
				// System.out.println(port);

			} catch (ServiceException | RemoteException ex) {
				ZSMDPLogger.getInstance();
				ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
			}
			new SlanjePorukeThread(korisnik.getUsername(), Integer.parseInt(port), this);
		}
	}

	public void procitanaPoruka() {
		porukaCircle.setFill(Color.WHITE);
	}

	public void posaljiFileClick() {
		String port = "";
		if (lokacijaBox.getValue().equals(korisnik.getLokacija())) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nevalidna opcija!");
			alert.setHeaderText("GRESKA");
			alert.showAndWait();

		} else {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(stage);
			try {
				KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
				KorisnikServis servis = locator.getKorisnikServis();

				port = servis.getPort(lokacijaBox.getValue());
				
				new SlanjeFileThread(Integer.parseInt(port),korisnik.getUsername(),file);

			} catch (ServiceException | RemoteException ex) {
				ZSMDPLogger.getInstance();
				ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
			}
		}
	}
	
	public void slanjeIzvjestajaClick() {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("SlanjeIzvjestaja.fxml"));
			Stage stage=new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
	
	public void slanjeObavjestenjaClick() {
		//System.out.println("click");
		try {
			Parent root=FXMLLoader.load(getClass().getResource("SlanjeObavjestenja.fxml"));
			Stage stage=new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
	
	public void procitanoObavjestenje() {
		obavjestenjeCircle.setFill(Color.WHITE);
	}
	
	public void pregledRedaVoznjeClick() {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("PregledRedaVoznje.fxml"));
			Stage stage=new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
	
	public void evidentirajProlazakClick() {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("EvidentiranjeVoza.fxml"));
			Stage stage=new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lokacijaBox.setValue(korisnik.getLokacija());
		String port = "";
		try {
			KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
			KorisnikServis servis = locator.getKorisnikServis();

			port = servis.getPort(korisnik.getLokacija());

		} catch (ServiceException | RemoteException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
		new CitanjePorukeThread(Integer.parseInt(port), this);
		new CitanjeObavjestenjaThread(this);
	}

}
