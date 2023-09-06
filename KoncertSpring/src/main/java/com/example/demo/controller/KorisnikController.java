package com.example.demo.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KartaRepository;
import com.example.demo.repository.KomentarRepository;
import com.example.demo.repository.KoncertRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.OmiljeniKoncertRepository;
import com.example.demo.repository.SlikaRepository;
import com.example.demo.repository.UlogaRepository;
import com.example.demo.repository.ZanrRepository;

import model.Karta;
import model.Komentar;
import model.Koncert;
import model.Korisnik;
import model.Omiljenikoncerti;
import model.Slika;
import model.Zanr;

@Controller
@RequestMapping(value="/user")
public class KorisnikController {
	@Autowired
	KorisnikRepository korr;
	@Autowired
	KoncertRepository kr;
	@Autowired
	KartaRepository karr;
	@Autowired
	UlogaRepository ur;
	@Autowired
	ZanrRepository zr;
	@Autowired
	OmiljeniKoncertRepository okr;
	@Autowired
	KomentarRepository komr;
	@Autowired
	SlikaRepository sr;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value="nadjiKoncert", method=RequestMethod.GET)
	public String nadjiKoncert(String izvodjac, HttpServletRequest request) {
		Koncert k = kr.findByIzvodjac(izvodjac);
		if(k!=null) {
			List<Komentar> komentari = komr.findByKoncert(k);
			request.setAttribute("komentari", komentari);
			request.getSession().setAttribute("Kkoncert", k);
			List<Slika> slike = sr.findByKoncert(k);
			request.setAttribute("slike", slike);
			return "korisnik/prikazKoncerta";
		}else {
			request.setAttribute("poruka", "Koncert sa traženim izvođačem ne postoji.");
			return "korisnik/pretragaKoncerta";
		}
	}
	
	@RequestMapping(value="getZanroveZaPretragu", method=RequestMethod.GET)
	public String getZanroveZaPretragu(HttpServletRequest request) {
		List<Zanr> zanrovi = zr.findAll();
		request.getSession().setAttribute("zanrovi", zanrovi);
		return "korisnik/pretragaPoZanru";
	}
	
	@RequestMapping(value="getKoncertePoZanru", method=RequestMethod.GET)
	public String getKoncertePoZanru(Integer zanr, HttpServletRequest request) {
		Zanr z = zr.findById(zanr).get();
		List<Koncert> koncerti = kr.findByZanr(z);
		request.setAttribute("koncerti", koncerti);
		return "korisnik/pretragaPoZanru";
	}
	
	@RequestMapping(value="rezervisiKartu", method=RequestMethod.POST)
	public String rezervisiKartu(HttpServletRequest request) {
		Koncert kon = (Koncert) request.getSession().getAttribute("koncert");
//		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik kor = korr.findByUsername(user);
		Karta k = new Karta();
		k.setKoncert(kon);
		k.setKorisnik(kor);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		k.setDatumRezervacije(time);
		Karta karta = karr.save(k);
		if(karta!=null) {
			request.setAttribute("poruka", "Karta je uspešno rezervisana.");
		}else {
			request.setAttribute("poruka", "Došlo je do greške! Karta nije rezervisana.");
		}
		return "korisnik/pretragaKoncerta";
	}
	
	@RequestMapping(value="getKoncerti", method=RequestMethod.GET)
	public String getKoncerti(HttpServletRequest request) {
		List<Koncert> koncerti = kr.findAll();
		request.setAttribute("koncerti", koncerti);
		return "korisnik/prikazSvihKoncerata";
	}
	
	@RequestMapping(value="getRezervisaneKarte", method=RequestMethod.GET)
	public String getRezervisaneKarte(HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Karta> karte = karr.rezervisaneKarte(k.getIdKorisnik());
		if(karte.size()>0) {
			request.setAttribute("karte", karte);
		}else {
			request.setAttribute("poruka", "Nemate rezervisanih karata.");
		}
		return "korisnik/rezervisaneKarte";
	}
	
	@RequestMapping(value="prikaziKoncert", method=RequestMethod.GET)
	public String prikaziKoncert(Integer idK, HttpServletRequest request) {
		Koncert k = kr.findById(idK).get();		
		request.getSession().setAttribute("Kkoncert", k);
		List<Komentar> komentari = komr.findByKoncert(k);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(k);
		request.setAttribute("slike", slike);
		return "korisnik/prikazKoncerta";
	}
	
	@RequestMapping(value="dodajKomentar", method=RequestMethod.POST)
	public String dodajKomentar(String komentar, HttpServletRequest request) {
		Koncert kon = (Koncert) request.getSession().getAttribute("Kkoncert");
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		Komentar komm = new Komentar();
		komm.setTekst(komentar);
		komm.setKoncert(kon);
		komm.setKorisnik(k);
		komr.save(komm);
		List<Komentar> komentari = komr.findByKoncert(kon);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(kon);
		request.setAttribute("slike", slike);
		return "korisnik/prikazKoncerta";
	}
	
	@RequestMapping(value="rezervacija", method=RequestMethod.POST)
	public String rezervacija(HttpServletRequest request) {
		Koncert kon = (Koncert) request.getSession().getAttribute("Kkoncert");
//		Korisnik kor = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik kor = korr.findByUsername(user);
		Karta k = new Karta();
		k.setKoncert(kon);
		k.setKorisnik(kor);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		k.setDatumRezervacije(time);
		Karta karta = karr.save(k);
		if(karta!=null) {
			request.setAttribute("rezervacijaPoruka", "Karta je uspešno rezervisana.");
		}else {
			request.setAttribute("rezervacijaPoruka", "Došlo je do greške! Karta nije rezervisana.");
		}
		List<Komentar> komentari = komr.findByKoncert(kon);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(kon);
		request.setAttribute("slike", slike);
		return "korisnik/prikazKoncerta";
	}
	
	@RequestMapping(value="otkaziRezervaciju", method=RequestMethod.POST)
	public String otkaziRezervaciju(Integer idK, HttpServletRequest request) {
		karr.deleteById(idK);
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Karta> karte = karr.rezervisaneKarte(k.getIdKorisnik());
		if(karte.size()>0) {
			request.setAttribute("karte", karte);
		}
		request.setAttribute("poruka", "Rezervacija je otkazana.");
		return "korisnik/rezervisaneKarte";
	}
	
	@RequestMapping(value="dodajUListuOmiljenih", method=RequestMethod.POST)
	public String dodajUListuOmiljenih(HttpServletRequest request) {
		Koncert koncert = (Koncert) request.getSession().getAttribute("Kkoncert");
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		Omiljenikoncerti postoji = okr.findByKoncertAndKorisnik(koncert, k);
		if(postoji != null) {
			request.setAttribute("koncertUListi", "Koncert je već u listi omiljenih.");
			List<Komentar> komentari = komr.findByKoncert(koncert);
			request.setAttribute("komentari", komentari);
			List<Slika> slike = sr.findByKoncert(koncert);
			request.setAttribute("slike", slike);
			return "korisnik/prikazKoncerta";
		}	
		Omiljenikoncerti omiljeni = new Omiljenikoncerti();
		omiljeni.setKoncert(koncert);
		omiljeni.setKorisnik(k);
		Omiljenikoncerti o = okr.save(omiljeni);
		if(o != null) {
			request.setAttribute("koncertUListi", "Koncert je dodat u listu omiljenih.");
		}else {
			request.setAttribute("koncertUListi", "Došlo je do greške. Koncert nije dodat u listu omiljenih.");
		}
		List<Komentar> komentari = komr.findByKoncert(koncert);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(koncert);
		request.setAttribute("slike", slike);
		return "korisnik/prikazKoncerta";
	}
	
	@RequestMapping(value="getListuOmiljenihKoncerata", method=RequestMethod.GET)
	public String getListuOmiljenihKoncerata(HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Omiljenikoncerti> lista = okr.findByKorisnik(k);
		if(lista.size()>0) {
			request.setAttribute("lista", lista);
		}else {
			request.setAttribute("poruka", "Nemate koncerata u listi omiljenih.");
		}
		return "korisnik/listaOmiljenihKoncerata";
	}
	
	@RequestMapping(value="izbaciIzListeOmiljenih", method=RequestMethod.POST)
	public String izbaciIzListeOmiljenih(Integer idK, HttpServletRequest request) {
		okr.deleteById(idK);
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Omiljenikoncerti> lista = okr.findByKorisnik(k);
		if(lista.size()>0) {
			request.setAttribute("lista", lista);
			request.setAttribute("poruka", "Koncert izbačen iz liste omiljenih.");
		}else {
			request.setAttribute("poruka", "Nemate koncerata u listi omiljenih.");
		}
		return "korisnik/listaOmiljenihKoncerata";
	}
	
	@RequestMapping(value="mojiKomentari", method=RequestMethod.GET)
	public String mojiKomentari(HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Komentar> komentari= komr.findByKorisnik(k);
		if(komentari.size()>0) {
			request.setAttribute("mojikomentari", komentari);
		}else {
			request.setAttribute("porukaKom", "Nemate postavljenih komentara.");
		}
		return "korisnik/mojiKomentari";
	}
	
	@RequestMapping(value="obrisiKomentar", method=RequestMethod.POST)
	public String obrisiKomentar(Integer idK, HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		komr.deleteById(idK);
		List<Komentar> komentari= komr.findByKorisnik(k);
		if(komentari.size()>0) {
			request.setAttribute("mojikomentari", komentari);
			request.setAttribute("porukaKom", "Komentar uspešno obrisan.");
		}else {
			request.setAttribute("porukaKom", "Nemate postavljenih komentara.");
		}
		return "korisnik/mojiKomentari";
	}
}	
	
