package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.FileService;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthenticationController {

	@Autowired
	private CredenzialiService credenzialiService;
	@Autowired
	private FileService fileService;
	@Autowired
    private CredenzialiValidator credenzialiValidator;

	// Costante per il percorso della cartella di upload
	private static final String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";

	// Gestisce la richiesta GET per la registrazione
	@GetMapping(value = "/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("cuoco", new Cuoco()); // Aggiunge un nuovo oggetto Cuoco al modello
		model.addAttribute("credenziali", new Credenziali()); // Aggiunge un nuovo oggetto Credenziali al modello
		return "register"; // Restituisce la vista della pagina di registrazione
	}

	// Gestisce la richiesta GET per il login
	@GetMapping(value = "/login")
	public String showLoginForm(Model model) {
		return "login"; // Restituisce la vista della pagina di login
	}

	// Gestisce la richiesta GET per la pagina principale
	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index"; // Se l'utente non è autenticato, restituisce la vista index
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
			if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				return "admin/indexAdmin"; // Se l'utente è un amministratore, restituisce la vista admin
			}
			if (credenziali.getRuolo().equals(Credenziali.COUCO_ROLE)) {
				return "cuoco/indexCuoco"; // Se l'utente è un cuoco, restituisce la vista cuoco
			}
		}
		return "index"; // Default: restituisce la vista index
	}

	// Gestisce la richiesta GET per il redirect dopo il login
	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/indexAdmin"; // Se l'utente è un amministratore, restituisce la vista admin
		}
		if (credenziali.getRuolo().equals(Credenziali.COUCO_ROLE)) {
			return "cuoco/indexCuoco"; // Se l'utente è un cuoco, restituisce la vista cuoco
		}
		return "errore"; 
	}

	@PostMapping(value = "/register")
    public String registerUser(@Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult utenteBindingResult,
                               @ModelAttribute("credenziali") Credenziali credenziali, BindingResult credenzialiBindingResult,
                               @RequestParam("fileImage") MultipartFile fileImage, Model model) {
        this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
        
        // Check for validation errors
        if (!credenzialiBindingResult.hasErrors()) {
            try {
                // Handle file upload if not empty
                if (!fileImage.isEmpty()) {
                    String imageUrl = fileService.saveFile(fileImage, UPLOADED_FOLDER);
                    cuoco.setUrlsImages(List.of(imageUrl));
                }
                
                // Set Cuoco and save credentials
                credenziali.setCuoco(cuoco);
                credenzialiService.saveCredenziali(credenziali);
                model.addAttribute("cuoco", cuoco);
                
                // Redirect to login on successful registration
                return "redirect:/login";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Errore nel caricamento dell'immagine.");
                return "register";
            }
        }
        return "register";
    }

}