package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the koncert database table.
 * 
 */
@Entity
@NamedQuery(name="Koncert.findAll", query="SELECT k FROM Koncert k")
public class Koncert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKoncert;

	private String adresa;

	private String grad;

	private String izvodjac;

	private int maxBrojKarata;

	private String opis;

	private int trajanje;
	
	private double cena;
	
	@Temporal(TemporalType.DATE)
	private Date datumIzvodjenja; 

	//bi-directional many-to-one association to Karta
	@OneToMany(mappedBy="koncert")
	private List<Karta> kartas;

	//bi-directional many-to-one association to Komentar
	@OneToMany(mappedBy="koncert")
	private List<Komentar> komentars;

	//bi-directional many-to-one association to Zanr
	@ManyToOne
	private Zanr zanr;

	//bi-directional many-to-one association to Omiljenikoncerti
	@OneToMany(mappedBy="koncert")
	private List<Omiljenikoncerti> omiljenikoncertis;

	//bi-directional many-to-one association to Slika
	@OneToMany(mappedBy="koncert")
	private List<Slika> slikas;

	public Koncert() {
	}

	public int getIdKoncert() {
		return this.idKoncert;
	}

	public void setIdKoncert(int idKoncert) {
		this.idKoncert = idKoncert;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return this.grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getIzvodjac() {
		return this.izvodjac;
	}

	public void setIzvodjac(String izvodjac) {
		this.izvodjac = izvodjac;
	}

	public int getMaxBrojKarata() {
		return this.maxBrojKarata;
	}

	public void setMaxBrojKarata(int maxBrojKarata) {
		this.maxBrojKarata = maxBrojKarata;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getTrajanje() {
		return this.trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	
	public double getCena() {
		return this.cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Date getDatumIzvodjenja() {
		return this.datumIzvodjenja;
	}

	public void setDatumIzvodjenja(Date datumIzvodjenja) {
		this.datumIzvodjenja = datumIzvodjenja;
	}

	public List<Karta> getKartas() {
		return this.kartas;
	}

	public void setKartas(List<Karta> kartas) {
		this.kartas = kartas;
	}

	public Karta addKarta(Karta karta) {
		getKartas().add(karta);
		karta.setKoncert(this);

		return karta;
	}

	public Karta removeKarta(Karta karta) {
		getKartas().remove(karta);
		karta.setKoncert(null);

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
		komentar.setKoncert(this);

		return komentar;
	}

	public Komentar removeKomentar(Komentar komentar) {
		getKomentars().remove(komentar);
		komentar.setKoncert(null);

		return komentar;
	}

	public Zanr getZanr() {
		return this.zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}

	public List<Omiljenikoncerti> getOmiljenikoncertis() {
		return this.omiljenikoncertis;
	}

	public void setOmiljenikoncertis(List<Omiljenikoncerti> omiljenikoncertis) {
		this.omiljenikoncertis = omiljenikoncertis;
	}

	public Omiljenikoncerti addOmiljenikoncerti(Omiljenikoncerti omiljenikoncerti) {
		getOmiljenikoncertis().add(omiljenikoncerti);
		omiljenikoncerti.setKoncert(this);

		return omiljenikoncerti;
	}

	public Omiljenikoncerti removeOmiljenikoncerti(Omiljenikoncerti omiljenikoncerti) {
		getOmiljenikoncertis().remove(omiljenikoncerti);
		omiljenikoncerti.setKoncert(null);

		return omiljenikoncerti;
	}

	public List<Slika> getSlikas() {
		return this.slikas;
	}

	public void setSlikas(List<Slika> slikas) {
		this.slikas = slikas;
	}

	public Slika addSlika(Slika slika) {
		getSlikas().add(slika);
		slika.setKoncert(this);

		return slika;
	}

	public Slika removeSlika(Slika slika) {
		getSlikas().remove(slika);
		slika.setKoncert(null);

		return slika;
	}

}