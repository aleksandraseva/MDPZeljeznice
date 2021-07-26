package mdp.lokacija.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class LokacijaPort {

	
	public void unesiPortove() {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			jedis.set("Banjaluka:port","9000");
			jedis.set("Doboj:port","9001");
			jedis.set("Prijedor:port","9002");
			
		}
		jPool.close();
	}
	
	public String getPort(String lokacija) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			String port=jedis.get(lokacija+":port");
			jPool.close();
			return(port);
		}
	}
	
	public String getKorisnik(String lokacija) {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			String port=jedis.get(lokacija+":online");
			jPool.close();
			return(port);
		}
	}
	
	public void setOnlineKorisnike() {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			jedis.set("Banjaluka:online","");
			jedis.set("Doboj:online","");
			jedis.set("Prijedor:online","");
			jPool.close();
		}
	}
	
	public long obrisati(String lokacija) {
		JedisPool jPool=new JedisPool("localhost");
		long broj;
		try(Jedis jedis=jPool.getResource()){
			
			broj=jedis.del(lokacija+":port");
		}
		jPool.close();
		return broj;
	}
	
	public void obrisiSve() {
		JedisPool jPool=new JedisPool("localhost");
		try(Jedis jedis=jPool.getResource()){
			
			jedis.flushDB();
			jPool.close();
		}
	}
	
	public static void main(String... args) {
		
		LokacijaPort lp=new LokacijaPort();
		//lp.obrisiSve();
		lp.unesiPortove();
		lp.setOnlineKorisnike();
		System.out.println(lp.getPort("Doboj"));
		System.out.println(lp.getKorisnik("Banjaluka"));
		//System.out.println(lp.obrisati("Trebinje"));
		//System.out.println(lp.getKorisnik("Banjaluka"));
		//System.out.println(lp.getKorisnik("Doboj"));
		//System.out.println(lp.getKorisnik("Prijedor"));
		
	}
}
