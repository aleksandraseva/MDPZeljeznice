package mdp.czsmdp.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mdp.czsmdp.model.Linija;
import mdp.czsmdp.model.ZeljeznickaStanica;

@Path("/linije")
public class APIServis {
	
	private LinijeServis servis=new LinijeServis();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Linija> dohvatiSveLinije(){
		return servis.dohvatiSveLinije();
	}
	
	@GET
	@Path("/{stanica}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Linija> dohvatiLinijeStanica(@PathParam("stanica") String nazivStanice){
		 return servis.dohvatiLinijeStanica(nazivStanice);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajLiniju(Linija linija) {
		if(servis.dodajLiniju(linija))
			return Response.status(200).entity(linija).build();
		else
			return Response.status(500).entity("Greska").build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response evidentirajVrijeme(ZeljeznickaStanica stanica,@PathParam("id") String id) {
		if(servis.evidentirajProlazak(stanica,id))
			return Response.status(200).entity("Stvarno vrijeme je evidentirano").build();
		else
			return Response.status(404).entity("GRESKA").build(); //razmisli za kod
		
		//return Response.status(200).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response obrisiLiniju(@PathParam("id") String id) {
		if(servis.obrisiLiniju(id))
			return Response.status(200).entity("Linija je uspjesno obrisana").build();
		else
			return Response.status(404).entity("GRESKA").build();
	}
}
