package com.github.jesty.camera.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
/**
 * Entity implementation class for Entity: Anagrafe
 *
 */
@Entity
@TableGenerator (name = "anagrafe")
public class Anagrafe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	private String id;

	@Column (name = "data")
	private Timestamp data;
	
	@ManyToOne
	@JoinColumn (name = "luogoNascita", referencedColumnName = "id")
	private Luogo luogoDiNascita;

	public Anagrafe() {
		super();
	}   
	
	public Anagrafe(String id, Timestamp data, Luogo luogoDiNascita) {
		this.id = id;
		this.data = data;
		this.luogoDiNascita = luogoDiNascita;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	public Timestamp getData() {
		return this.data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}   
	public Luogo getLuogoDiNascita() {
		return this.luogoDiNascita;
	}

	public void setLuogoDiNascita(Luogo luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	
	@Override
	public String toString() {
		return "Anagrafe [id=" + id + ", data=" + data + ", luogoDiNascita="
				+ luogoDiNascita + "]";
	}
}
