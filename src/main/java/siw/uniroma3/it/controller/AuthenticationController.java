package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import siw.uniroma3.it.controller.validator.CredenzialiValidator;
import siw.uniroma3.it.controller.validator.UtenteValidator;
import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.model.Utente;
import siw.uniroma3.it.service.CredenzialiService;

@Controller
public class AuthenticationController {
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private UtenteValidator userValidator;
	
	@Autowired
	private CredenzialiValidator credentialsValidator;
	
	@GetMapping(value = "/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		return "formRegistraUtente";
	}

	@GetMapping(value = "/login")
	public String showLoginForm(Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		}
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getAuthentication().getPrincipal();
			Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
			if (credenziali.getRole().equals(Credenziali.ADMIN_ROLE)) {
				return "indexAmministratore.html"
			}
		}
		return "index.html";
	}
	
	@GetMapping(value="/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		if(credenziali.getRole().equals(Credenziali.ADMIN_ROLE)) {
			return "indexAdmin.html";
		}
	}
	
	@PostMapping(value= {"/register"})
	public String registerUser(@Valid @ModelAttribute("utente") Utente utente,
		BindingResult utenteBindingResult, @Valid
		@ModelAttribute("credenziali") Credenziali credenziali,
		BindingResult credenzialiBindingResult,
		Model model) {
			if(!utenteBindingResult.hasErrors() && ! credenzialiBindingResult.hasErrors()) {
				credenziali.setUtente(utente);
				credenzialiService.saveCredenziali(credenziali);
				model.addAttribute("utente", utente);
				return "registrationSuccesful";
			}
			return "registraUtente";
	}
	
}
		

