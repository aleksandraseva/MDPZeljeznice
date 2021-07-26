/**
 * KorisnikServis.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.czsmdp.servisi;

public interface KorisnikServis extends java.rmi.Remote {
    public java.lang.String getPort(java.lang.String lokacija) throws java.rmi.RemoteException;
    public boolean registrovanjeKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException;
    public boolean provjeraKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException;
    public java.lang.String getOnlineKorisnika(java.lang.String lokacija) throws java.rmi.RemoteException;
    public boolean brisanjeKorisnika(java.lang.String lokacija, java.lang.String username) throws java.rmi.RemoteException;
    public mdp.czsmdp.model.Korisnik[] dohvatiKorisnike(java.lang.String lokacija) throws java.rmi.RemoteException;
    public void odjavaKorisnika(mdp.czsmdp.model.Korisnik korisnik) throws java.rmi.RemoteException;
}
