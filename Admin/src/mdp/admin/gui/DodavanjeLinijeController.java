package mdp.admin.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import mdp.admin.logger.AdminLogger;
import mdp.admin.model.Linija;

public class DodavanjeLinijeController implements Initializable{

	public static String BASE_URL;
	public static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";

	public TextArea linijaArea;
	public Button dodajLinijuButton;

	public void dodajLinijuClick() {
		String strLinija = linijaArea.getText();
		Linija linija = Linija.getLinija(strLinija);
		//System.out.println(linija);
		Gson gson = new Gson();
		String jsonLinija = gson.toJson(linija);
		// System.out.println(jsonLinija);
		JSONObject objekat = new JSONObject(jsonLinija);
		try {
			URL url = new URL(BASE_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStream os = connection.getOutputStream();
			os.write(objekat.toString().getBytes());
			os.flush();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
				throw new RuntimeException("GRESKA:" + connection.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String odgovor;
			while ((odgovor = br.readLine()) != null) {
				linija=gson.fromJson(odgovor, Linija.class);
				System.out.println(linija);
			}

			os.close();
			br.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		} catch (IOException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try(InputStream is=new FileInputStream(PATH_CONFIG)) {
			Properties properties=new Properties();
			properties.load(is);
			String urlString=properties.getProperty("BASE_URL");
			if(urlString!=null)
				BASE_URL=urlString;
			else
				throw new MissingFormatArgumentException("Url nije konfigurosan u config fajlu!");
		}
		catch(IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		
	}
}
