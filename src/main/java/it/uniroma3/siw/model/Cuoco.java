package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuoco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	private LocalDate dataDiNascita;
	@Column(nullable = false)
	private List<String> urlsImages;
	
	@OneToMany(mappedBy = "cuoco", cascade = CascadeType.ALL, orphanRemoval = true)  
	private List<Ricetta> ricette;
	
	/* getter e setter */
	public List<Ricetta> getRicette() {
		return ricette;
	}
	public void setRicette(List<Ricetta> ricette) {
		this.ricette = ricette;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public List<String> getUrlsImages() {
		return urlsImages;
	}
	
	public void setUrlsImages(List<String> urlsImages) {
		this.urlsImages = urlsImages;
	} 
}