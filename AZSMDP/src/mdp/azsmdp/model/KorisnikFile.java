package mdp.azsmdp.model;

import java.io.Serializable;

public class KorisnikFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String lokacija;
	
	
	public KorisnikFile() {
		super();
		// TODO Auto-generated constructor stub
	}


	public KorisnikFile(String username, String lokacija) {
		super();
		this.username = username;
		this.lokacija = lokacija;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getLokacija() {
		return lokacija;
	}


	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lokacija == null) ? 0 : lokacija.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		KorisnikFile other = (KorisnikFile) obj;
		if (lokacija == null) {
			if (other.lokacija != null)
				return false;
		} else if (!lokacija.equals(other.lokacija))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "[username=" + username + ", lokacija=" + lokacija + "]";
	}
	
	

}
