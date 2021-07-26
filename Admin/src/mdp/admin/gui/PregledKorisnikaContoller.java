package mdp.admin.gui;

import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.xml.rpc.ServiceException;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import mdp.admin.logger.AdminLogger;
import mdp.czsmdp.model.Korisnik;
import mdp.czsmdp.servisi.KorisnikServis;
import mdp.czsmdp.servisi.KorisnikServisServiceLocator;

public class PregledKorisnikaContoller {
	public ComboBox<String> lokacijeBox;
	public Button pogledajButton;
	public TextArea korisniciArea;

	public void pogledajClick() {
		KorisnikServisServiceLocator locator = new KorisnikServisServiceLocator();
		try {
			KorisnikServis servis = locator.getKorisnikServis();
			Korisnik[] korisnici = servis.dohvatiKorisnike(lokacijeBox.getValue());
			String text = "";
			if (korisnici!=null) {
				for (Korisnik k : korisnici) {
					// System.out.println(k.getUsername());
					text += k.getUsername() + "\n";
				}
			}
			korisniciArea.setText(text);
		} catch (ServiceException | RemoteException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
	}

}
