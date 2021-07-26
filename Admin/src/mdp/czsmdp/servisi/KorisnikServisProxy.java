package mdp.czsmdp.servisi;

public class KorisnikServisProxy implements mdp.czsmdp.servisi.KorisnikServis {
  private String _endpoint = null;
  private mdp.czsmdp.servisi.KorisnikServis korisnikServis = null;
  
  public KorisnikServisProxy() {
    _initKorisnikServisProxy();
  }
  
  public KorisnikServisProxy(String endpoint) {
    _endpoint = endpoint;
    _initKorisnikServisProxy();
  }
  
  private void _initKorisnikServisProxy() {
    try {
      korisnikServis = (new mdp.czsmdp.servisi.KorisnikServisServiceLocator()).getKorisnikServis();
      if (korisnikServis != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)korisnikServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)korisnikServis)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (korisnikServis != null)
      ((javax.xml.rpc.Stub)korisnikServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mdp.czsmdp.servisi.KorisnikServis getKorisnikServis() {
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis;
  }
  
  public java.lang.String getPort(java.lang.String lokacija) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.getPort(lokacija);
  }
  
  public boolean registrovanjeKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.registrovanjeKorisnika(korisnik);
  }
  
  public boolean provjeraKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.provjeraKorisnika(korisnik);
  }
  
  public java.lang.String getOnlineKorisnika(java.lang.String lokacija) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.getOnlineKorisnika(lokacija);
  }
  
  public boolean brisanjeKorisnika(java.lang.String lokacija, java.lang.String username) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.brisanjeKorisnika(lokacija, username);
  }
  
  public mdp.czsmdp.model.Korisnik[] dohvatiKorisnike(java.lang.String lokacija) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    return korisnikServis.dohvatiKorisnike(lokacija);
  }
  
  public void odjavaKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException{
    if (korisnikServis == null)
      _initKorisnikServisProxy();
    korisnikServis.odjavaKorisnika(korisnik);
  }
  
  
}