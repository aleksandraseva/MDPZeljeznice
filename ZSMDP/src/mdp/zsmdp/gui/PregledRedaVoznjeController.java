package mdp.zsmdp.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.json.JSONArray;

import com.google.gson.Gson;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import mdp.zsmdp.logger.ZSMDPLogger;
import mdp.zsmdp.model.Linija;

public class PregledRedaVoznjeController implements Initializable{
	
	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	public static String BASE_URL;

	public TextArea redVoznjeArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try(InputStream is=new FileInputStream(PATH_CONFIG)) {
			Properties properties=new Properties();
			properties.load(is);
			String urlString=properties.getProperty("BASE_URL");
			if(urlString==null)
				throw new MissingFormatArgumentException("Url nije definisan u config fajlu!");
			BASE_URL=urlString;
		}
		catch(IOException ex) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,ex.getMessage());
		}	
		
		String nazivStanice=MainWindowController.korisnik.getLokacija();
		//System.out.println(nazivStanice);
		try(InputStream is=new URL(BASE_URL+nazivStanice).openStream()){
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String json=readAll(br);
			JSONArray listaLinija=new JSONArray(json);
			Gson gson=new Gson();
			for(int i=0;i<listaLinija.length();i++) {
				redVoznjeArea.appendText(gson.fromJson(listaLinija.getJSONObject(i).toString(), Linija.class).toString());
				redVoznjeArea.appendText("\n");
			}
			
		} catch (MalformedURLException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		} catch (IOException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
	}
	
	public String readAll(Reader reader) {
		StringBuilder sb=new StringBuilder();
		int znak;
		try {
			while((znak=reader.read())!=-1)
				sb.append((char)znak);
		} catch (IOException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
		return sb.toString();
	}
}
