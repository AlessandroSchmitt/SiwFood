package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.IngredienteService;

import java.util.List;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;
    
    @GetMapping("/admin/indexAdmin")
    public String showAdminIndex(Model model) {
        return "/admin/indexAdmin"; 
    }

    @GetMapping("/aggiungiIngredienti")
    public String showAggiungiIngredienteForm(Model model) {
        return "aggiungiIngredienti";
    }

    @PostMapping("/aggiungiIngredienti")
    public String aggiungiIngrediente(@RequestParam("ingredienti") List<String> ingredienti, Model model) {
        boolean tuttiAggiunti = ingredienteService.aggiungiIngredienti(ingredienti);
        if (!tuttiAggiunti) {
            model.addAttribute("errore", "Alcuni ingredienti esistono già nel database. aggiunti solo gli ingredienti non presenti");
            return "aggiungiIngredienti";
        }
        return "redirect:/cuoco/aggiungiRicetta";
    }
    
    @GetMapping("/admin/aggiungiIngredienti")
    public String showAggiungiIngredienteFormAdmin(Model model) {
        return "aggiungiIngredienti";
    }

    @PostMapping("/admin/aggiungiIngredienti")
    public String aggiungiIngredienteAdmin(@RequestParam("ingredienti") List<String> ingredienti, Model model) {
        boolean tuttiAggiunti = ingredienteService.aggiungiIngredienti(ingredienti);
        if (!tuttiAggiunti) {
            model.addAttribute("errore", "Alcuni ingredienti esistono già nel database. aggiunti solo gli ingredienti non presenti");
            return "aggiungiIngredienti";
        }
        return "redirect:/admin/indexAdmin";
    }
}