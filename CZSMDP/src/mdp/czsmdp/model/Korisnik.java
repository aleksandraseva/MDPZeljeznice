package mdp.czsmdp.model;

public class Korisnik {

	private String lokacija;
	private String username;
	private String passwordHash;
	
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Korisnik(String lokacija, String username, String passwordHash) {
		super();
		this.lokacija = lokacija;
		this.username = username;
		this.passwordHash = passwordHash;
	}


	public String getLokacija() {
		return lokacija;
	}


	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPasswordHash() {
		return passwordHash;
	}


	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
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
		Korisnik other = (Korisnik) obj;
		if (passwordHash == null) {
			if (other.passwordHash != null)
				return false;
		} else if (!passwordHash.equals(other.passwordHash))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Korisnik [lokacija=" + lokacija + ", username=" + username + ", passwordHash=" + passwordHash + "]";
	}
	
	
}
