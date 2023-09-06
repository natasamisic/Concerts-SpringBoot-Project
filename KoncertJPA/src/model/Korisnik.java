package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKorisnik;

	private String email;

	private String ime;

	private String password;

	private String prezime;

	private String username;

	//bi-directional many-to-one association to Karta
	@OneToMany(mappedBy="korisnik")
	private List<Karta> kartas;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="korisnik")
	private List<Komentar> komentars;

	//bi-directional many-to-one association to Uloga
	@ManyToOne
	private Uloga uloga;

	//bi-directional many-to-one association to Omiljenikoncerti
	@OneToMany(mappedBy="korisnik")
	private List<Omiljenikoncerti> omiljenikoncertis;

	public Korisnik() {
	}

	public int getIdKorisnik() {
		return this.idKorisnik;
	}

	public void setIdKorisnik(int idKorisnik) {
		this.idKorisnik = idKorisnik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrezime() {
		return this.prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Karta> getKartas() {
		return this.kartas;
	}

	public void setKartas(List<Karta> kartas) {
		this.kartas = kartas;
	}

	public Karta addKarta(Karta karta) {
		getKartas().add(karta);
		karta.setKorisnik(this);

		return karta;
	}

	public Karta removeKarta(Karta karta) {
		getKartas().remove(karta);
		karta.setKorisnik(null);

		return karta;
	}

	public List<Komentar> getKomentars() {
		return this.komentars;
	}

	public void setKomentars(List<Komentar> komentars) {
		this.komentars = komentars;
	}

	public Komentar addKomentar(Komentar komentar) {
		getKomentars().add(komentar);
		komentar.setKorisnik(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setKorisnik(null);

		return komentar;
	}

	public Uloga getUloga() {
		return this.uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public List<Omiljenikoncerti> getOmiljenikoncertis() {
		return this.omiljenikoncertis;
	}

	public void setOmiljenikoncertis(List<Omiljenikoncerti> omiljenikoncertis) {
		this.omiljenikoncertis = omiljenikoncertis;
	}

	public Omiljenikoncerti addOmiljenikoncerti(Omiljenikoncerti omiljenikoncerti) {
		getOmiljenikoncertis().add(omiljenikoncerti);
		omiljenikoncerti.setKorisnik(this);

		return omiljenikoncerti;
	}

	public Omiljenikoncerti removeOmiljenikoncerti(Omiljenikoncerti omiljenikoncerti) {
		getOmiljenikoncertis().remove(omiljenikoncerti);
		omiljenikoncerti.setKorisnik(null);

		return omiljenikoncerti;
	}

}