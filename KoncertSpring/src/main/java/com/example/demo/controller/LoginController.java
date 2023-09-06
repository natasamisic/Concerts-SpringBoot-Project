package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.KoncertRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.UlogaRepository;

import model.Koncert;
import model.Korisnik;
import model.Uloga;

@Controller
@RequestMapping(value="/auth")
public class LoginController {
	
	@Autowired
	KorisnikRepository korr;
	@Autowired
	UlogaRepository ur;
	@Autowired
	KoncertRepository kr;
	
	@RequestMapping(value = "noviProfil", method = RequestMethod.GET)
	public String noviProfil(HttpServletRequest request) {
		Korisnik k = new Korisnik();
		request.setAttribute("korisnik", k);
		return "register";
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String register(@ModelAttribute("korisnik")Korisnik k, HttpServletRequest request) {
		Korisnik vecIma = korr.findByUsername(k.getUsername());
		if(vecIma != null) {
			request.setAttribute("vecIma", "Uneti username već postoji. Unesite nešto drugo.");
			return "korisnik/register";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		k.setPassword(passwordEncoder.encode(k.getPassword()));
		Uloga uloga = ur.findById(2).get();
		k.setUloga(uloga);
		Korisnik korisnik = korr.save(k);
		request.getSession().setAttribute("korisnik", korisnik);
		request.setAttribute("poruka", "Registracija uspešna.");
		return "login";
	}
	
	@RequestMapping(value="getKoncerti", method=RequestMethod.GET)
	public String getKoncerti(HttpServletRequest request) {
		List<Koncert> koncerti = kr.findAll();
		request.setAttribute("koncerti", koncerti);
		return "prikazSvihKoncerata";
	}
	
	@RequestMapping(value="/loginPage", method=RequestMethod.GET) 
	public String loginPage() { 
		return "login";
	}
	
	@RequestMapping(value="/failLogin", method=RequestMethod.GET) 
	public String failLogin(HttpServletRequest request) { 
		request.setAttribute("poruka", "Pogrešni podaci");
		return "login";
	}
	
	@RequestMapping(value="/pocetna", method=RequestMethod.GET) 
	public String pocetna() { 
		return "pocetna";
	}
	
	@RequestMapping(value="/indexPage", method=RequestMethod.GET) 
	public String indexPage() { 
		return "index";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		if (auth != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}	
		return "redirect:/auth/indexPage";
	}
//	@RequestMapping(value="odjava", method=RequestMethod.GET)
//	public String odjava(HttpServletRequest request) {
//		//request.getSession().setAttribute("korisnik", null);
//		request.removeAttribute("korisnik");
//		return "index";
//	}
//	@RequestMapping(value="loginKorisnik", method=RequestMethod.GET)
//	public String loginKorisnik(String username, String password, HttpServletRequest request) {
//		Korisnik k = korr.findByUsername(username);
//		if(k==null) {	
//			request.setAttribute("poruka", "Username koji ste uneli ne postoji!");
//			return "index";
//		}
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		boolean dobraSifra = passwordEncoder.matches(password, k.getPassword());
//		if(!dobraSifra) {
//			request.setAttribute("poruka", "Niste uneli dobar password!");
//			return "index";
//		}
//		request.getSession().setAttribute("korisnik", k);
//		if(k.getUloga().getIdUloga() == 1) {
//			return "radnik/pocetna";
//		}else {
//			return "korisnik/pocetna";
//		}
//	}
	@ModelAttribute
	public void getRoles(Model model) {
		List<Uloga> roles=ur.findAll();
		model.addAttribute("roles", roles);
		
	}
}
