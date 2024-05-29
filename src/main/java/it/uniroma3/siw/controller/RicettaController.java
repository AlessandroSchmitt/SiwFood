package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
    @Autowired
    private RicettaService ricettaService;
    @Autowired
	private IngredienteService ingredienteService;
    
    // Visualizza tutte le ricette
    @GetMapping("/ricette")
    public String showRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "ricette.html"; 
    }
    
    // Visualizza una singola ricetta in base all'ID
    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        Ricetta ricetta = ricettaService.findById(id);
        if (ricetta != null) {
            model.addAttribute("ricetta", ricetta);
            return "ricetta.html";
        }
        return "redirect:/ricette";
    }
    
 // Visualizza tutte le ricette per l'amministratore
    @GetMapping("/admin/ricette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/ricette.html";
    }
    
    @GetMapping("/cuoco/ricetteCuoco")
	public String showLeMieRicette(Model model) {
		model.addAttribute("ricette", ricettaService.findAll());
		return "cuoco/ricetteCuoco";
	}
	
	@PostMapping("/delete/ricetta/{id}")
	public String deleteRicetta(@PathVariable("id") Long id) {
		ricettaService.deleteById(id);
		return "redirect:/cuoco/ricetteCuoco";
	}
}
