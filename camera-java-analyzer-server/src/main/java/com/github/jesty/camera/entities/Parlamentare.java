package com.github.jesty.camera.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

/**
 * Entity implementation class for Entity: Parlamentare
 *
 */
@Entity
@TableGenerator (name = "parlamentare")
public class Parlamentare implements Serializable {

	@Id 
	private String id;
	
	@Column (name = "nome")
	private String nome;
	
	@Column (name = "cognome")
	private String cognome;
	
	@Column (name = "genere")
	private String genere;
	
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "categorie")
	private List<Categoria> categories;
	
	@Column
	private String luogo;
	
	@Column
	private Timestamp dataDiNascita;
	
/*	
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "titoloDiStudio")
*/
	@Column (name = "TitoloDiStudio")
	private String titoloDiStudio;
	
	@Column (name = "collegio")
	private String collegio;
	
	@Column (name = "dataElezione")
	private Timestamp dataElezione;
	
	private List<Documento> documenti;
	
	public void setDocumenti(List<Documento> documenti) {
		this.documenti = documenti;
	}
	
	public List<Documento> getDocumenti() {
		return documenti;
	}
	
	/*
	 * Il dato è contenuto nella descrizione della sorgente
	 * potrebbe risultare difficile separare il titolo di studio dalla professione!
	 * 
	@Column (name = "professione")
	private String professione;
	*/
	private static final long serialVersionUID = 1L;

	
	public Parlamentare(String id, String nome, String cognome, String genere,
			String titoloDiStudio, String collegio,
			Timestamp dataElezione) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.genere = genere;
		this.titoloDiStudio = titoloDiStudio;
		this.collegio = collegio;
		this.dataElezione = dataElezione;
	}
	
	public Timestamp getDataDiNascita() {
		return dataDiNascita;
	}
	
	public void setDataDiNascita(Timestamp dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public Parlamentare() {
		super();
	}   
	public String getId() {
		return this.id;
	}

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getCognome() {
		return cognome;
	}



	public void setCognome(String cognome) {
		this.cognome = cognome;
	}



	public String getGenere() {
		return genere;
	}



	public void setGenere(String genere) {
		this.genere = genere;
	}



	public Timestamp getDataElezione() {
		return dataElezione;
	}



	public void setDataElezione(Timestamp dataElezione) {
		this.dataElezione = dataElezione;
	}


	public void setId(String id) {
		this.id = id;
	}   
	public String getTitoloDiStudio() {
		return this.titoloDiStudio;
	}

	public void setTitoloDiStudio(String titoloDiStudio) {
		this.titoloDiStudio = titoloDiStudio;
	}   
	public String getCollegio() {
		return this.collegio;
	}

	public void setCollegio(String collegio) {
		this.collegio = collegio;
	}
	
	public List<Categoria> getCategories() {
		return categories;
	}
	
	public void setCategories(List<Categoria> categories) {
		this.categories = categories;
	}
	
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	@Override
	public String toString() {
		return "Parlamentare [id=" + id + ", nome=" + nome + ", cognome="
				+ cognome + ", genere=" + genere 
				+ ", titoloDiStudio=" + titoloDiStudio + ", collegio="
				+ collegio + ", dataElezione=" + dataElezione + "]";
	}
   
	
	
}
