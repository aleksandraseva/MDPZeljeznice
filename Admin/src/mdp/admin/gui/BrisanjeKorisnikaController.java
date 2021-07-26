package mdp.admin.gui;

import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import mdp.admin.logger.AdminLogger;
import mdp.czsmdp.servisi.KorisnikServis;
import mdp.czsmdp.servisi.KorisnikServisServiceLocator;

public class BrisanjeKorisnikaController {
	public ComboBox<String> lokacijeBox;
	public TextField imeField;
	public Button obrisiButton;
	
	public void brisanjeClick() {
		KorisnikServisServiceLocator locator=new KorisnikServisServiceLocator();
		try {
			KorisnikServis servis=locator.getKorisnikServis();
			boolean izvrseno=servis.brisanjeKorisnika(lokacijeBox.getValue(), imeField.getText());
			if (izvrseno) {
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Uspjeno obrisan korisnik!");
				alert.showAndWait();
				obrisiButton.getScene().getWindow().hide();
			}
			else {
				Alert alert=new Alert(AlertType.ERROR);
				alert.setHeaderText("GRESKA!");
				alert.setContentText("Netacni podaci!");
				alert.showAndWait();
			}
			
		} catch (ServiceException | RemoteException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		
	}
}
