package mdp.czsmdp.model;

public class ZeljeznickaStanica {

	private String naziv;
	private String planiranoVrijeme;
	private String stvarnoVrijeme;

	public ZeljeznickaStanica() {
		super();
	}

	public ZeljeznickaStanica(String naziv, String planiranoVrijeme, String stvarnoVrijeme) {
		super();
		this.naziv = naziv;
		this.planiranoVrijeme = planiranoVrijeme;
		this.stvarnoVrijeme = stvarnoVrijeme;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPlaniranoVrijeme() {
		return planiranoVrijeme;
	}

	public void setPlaniranoVrijeme(String planiranoVrijeme) {
		this.planiranoVrijeme = planiranoVrijeme;
	}

	public String getStvarnoVrijeme() {
		return stvarnoVrijeme;
	}

	public void setStvarnoVrijeme(String stvarnoVrijeme) {
		this.stvarnoVrijeme = stvarnoVrijeme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZeljeznickaStanica other = (ZeljeznickaStanica) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (stvarnoVrijeme != "")
			return naziv + "(" + planiranoVrijeme + "," + stvarnoVrijeme + ")";
		else
			return naziv + "(" + planiranoVrijeme + ")";
	}

	public static ZeljeznickaStanica getStanica(String strStanica) {
		String naziv = strStanica.split("\\(")[0];
		String[] vremena = strStanica.split("\\(")[1].substring(0, strStanica.split("\\(")[1].length()-1).split(",");
		if (vremena.length == 2)
			return new ZeljeznickaStanica(naziv, vremena[0], vremena[1]);
		else
			return new ZeljeznickaStanica(naziv, vremena[0],"");
	}

}
