package mdp.admin.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Linija {

	private String id;
	private ArrayList<ZeljeznickaStanica> listaStanica;
	

	public Linija() {
		super();
		listaStanica=new ArrayList<>();
		setId();
	}


	public synchronized void setId() {
		File file=new File(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"BrojLinije.txt");
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			
			id=br.readLine();
			
			int broj=Integer.parseInt(id)+1;
			PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(file,false)));
			pw.println(broj);
			
			pw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Linija getLinija(String strLinija) {
		Linija linija=new Linija();
		String[] stanice=strLinija.split("-");
		for(String s:stanice) {
			ZeljeznickaStanica zs=ZeljeznickaStanica.getStanica(s);
			linija.dodajStanicu(zs);
		}
		return linija;
	}
	
	
	public ArrayList<ZeljeznickaStanica> getListaStanica() {
		return listaStanica;
	}


	public void setListaStanica(ArrayList<ZeljeznickaStanica> listaStanica) {
		this.listaStanica = listaStanica;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public void dodajStanicu(ZeljeznickaStanica stanica) {
		listaStanica.add(stanica);
	}
	
	
	@Override
	public String toString() {
		String linijaString=id+"#";
		for(ZeljeznickaStanica zs:listaStanica) {
			linijaString+=zs.toString();
			linijaString+="-";
		}
		linijaString=linijaString.substring(0, linijaString.length()-1);
		return linijaString;
	}
	
}
