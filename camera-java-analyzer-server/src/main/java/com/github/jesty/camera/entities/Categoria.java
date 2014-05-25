package com.github.jesty.camera.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entity implementation class for Entity: Categoria
 *
 */
@Entity
@TableGenerator (name = "Categoria")
public class Categoria implements Serializable {

	@Id
	@Column (name = "categoria")
	private String categoria;
	@Column (name = "categoriaPadre")
	private String catPadre;
	
	private static final long serialVersionUID = 1L;
	
	
	public Categoria(int id, String categoria, String catPadre) {
		this.categoria = categoria;
		this.catPadre = catPadre;
	}


	public Categoria() {
		super();
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	@JsonIgnore
	public String getCatPadre() {
		return catPadre;
	}


	public void setCatPadre(String catPadre) {
		this.catPadre = catPadre;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((catPadre == null) ? 0 : catPadre.hashCode());
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
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
		Categoria other = (Categoria) obj;
		if (catPadre == null) {
			if (other.catPadre != null)
				return false;
		} else if (!catPadre.equals(other.catPadre))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Categoria [categoria=" + categoria + ", catPadre=" + catPadre
				+ "]";
	}
	
	
	
}
