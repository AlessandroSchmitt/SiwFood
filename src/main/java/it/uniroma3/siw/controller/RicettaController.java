package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
    @Autowired
    private RicettaService ricettaService;

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

    // Mostra il form per creare una nuova ricetta per un dato cuoco
    @GetMapping("/cuoco/{cuocoId}/new/ricetta")
    public String formNewRicetta(@PathVariable("cuocoId") Long cuocoId, Model model) {
        model.addAttribute("ricetta", new Ricetta());
        model.addAttribute("cuocoId", cuocoId);
        return "cuoco/formNewRicetta.html";
    }

    // Gestisce la richiesta di creazione di una nuova ricetta
    @PostMapping("/cuoco/{cuocoId}/new/ricetta")
    public String addNewRicetta(@PathVariable("cuocoId") Long cuocoId, @ModelAttribute("ricetta") Ricetta ricetta) {
        if (ricettaService.addNewRicetta(cuocoId, ricetta)) {
            return "redirect:/cuoco/" + cuocoId;
        }
        return "redirect:/ricette";
    }

    // Mostra il form per modificare una ricetta esistente
    @GetMapping("/cuoco/{cuocoId}/edit/ricetta/{ricettaId}")
    public String formModifyRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, Model model) {
        Ricetta ricetta = ricettaService.getRicettaForModification(cuocoId, ricettaId);
        if (ricetta != null) {
            model.addAttribute("ricetta", ricetta);
            return "cuoco/formModifyRicetta.html";
        }
        return "redirect:/cuoco/" + cuocoId;
    }

    // Gestisce la richiesta di aggiornamento di una ricetta esistente
    @PostMapping("/cuoco/{cuocoId}/update/ricetta/{ricettaId}")
    public String updateRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, @ModelAttribute("ricetta") Ricetta ricetta) {
        if (ricettaService.updateRicetta(cuocoId, ricettaId, ricetta)) {
            return "redirect:/cuoco/" + cuocoId;
        }
        return "redirect:/ricette";
    }

    // Gestisce la richiesta di cancellazione di una ricetta
    @PostMapping("/cuoco/{cuocoId}/delete/ricetta/{ricettaId}")
    public String deleteRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId) {
        ricettaService.deleteRicetta(cuocoId, ricettaId);
        return "redirect:/cuoco/" + cuocoId;
    }

    // Visualizza tutte le ricette per l'amministratore
    @GetMapping("/admin/ricette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/ricette.html";
    }
}
