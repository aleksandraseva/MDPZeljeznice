package mdp.admin.gui;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mdp.admin.logger.AdminLogger;
import mdp.czsmdp.model.Korisnik;
import mdp.czsmdp.servisi.KorisnikServis;
import mdp.czsmdp.servisi.KorisnikServisServiceLocator;

public class KreiranjeKorisnikaController {

	public ComboBox<String> lokacijeBox;
	public TextField imeField;
	public PasswordField lozinkaField;
	public Button kreirajButton;
	
	public void kreirajClick() {
		String lokacija = lokacijeBox.getValue();
		String ime = imeField.getText();
		String lozinka = lozinkaField.getText();

		// System.out.println(hashPass(lozinka));

		KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
		try {
			KorisnikServis servis = locator.getKorisnikServis();
			boolean izvrseno = servis.registrovanjeKorisnika(new Korisnik(lokacija, hashPass(lozinka), ime));
			if (izvrseno) {
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Uspjeno registrovan korisnik!");
				alert.showAndWait();
				kreirajButton.getScene().getWindow().hide();
			}
			else {
				Alert alert=new Alert(AlertType.ERROR);
				alert.setHeaderText("GRESKA!");
				alert.setContentText("Vec postoji korisnik sa datim podacima!");
				alert.showAndWait();
			}
		} catch (ServiceException | RemoteException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE,ex.getMessage());
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
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE,ex.getMessage());
			return null;
		}

	}

}
