package mdp.azsmdp.model;

import java.io.Serializable;

public class FilePodaci implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KorisnikFile korisnik;
	private String fileName;
	private String vrijeme;
	private long velicina;
	
	public FilePodaci() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FilePodaci(KorisnikFile korisnik, String fileName, String vrijeme, long velicina) {
		super();
		this.korisnik = korisnik;
		this.fileName = fileName;
		this.vrijeme = vrijeme;
		this.velicina = velicina;
	}

	public KorisnikFile getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(KorisnikFile korisnik) {
		this.korisnik = korisnik;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVrijeme() {
		return vrijeme;
	}

	public void setVrijeme(String vrijeme) {
		this.vrijeme = vrijeme;
	}

	public long getVelicina() {
		return velicina;
	}

	public void setVelicina(long velicina) {
		this.velicina = velicina;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((korisnik == null) ? 0 : korisnik.hashCode());
		result = prime * result + (int) (velicina ^ (velicina >>> 32));
		result = prime * result + ((vrijeme == null) ? 0 : vrijeme.hashCode());
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
		FilePodaci other = (FilePodaci) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (korisnik == null) {
			if (other.korisnik != null)
				return false;
		} else if (!korisnik.equals(other.korisnik))
			return false;
		if (velicina != other.velicina)
			return false;
		if (vrijeme == null) {
			if (other.vrijeme != null)
				return false;
		} else if (!vrijeme.equals(other.vrijeme))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PodaciFile \n korisnik=" + korisnik + "\n fileName=" + fileName + "\n vrijeme=" + vrijeme + "\n velicina="
				+ velicina;
	}
	
	

}
