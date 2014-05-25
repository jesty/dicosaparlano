package com.github.jesty.camera.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entity implementation class for Entity: Documento
 *
 */
@Entity
@TableGenerator (name="Documento")
public class Documento implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String title;
	private String description;
	private String image;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name="doc_uri", length=1000)
	private String uri;
	
	@Column (name ="text")
	@Lob
	private String text;
	
	@ManyToMany (cascade = CascadeType.MERGE)
	@JoinColumn (name = "categorie")
	private List<Categoria> categories;
	
	@Column (name = "data")
	private Timestamp date;
	
	@ManyToMany
	@JoinColumn (name = "parlamentari")
	private List<Parlamentare> parlamentari;
	
	private static final long serialVersionUID = 1L;

	public Documento() {
		super();
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public Documento(String uri, String text, List<Categoria> categories,
			Timestamp date, List<Parlamentare> parlamentari) {
		super();
		this.uri = uri;
		this.text = text;
		this.categories = categories;
		this.date = date;
		this.parlamentari = parlamentari;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@JsonIgnore
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Categoria> getCategories() {
		return categories;
	}

	public void setCategories(List<Categoria> categories) {
		this.categories = categories;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<Parlamentare> getParlamentari() {
		return parlamentari;
	}

	public void setParlamentari(List<Parlamentare> parlamentari) {
		this.parlamentari = parlamentari;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", title=" + title + ", description="
				+ description + ", image=" + image + ", uri=" + uri + ", text="
				+ (text != null ? text.length() : "NO TEXT") + ", categories=" + categories + ", date=" + date
				+ ", parlamentari=" + parlamentari + "]";
	}

	
	
	
   
}
