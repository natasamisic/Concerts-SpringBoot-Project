package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the karta database table.
 * 
 */
@Entity
@NamedQuery(name="Karta.findAll", query="SELECT k FROM Karta k")
public class Karta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKarta;

	private Timestamp datumRezervacije;

	//bi-directional many-to-one association to Koncert
	@ManyToOne
	private Koncert koncert;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	public Karta() {
	}

	public int getIdKarta() {
		return this.idKarta;
	}

	public void setIdKarta(int idKarta) {
		this.idKarta = idKarta;
	}

	public Timestamp getDatumRezervacije() {
		return this.datumRezervacije;
	}

	public void setDatumRezervacije(Timestamp datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

	public Koncert getKoncert() {
		return this.koncert;
	}

	public void setKoncert(Koncert koncert) {
		this.koncert = koncert;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

}