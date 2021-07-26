package mdp.zsmdp.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import mdp.zsmdp.logger.ZSMDPLogger;
import mdp.zsmdp.model.Linija;
import mdp.zsmdp.model.ZeljeznickaStanica;

public class EvidentiranjeVozaController implements Initializable {

	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	public static String BASE_URL;

	public ComboBox<String> linijeBox;
	public Button evidentirajButton;
	
	public void evidentirajClick() {
		String nazivStanice=MainWindowController.korisnik.getLokacija();
		Linija linija=Linija.getLinija(linijeBox.getValue());
		String idLinije=linija.getId();
		try {
			URL url=new URL(BASE_URL+idLinije);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			
			ZeljeznickaStanica stanica=linija.dohvatiStanicu(nazivStanice);
			stanica.setStvarnoVrijeme(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
			Gson gson=new Gson();
			String stanicaJsonString=gson.toJson(stanica);
			JSONObject stanicaJson=new JSONObject(stanicaJsonString);
			
			OutputStream os=connection.getOutputStream();
			os.write(stanicaJson.toString().getBytes());
			os.flush();
			
			if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
				throw new RuntimeException("GRESKA:"+connection.getResponseCode());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String odgovor=br.readLine();
			
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setContentText(odgovor);
			alert.showAndWait();
			
		} catch (MalformedURLException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		} catch (IOException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
	}

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
		
		ArrayList<String> listaLinija = new ArrayList<String>();
		String nazivStanice = MainWindowController.korisnik.getLokacija();
		try (InputStream is = new URL(BASE_URL + nazivStanice).openStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String json = readAll(br);
			JSONArray listaLinijaJson = new JSONArray(json);
			Gson gson = new Gson();
			for (int i = 0; i < listaLinijaJson.length(); i++) {
				listaLinija.add(gson.fromJson(listaLinijaJson.getJSONObject(i).toString(), Linija.class).toString());
			}
		} catch (MalformedURLException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		} catch (IOException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
		ObservableList<String> linije = FXCollections.observableList(listaLinija);
		linijeBox.setItems(linije);
	}

	public String readAll(Reader reader) {
		
		StringBuilder sb = new StringBuilder();
		int znak;
		try {
			while ((znak = reader.read()) != -1)
				sb.append((char) znak);
		} catch (IOException e) {
			ZSMDPLogger.getInstance();
			ZSMDPLogger.logger.log(Level.SEVERE,e.getMessage());
		}
		return sb.toString();
	}

}
