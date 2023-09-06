package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import model.Karta;
import model.Komentar;
import model.Koncert;
import model.Korisnik;
import model.Omiljenikoncerti;
import model.Slika;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/koncerti")
public class KoncertController {
	
	@Autowired
	KoncertRepository kr;
	@Autowired
	KorisnikRepository korr;
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
	
	@RequestMapping(value="getProfil", method=RequestMethod.GET)
	public String getProfil(HttpServletRequest request) {
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		request.setAttribute("korisnik", k);
		if(k.getUloga().getIdUloga() == 1) {
			return "radnik/azurirajPodatke";
		}else {
			return "korisnik/azurirajPodatke";
		}
	}
	
	@RequestMapping(value="azurirajPodatke", method=RequestMethod.POST)
	public String azurirajPodatke(String ime, String prezime, String email, HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setEmail(email);
		Korisnik azuriran = korr.save(k);
		if(azuriran!=null) {
			request.setAttribute("korisnik", azuriran);
			request.setAttribute("poruka", "Podaci su uspešno ažurirani.");
		}else {
			request.setAttribute("poruka", "Došlo je greške! Podaci nisu ažurirani.");
		}
		if(k.getUloga().getIdUloga() == 1) {
			return "radnik/azurirajPodatke";
		}else {
			return "korisnik/azurirajPodatke";
		}
	}
	
	@RequestMapping(value="obrisiProfil", method=RequestMethod.POST)
	public String obrisiProfil(HttpServletRequest request) {
//		Korisnik k = (Korisnik) request.getSession().getAttribute("korisnik");
		String user = request.getUserPrincipal().getName();
		Korisnik k = korr.findByUsername(user);
		List<Omiljenikoncerti> omiljeni = okr.findByKorisnik(k);
		for(Omiljenikoncerti o:omiljeni) {
			okr.delete(o);
		}
		List<Karta> karte = karr.findByKorisnik(k);
		for(Karta kar:karte) {
			karr.delete(kar);
		}
		List<Komentar> komentari = komr.findByKorisnik(k);
		for(Komentar kom:komentari) {
			komr.delete(kom);
		}
		korr.delete(k);
		return "index";
	}
	
	@RequestMapping(value="getSlika", method=RequestMethod.GET)
	public void getSlika(Integer idS, HttpServletRequest request,HttpServletResponse response) {
		Slika s = sr.findById(idS).get();		
		byte[] slika = s.getSlika();
		try {
			OutputStream out = response.getOutputStream();
			out.write(slika);
			out.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	@RequestMapping(value="repertoar", method=RequestMethod.GET)
    public void repertoar(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Koncert> koncerti = kr.findAllByOrderByDatumIzvodjenja();
		
    	response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(koncerti);
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/Repertoar.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imeSajta", "SvetMuzike");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=Repertoar.pdf");
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }
}
