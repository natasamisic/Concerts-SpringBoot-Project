package com.example.demo.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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
import model.Uloga;
import model.Zanr;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/worker")
public class RadnikController {
	
	@Autowired
	KoncertRepository kr;
	@Autowired
	ZanrRepository zr;
	@Autowired
	KorisnikRepository korr;
	@Autowired
	UlogaRepository ur;
	@Autowired
	KartaRepository karr;
	@Autowired
	KomentarRepository komr;
	@Autowired
	OmiljeniKoncertRepository okr;
	@Autowired
	SlikaRepository sr;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping(value="prikaziKoncert", method=RequestMethod.GET)
	public String prikaziKoncert(Integer idK, HttpServletRequest request) {
		Koncert k = kr.findById(idK).get();		
		request.getSession().setAttribute("Kkoncert", k);
		List<Komentar> komentari = komr.findByKoncert(k);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(k);
		request.setAttribute("slike", slike);
		return "radnik/prikazKoncerta";
	}
	
	@ModelAttribute("koncert")
	public Koncert noviKoncert() {
		return new Koncert();
	}
	
	@RequestMapping(value="dodajKoncert", method=RequestMethod.POST)
	public String dodajKoncert(@ModelAttribute("koncert")Koncert k, HttpServletRequest request) {
		Koncert koncert = kr.save(k);
		if(koncert!=null) {
			request.setAttribute("poruka", "Koncert je uspešno sačuvan!");
			request.setAttribute("noviKoncert",koncert);
		}else {
			request.setAttribute("poruka", "Došlo je do greške!");
		}
		return "radnik/dodajKoncert";
	}
	
	@RequestMapping(value="saveSlika", method=RequestMethod.POST)
	public String saveSlika(MultipartFile file, HttpServletRequest request) {
		Koncert k = (Koncert) request.getSession().getAttribute("Kkoncert");
		if(file != null) {
			String fileName = file.getOriginalFilename();
			try {
				String filePath = System.getProperty("user.dir");
				File imageFile = new File(filePath, fileName);	
				file.transferTo(imageFile);
				Slika slika = new Slika();
				slika.setKoncert(k);
				slika.setSlika(Files.readAllBytes(imageFile.toPath()));
				Slika s = sr.save(slika);
				if(s != null) {
					request.setAttribute("porukaOSlici", "Slika je uspešno postavljena.");
				}else {
					request.setAttribute("porukaOSlici", "Došlo je do greške! Slika nije postavljena.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			request.setAttribute("porukaOSlici", "Došlo je do greške! Slika nije postavljena.");
		}
		List<Komentar> komentari = komr.findByKoncert(k);
		request.setAttribute("komentari", komentari);
		List<Slika> slike = sr.findByKoncert(k);
		request.setAttribute("slike", slike);
		return "radnik/prikazKoncerta";
	}
	
	@RequestMapping(value="getKoncertZaAzuriranje", method=RequestMethod.GET)
	public String getKoncertZaAzuriranje(HttpServletRequest request) {
		Koncert koncert = (Koncert) request.getSession().getAttribute("Kkoncert");
		request.getSession().setAttribute("koncertA", koncert);
		return "radnik/azurirajKoncert";
	}
	
	@RequestMapping(value="azuriraj", method=RequestMethod.POST)
	public String azuriraj(String izvodjac, Date datumIzvodjenja, String trajanje, String maxBrojKarata, String grad, String adresa, String opis,String cena, HttpServletRequest request) {
		Koncert k = (Koncert) request.getSession().getAttribute("koncertA");
		Integer t = Integer.parseInt(trajanje);
		Integer max = Integer.parseInt(maxBrojKarata);
		Double c = Double.parseDouble(cena);
		k.setIzvodjac(izvodjac);
		k.setDatumIzvodjenja(datumIzvodjenja);
		k.setTrajanje(t);
		k.setMaxBrojKarata(max);
		k.setGrad(grad);
		k.setAdresa(adresa);
		k.setOpis(opis);
		k.setCena(c);
		Koncert azuriran = kr.save(k);
		String poruka;
		if(azuriran!=null) {
			request.setAttribute("azuriran", azuriran);
			poruka="";
		}else {
			poruka="Došlo je greške!";
		}
		request.setAttribute("poruka", poruka);
		return "radnik/azurirajKoncert";
	}
	
	@RequestMapping(value="obrisiKoncert", method=RequestMethod.POST)
	public String obrisiKoncert(HttpServletRequest request) {
		Koncert koncert = (Koncert) request.getSession().getAttribute("Kkoncert");
		List<Komentar> komentari = komr.findByKoncert(koncert);
		for(Komentar k:komentari) {
			komr.delete(k);
		}
		List<Omiljenikoncerti> omiljeni = okr.findByKoncert(koncert);
		for(Omiljenikoncerti o:omiljeni) {
			okr.delete(o);
		}
		List<Karta> karte = karr.findByKoncert(koncert);
		for(Karta k:karte) {
			karr.delete(k);
		}
		List<Slika> slike = sr.findByKoncert(koncert);
		for(Slika s:slike) {
			sr.delete(s);
		}
		kr.delete(koncert);
		request.setAttribute("porukaOBrisanju", "Koncert i njegovi podacu su uspešno obrisani.");
		request.getSession().setAttribute("Kkoncert", null);		
		return "radnik/prikazKoncerta";
	}
	
	@RequestMapping(value="dodajZanr", method=RequestMethod.POST)
	public String dodajZanr(String zanr, HttpServletRequest request) {
		Zanr postoji = zr.findByNaziv(zanr);
		if(postoji != null) {
			request.setAttribute("poruka", "Žanr sa takvim nazivom već postoji!");
			return "radnik/dodajZanr";
		}
		Zanr z = new Zanr();
		z.setNaziv(zanr);
		Zanr noviZanr = zr.save(z);
		if(noviZanr != null) {
			request.setAttribute("poruka", "Žanr je uspešno unet!");
		}else {
			request.setAttribute("poruka", "Došlo je do greške!");
		}
		return "radnik/dodajZanr";
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
			return "radnik/prikazKoncerta";
		}else {
			request.setAttribute("poruka", "Koncert sa traženim izvođačem ne postoji.");
			return "radnik/pretragaKoncerta";
		}
	}
	
	@RequestMapping(value="getZanroveZaPretragu", method=RequestMethod.GET)
	public String getZanroveZaPretragu(HttpServletRequest request) {
		List<Zanr> zanrovi = zr.findAll();
		request.getSession().setAttribute("zanrovi", zanrovi);
		return "radnik/pretragaPoZanru";
	}
	
	@RequestMapping(value="getKoncertePoZanru", method=RequestMethod.GET)
	public String getKoncertePoZanru(Integer zanr, HttpServletRequest request) {
		Zanr z = zr.findById(zanr).get();
		List<Koncert> koncerti = kr.findByZanr(z);
		request.setAttribute("koncerti", koncerti);
		return "radnik/pretragaPoZanru";
	}
	
	@RequestMapping(value="getSviKoncerti", method=RequestMethod.GET)
	public String getSviKoncerti(HttpServletRequest request) {
		List<Koncert> koncerti = kr.findAll();
		request.setAttribute("svikoncerti", koncerti);
		return "radnik/sviKoncerti";
	}
	
	@RequestMapping(value="getKoncertiKarte", method=RequestMethod.GET)
	public String getKoncerti(HttpServletRequest request) {
		List<Koncert> koncerti = kr.findAll();
		request.getSession().setAttribute("koncertiKar", koncerti);
		return "radnik/prikazKarata";
	}
	
	@RequestMapping(value="getKarte", method=RequestMethod.GET)
	public String getKarte(Integer koncert, HttpServletRequest request) {
		Koncert k = kr.findById(koncert).get();
		List<Karta> karte = karr.findByKoncert(k);
		String poruka;
		if(karte.size() > 0) {
			poruka="";
		}else {
			poruka="Nema rezervisanih karata za traženi koncert.";
		}
		request.setAttribute("karte", karte);
		request.setAttribute("poruka", poruka);
		return "radnik/prikazKarata";
	}
	
	@RequestMapping(value="getKoncerteBrKarata", method=RequestMethod.GET)
	public String getKoncerte(HttpServletRequest request) {
		List<Koncert> koncerti = kr.findAll();
		request.getSession().setAttribute("koncertiBr", koncerti);
		return "radnik/prikazBrojaKarata";
	}
	
	@RequestMapping(value="getBrojKarata", method=RequestMethod.GET)
	public String getBrojKarata(Integer koncert, HttpServletRequest request) {
		Koncert k = kr.findById(koncert).get();
		List<Karta> karte = karr.findByKoncert(k);
		int broj = karte.size();
		request.setAttribute("izvodjacKoncerta", k.getIzvodjac());
		request.setAttribute("broj", broj);
		return "radnik/prikazBrojaKarata";
	}
	
	@RequestMapping(value="getZanrove", method=RequestMethod.GET)
	public String getZanrove(HttpServletRequest request) {
		List<Zanr> zanrovi = zr.findAll();
		request.setAttribute("zanrovi", zanrovi);
		return "radnik/dodajKoncert";
	}
	
	@RequestMapping(value = "noviRadnik", method = RequestMethod.GET)
	public String noviRadnik(HttpServletRequest request) {
		Korisnik k = new Korisnik();
		request.setAttribute("korisnik", k);
		return "radnik/dodajKorisnika";
	}
	
	@RequestMapping(value="dodajRadnika", method=RequestMethod.POST)
	public String dodajRadnika(@ModelAttribute("korisnik")Korisnik k, HttpServletRequest request) {
		Korisnik vecIma = korr.findByUsername(k.getUsername());
		if(vecIma != null) {
			request.setAttribute("poruka", "Uneti username već postoji. Unesite nešto drugo.");
			return "radnik/dodajKorisnika";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		k.setPassword(passwordEncoder.encode(k.getPassword()));
		Uloga uloga = ur.findById(1).get();
		k.setUloga(uloga);
		Korisnik korisnik = korr.save(k);
		if(korisnik!=null) {
			request.setAttribute("poruka", "Korisnik je uspešno sačuvan!");
		}else {
			request.setAttribute("poruka", "Došlo je do greške!");
		}
		return "radnik/dodajKorisnika";
	}
	
	@RequestMapping(value="izvestajPoGradovima", method=RequestMethod.GET)
    public void izvestajPoGradovima(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Koncert> koncerti = kr.findAllByOrderByGrad();
		
    	response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(koncerti);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/koncertiPoGradovima.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imeSajta", "SvetMuzike");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=KoncertiPoGradovima.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
