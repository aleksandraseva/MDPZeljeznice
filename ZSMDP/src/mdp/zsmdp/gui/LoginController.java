package mdp.zsmdp.gui;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mdp.czsmdp.model.Korisnik;
import mdp.czsmdp.servisi.KorisnikServis;
import mdp.czsmdp.servisi.KorisnikServisServiceLocator;
import mdp.zsmdp.logger.ZSMDPLogger;

public class LoginController {

	public ComboBox<String> lokacijeBox;
	public TextField imeField;
	public PasswordField lozinkaField;
	public Button prijavaButton;

	public void prijavaClick() {
		try {
			String ime = imeField.getText();
			String lokacija = lokacijeBox.getValue();
			String lozinka = lozinkaField.getText();

			KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
			KorisnikServis servis = locator.getKorisnikServis();
			
			boolean postoji = servis.provjeraKorisnika(new Korisnik(lokacija, hashPass(lozinka), ime));
			if (postoji) {
				
				Korisnik korisnik=new Korisnik(lokacija,hashPass(lozinka),ime);
				
				MainWindowController.korisnik=korisnik;
				Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle(korisnik.getLokacija());
				stage.show();
				
				prijavaButton.getScene().getWindow().hide();
			}
			else {
				Alert alert=new Alert(AlertType.ERROR);
				alert.setContentText("Netacni podaci ili je vec neko prijavljen na izabranoj lokaciji!");
				alert.setHeaderText("GRESKA");
				alert.showAndWait();
			}

			
		} catch (ServiceException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		} catch (IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}

	public String hashPass(String lozinka) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashByte = md.digest(lozinka.getBytes());
			BigInteger i = new BigInteger(1, hashByte);
			String hashLozinka = i.toString(16);
			while (hashLozinka.length() < 32)
				hashLozinka = "0" + hashLozinka;
			return hashLozinka;

		} catch (NoSuchAlgorithmException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
			return null;
		}

	}
}
