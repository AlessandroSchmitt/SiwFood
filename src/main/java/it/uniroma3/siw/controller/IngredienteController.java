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

    @GetMapping("/aggiungiIngredienti")
    public String showAggiungiIngredienteForm(Model model) {
        return "aggiungiIngredienti";
    }

    @PostMapping("/aggiungiIngredienti")
    public String aggiungiIngrediente(@RequestParam("ingredienti") List<String> ingredienti, Model model) {
        ingredienteService.aggiungiIngredienti(ingredienti);
        return "redirect:/cuoco/aggiungiRicetta";
    }
}