package mdp.czsmdp.model;

import java.util.ArrayList;

public class Linija {

	private String id;
	private ArrayList<ZeljeznickaStanica> listaStanica;
	
	
	public Linija() {}
	
	
	public Linija(String id) {
		super();
		listaStanica=new ArrayList<>();
		this.id=id;
	}


	public static Linija getLinija(String strLinija) {
		String[] podaci=strLinija.split("#");
		Linija linija=new Linija(podaci[0]);
		String[] stanice=podaci[1].split("-");
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
	
	public boolean sadrziStanicu(String nazivStanice) {
		for(ZeljeznickaStanica zs:listaStanica) {
			if(zs.getNaziv().equals(nazivStanice))
				return true;
		}
		return false;
	}
	
	public void azurirajVrijeme(ZeljeznickaStanica stanica) {
		for(ZeljeznickaStanica zs:listaStanica) {
			if(zs.getNaziv().equals(stanica.getNaziv()))
				zs.setStvarnoVrijeme(stanica.getStvarnoVrijeme());
		}
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
