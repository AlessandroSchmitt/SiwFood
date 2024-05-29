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

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.FileService;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthenticationController {
    
    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private FileService fileService;
    
    private static final String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";
    
    @GetMapping(value = "/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        model.addAttribute("credenziali", new Credenziali());
        return "register.html";
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model) {
        return "login.html";
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "index.html";
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
            if (credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
                return "admin/indexAdmin.html";
            }
            if (credenziali.getRuolo().equals(Credenziali.COUCO_ROLE)) {
                return "cuoco/indexCuoco.html";
            }
        }
        return "index.html";
    }
    
    @GetMapping(value="/success")
    public String defaultAfterLogin(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
        if(credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        if(credenziali.getRuolo().equals(Credenziali.COUCO_ROLE)) {
            return "cuoco/indexCuoco.html";
        }
        return "success.html";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("cuoco") Cuoco cuoco,
                               BindingResult utenteBindingResult,
                               @ModelAttribute("credenziali") Credenziali credenziali,
                               BindingResult credenzialiBindingResult,
                               @RequestParam("fileImage") MultipartFile fileImage,
                               Model model) {
        if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
            try {
                if (!fileImage.isEmpty()) {
                    String imageUrl = fileService.saveFile(fileImage, UPLOADED_FOLDER);
                    cuoco.setUrlsImages(List.of(imageUrl));
                }
                credenziali.setCuoco(cuoco);
                credenzialiService.saveCredenziali(credenziali);
                model.addAttribute("cuoco", cuoco);
                return "redirect:/login";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Errore nel caricamento dell'immagine.");
                return "register.html";
            }
        }
        return "register.html";
    }
}
