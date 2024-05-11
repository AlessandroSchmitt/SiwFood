package siw.uniroma3.it.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import siw.uniroma3.it.model.Utente;

@ControllerAdvice
public class GlobalController {
	@ModelAttribute("DettagliUtente")
	public Utente getUtente() {
		Utente utente = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			utente = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}
		return utente;
	}
}
