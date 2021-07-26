package mdp.admin.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.json.JSONArray;

import com.google.gson.Gson;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import mdp.admin.logger.AdminLogger;
import mdp.admin.model.Linija;

public class PregledRedaVoznjeController implements Initializable {

	public static String BASE_URL;
	public static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	
	public TextArea redVoznjeArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try(InputStream is=new FileInputStream(PATH_CONFIG)) {
			Properties properties=new Properties();
			properties.load(is);
			
			String urlString=properties.getProperty("BASE_URL");
			if(urlString!=null)
				BASE_URL=urlString;
			else
				throw new MissingFormatArgumentException("Url nije konfigurisan u config fajlu!");
		}
		catch(IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		
		
		Gson gson=new Gson();
		try(InputStream is=new URL(BASE_URL).openStream()){
			BufferedReader reader=new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
			String jsonString=readAll(reader);
			JSONArray listaJson=new JSONArray(jsonString);
			for(int i=0;i<listaJson.length();i++) {
				Linija linija=gson.fromJson(listaJson.getJSONObject(i).toString(), Linija.class);
				redVoznjeArea.appendText(linija.toString());
				redVoznjeArea.appendText("\n");
			}
		} catch (MalformedURLException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public String readAll(Reader reader) {
		StringBuilder sb = new StringBuilder();
		int znak;
		try {
			while ((znak = reader.read()) != -1) {
				sb.append((char) znak);
			}
		} catch (IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		return sb.toString();
	}

}
