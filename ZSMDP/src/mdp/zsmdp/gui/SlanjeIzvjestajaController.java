package mdp.zsmdp.gui;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mdp.azsmdp.model.KorisnikFile;
import mdp.azsmdp.server.ArhiviranjeIzvjestajaInterface;
import mdp.zsmdp.logger.ZSMDPLogger;


public class SlanjeIzvjestajaController {
	
	private static final String PATH_RESOUCES=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"client_policyfile.txt";

	public Button izvjestajButton;
	public Button poslajiButton;
	public TextField fileField;
	
	private File izabraniFile;
	
	public void izvjestajClick() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		izabraniFile = fileChooser.showOpenDialog(stage);
		
		fileField.setText(izabraniFile.getName());
		
	}
	
	public void posaljiClick() {
		System.setProperty("java.security.policy",PATH_RESOUCES);
		if(System.getSecurityManager()==null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			Registry registar=LocateRegistry.getRegistry(1099);
			
			ArhiviranjeIzvjestajaInterface arhIzvjestaj=(ArhiviranjeIzvjestajaInterface) registar.lookup("ArhiviranjeIzvjestaja");
			arhIzvjestaj.upload(izabraniFile,new KorisnikFile(MainWindowController.korisnik.getUsername(),MainWindowController.korisnik.getLokacija()));
		}
		catch(RemoteException | NotBoundException  ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}
	}
}
