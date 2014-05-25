package com.github.jesty.camera.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator (name = "Luogo")
public class Luogo implements Serializable {

	@Id 
	private String id;

	@Column (name = "latitudine")
	private double latitudine;
	@Column (name = "longitudine")
	private double longitudine;
	@Column (name = "regione")
	private String regione;
	@Column (name = "citta")
	private String citta;
	@Column (name = "provincia")
	private String provincia;
	@Column (name = "nazione")
	private String nazione;
	private static final long serialVersionUID = 1L;

	   
	public Luogo(String id, double latitudine, double longitudine, String regione,
			String citta, String provincia, String nazione) {
		super();
		this.id = id;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.regione = regione;
		this.citta = citta;
		this.provincia = provincia;
		this.nazione = nazione;
	}

	public Luogo() {
		super();
	}   
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	public double getLatitudine() {
		return this.latitudine;
	}

	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}   
	public double getLongitude() {
		return this.longitudine;
	}

	public void setLongitude(double longitudine) {
		this.longitudine = longitudine;
	}   
	public String getRegione() {
		return this.regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}   
	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}   
	public String getNazione() {
		return this.nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	@Override
	public String toString() {
		return "Luogo [id=" + id + ", latitudine=" + latitudine
				+ ", longitudine=" + longitudine + ", regione=" + regione
				+ ", citta=" + citta + ", provincia=" + provincia
				+ ", nazione=" + nazione + "]";
	}
   
	
	
}
