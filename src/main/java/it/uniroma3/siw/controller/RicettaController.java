package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class RicettaController {
    @Autowired
    private RicettaService ricettaService;
    
    @Autowired
    private CuocoService cuocoService;

    @GetMapping("/ricette")
    public String showRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "ricette.html"; 
    }
    
    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "ricetta.html";
    }

    @GetMapping("/cuoco/{cuocoId}/new/ricetta")
    public String formNewRicetta(@PathVariable("cuocoId") Long cuocoId, Model model) {
        model.addAttribute("ricetta", new Ricetta());
        model.addAttribute("cuocoId", cuocoId);
        return "cuoco/formNewRicetta.html";
    }

    @PostMapping("/cuoco/{cuocoId}/new/ricetta")
    public String addNewRicetta(@PathVariable("cuocoId") Long cuocoId, @ModelAttribute("ricetta") Ricetta ricetta) {
        Cuoco cuoco = cuocoService.findById(cuocoId);
        if (cuoco != null) {
            ricetta.setCuoco(cuoco);
            ricettaService.save(ricetta);
            return "redirect:/cuoco/" + cuocoId;
        }
        return "redirect:/ricette";
    }

    @GetMapping("/cuoco/{cuocoId}/edit/ricetta/{ricettaId}")
    public String formModifyRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, Model model) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        if (ricetta != null && ricetta.getCuoco().getId().equals(cuocoId)) {
            model.addAttribute("ricetta", ricetta);
            return "cuoco/formModifyRicetta.html";
        }
        return "redirect:/cuoco/" + cuocoId;
    }

    @PostMapping("/cuoco/{cuocoId}/update/ricetta/{ricettaId}")
    public String updateRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, @ModelAttribute("ricetta") Ricetta ricetta) {
        Ricetta existingRicetta = ricettaService.findById(ricettaId);
        if (existingRicetta != null && existingRicetta.getCuoco().getId().equals(cuocoId)) {
            existingRicetta.setNome(ricetta.getNome());
            existingRicetta.setDescrizione(ricetta.getDescrizione());
            ricettaService.save(existingRicetta);
            return "redirect:/cuoco/" + cuocoId;
        }
        return "redirect:/ricette";
    }

    @PostMapping("/cuoco/{cuocoId}/delete/ricetta/{ricettaId}")
    public String deleteRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId) {
        Ricetta ricetta = ricettaService.findById(ricettaId);
        if (ricetta != null && ricetta.getCuoco().getId().equals(cuocoId)) {
            ricettaService.deleteById(ricettaId);
        }
        return "redirect:/cuoco/" + cuocoId;
    }

    @GetMapping("/admin/ricette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/ricette.html";
    }
}
