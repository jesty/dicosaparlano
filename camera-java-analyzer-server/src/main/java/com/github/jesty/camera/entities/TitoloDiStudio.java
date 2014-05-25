package com.github.jesty.camera.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TitoloDiStudio
 *
 */
@Entity
@TableGenerator (name = "TitoloDiStudio")
public class TitoloDiStudio implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column (name= "descrizione")
	private String descrizione;
	@OneToOne (cascade = CascadeType.PERSIST)
	@JoinColumn (name = "categoria")
	private Categoria cat;
	
	
	public TitoloDiStudio(int id, String descrizione, Categoria cat) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.cat = cat;
	}




	public TitoloDiStudio() {
		super();
	}
   
}
