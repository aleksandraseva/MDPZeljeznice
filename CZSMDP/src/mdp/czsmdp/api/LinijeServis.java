package mdp.czsmdp.api;

import java.util.ArrayList;

import mdp.czsmdp.model.Linija;
import mdp.czsmdp.model.ZeljeznickaStanica;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class LinijeServis {

	public ArrayList<Linija> dohvatiSveLinije() {
		ArrayList<Linija> sveLinije = new ArrayList<>();
		JedisPool pool = new JedisPool("localhost");
		Jedis jedis = pool.getResource();
		ArrayList<String> kljucevi = new ArrayList<>(jedis.keys("linija:*"));
		for (int i = 0; i < kljucevi.size(); i++) {
			sveLinije.add(Linija.getLinija(jedis.get(kljucevi.get(i))));
		}
		jedis.close();
		pool.close();
		return sveLinije;
	}

	public ArrayList<Linija> dohvatiLinijeStanica(String nazivStanice) {
		ArrayList<Linija> sveLinije = dohvatiSveLinije();
		ArrayList<Linija> stanicaLinije = new ArrayList<Linija>();
		for (Linija linija : sveLinije) {
			if (linija.sadrziStanicu(nazivStanice))
				stanicaLinije.add(linija);
		}
		return stanicaLinije;
	}

	public boolean dodajLiniju(Linija linija) {
		JedisPool pool = new JedisPool("localhost");
		String izvrseno = null;
		Jedis jedis = pool.getResource();
		izvrseno = jedis.set("linija:" + linija.getId(), linija.toString());
		jedis.close();
		pool.close();
		if (izvrseno != null)
			return true;
		else
			return false;
	}

	public boolean evidentirajProlazak(ZeljeznickaStanica stanica,String idLinije) {
		JedisPool pool = new JedisPool("localhost");
		String izvrseno = null;
		Jedis jedis = pool.getResource();
		ArrayList<Linija> sveLinije = dohvatiSveLinije();
		for (Linija linija : sveLinije) {
			if (linija.getId().equals(idLinije)) {
				//System.out.println(linija);
				linija.azurirajVrijeme(stanica);
				izvrseno = jedis.set("linija:" + linija.getId(), linija.toString());
			}
		}
		jedis.close();
		pool.close();
		if (izvrseno != null)
			return true;
		else
			return false;
	}

	public boolean obrisiLiniju(String id) {
		JedisPool pool = new JedisPool("localhost");
		long broj = 0;
		Jedis jedis = pool.getResource();
		broj = jedis.del("linija:" + id);
		jedis.close();
		pool.close();
		if (broj != 0) // ako ne postoji treba da vrati 0
			return true;
		else
			return false;
	}
}
