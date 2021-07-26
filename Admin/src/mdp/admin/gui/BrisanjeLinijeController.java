package mdp.admin.gui;

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
import java.nio.charset.Charset;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import mdp.admin.logger.AdminLogger;
import mdp.admin.model.Linija;

public class BrisanjeLinijeController implements Initializable{
	
	public static String BASE_URL;
	private static final String PATH_CONFIG=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"config.properties";
	
	public ComboBox<String> linijeBox;
	public Button obrisiButton;
	
	
	public void obrisiClick() {
		String linijaString=linijeBox.getValue();
		String id=linijaString.split("#")[0];
		try {
			URL url=new URL(BASE_URL+id);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("DELETE");
			OutputStream os=connection.getOutputStream();
			if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
				throw new RuntimeException("GRESKA:"+connection.getResponseCode());
			
			BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String odgovor=br.readLine();
			
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setContentText(odgovor);
			alert.showAndWait();
			
			os.close();
			connection.disconnect();
		}
		catch(Exception ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
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
				throw new MissingFormatArgumentException("Url nije kofigurisan u config fajlu!");
		}
		catch(IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		
		
		ArrayList<String> listaLinija=new ArrayList<String>();
		Gson gson=new Gson();
		try {
			InputStream is=new URL(BASE_URL).openStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
			String jsonString=readAll(br);
			JSONArray listaJson=new JSONArray(jsonString);
			for(int i=0;i<listaJson.length();i++) {
				JSONObject objekat=listaJson.getJSONObject(i);
				listaLinija.add(gson.fromJson(objekat.toString(), Linija.class).toString());
			}
			is.close();
		}
		catch(MalformedURLException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		catch(IOException ex) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, ex.getMessage());
		}
		ObservableList<String> listaBox=FXCollections.observableList(listaLinija);
		linijeBox.setItems(listaBox);
	}
	
	private String readAll(Reader reader) {
		StringBuilder sb=new StringBuilder();
		int znak;
		try {
			while((znak=reader.read())!=-1)
				sb.append((char) znak);
		} catch (IOException e) {
			AdminLogger.getInstance();
			AdminLogger.logger.log(Level.SEVERE, e.getMessage());
		}
		return sb.toString();
	}

}
