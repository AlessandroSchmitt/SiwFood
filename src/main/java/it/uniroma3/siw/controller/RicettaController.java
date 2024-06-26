package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return "ricette"; 
    }

    // Visualizza una singola ricetta in base all'ID
    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        Ricetta ricetta = ricettaService.findById(id);
        if (ricetta != null) {
            model.addAttribute("ricetta", ricetta);
            return "ricetta";
        }
        return "redirect:/ricette";
    }

    // Visualizza tutte le ricette per l'amministratore
    @GetMapping("/admin/ricette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/ricette";
    }

    // Visualizza le ricette del cuoco
    @GetMapping("/cuoco/ricetteCuoco")
    public String showLeMieRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "cuoco/ricetteCuoco";
    }

    // Elimina una ricetta in base all'ID
    @PostMapping("/delete/ricetta/{id}")
    public String deleteRicetta(@PathVariable("id") Long id) {
        ricettaService.deleteById(id);
        return "redirect:/cuoco/ricetteCuoco";
    }

    // Admin visualizza tutte le ricette
    @GetMapping("/admin/tutteRicette")
    public String tutteRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/tutteRicette";
    }

    // Admin elimina una ricetta in base all'ID
    @PostMapping("/admin/delete/ricetta/{id}")
    public String deleteRicettaByAdmin(@PathVariable("id") Long id) {
        ricettaService.deleteById(id);
        return "redirect:/admin/tutteRicette";
    }

    // Mostra il form per aggiungere una nuova ricetta
    @GetMapping("/cuoco/aggiungiRicetta")
    public String aggiungiRicetta(Model model) {
        List<Ingrediente> ingredienti = StreamSupport.stream(ingredienteService.findAll().spliterator(), false)
                .sorted((i1, i2) -> i1.getNome().compareToIgnoreCase(i2.getNome()))
                .collect(Collectors.toList());
        model.addAttribute("ingredienti", ingredienti);

        if (ingredienti.isEmpty()) {
            Ingrediente ingrediente = new Ingrediente();
            model.addAttribute("ingredienti", List.of(ingrediente));
        }

        return "cuoco/aggiungiRicetta";
    }

    // Gestisce l'invio del form per aggiungere una nuova ricetta
    @PostMapping("/cuoco/aggiungiRicetta")
    public String aggiungiRicetta(@ModelAttribute("ricetta") Ricetta ricetta, BindingResult ricettaBindingResult,
                                  @RequestParam("fileImages") MultipartFile[] files, @RequestParam("cuocoId") Long cuocoId,
                                  @RequestParam(value = "ingredientiIds", required = false) List<Long> ingredientiIds,
                                  @RequestParam(value = "quantita", required = false) List<String> quantitaList, Model model) {
        if (ricettaBindingResult.hasErrors()) {
            List<Ingrediente> ingredienti = StreamSupport.stream(ingredienteService.findAll().spliterator(), false)
                    .sorted((i1, i2) -> i1.getNome().compareToIgnoreCase(i2.getNome()))
                    .collect(Collectors.toList());
            model.addAttribute("ingredienti", ingredienti);
            return "cuoco/aggiungiRicetta";
        }

        // Verifica se ingredientiIds e quantitaList sono null o vuoti
        if (ingredientiIds == null) {
            ingredientiIds = new ArrayList<>();
        }

        if (quantitaList == null) {
            quantitaList = new ArrayList<>();
        }

        try {
            ricettaService.registerRicetta(ricetta, cuocoId, files, ingredientiIds, quantitaList);
            return "redirect:/cuoco/ricetteCuoco";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nel caricamento delle immagini");
            return "cuoco/aggiungiRicetta";
        }
    }

    // Mostra il form per modificare una ricetta esistente
    @GetMapping("/update/ricetta/{id}")
    public String modificaRicetta(@PathVariable("id") Long id, Model model) {
        Ricetta ricetta = ricettaService.findById(id);
        List<Ingrediente> ingredienti = StreamSupport.stream(ingredienteService.findAll().spliterator(), false)
                .sorted((i1, i2) -> i1.getNome().compareToIgnoreCase(i2.getNome()))
                .collect(Collectors.toList());
        model.addAttribute("ricetta", ricetta);
        model.addAttribute("ingredienti", ingredienti);
        return "cuoco/modificaRicetta";
    }

    // Gestisce l'invio del form per modificare una ricetta esistente
    @PostMapping("/update/ricetta/{id}")
    public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute("ricetta") Ricetta ricetta,
                                BindingResult ricettaBindingResult, @RequestParam("fileImages") MultipartFile[] files,
                                @RequestParam(value = "ingredientiIds", required = false) List<Long> ingredientiIds,
                                @RequestParam(value = "quantita", required = false) List<String> quantitaList, Model model) {
        if (ricettaBindingResult.hasErrors()) {
            List<Ingrediente> ingredienti = StreamSupport.stream(ingredienteService.findAll().spliterator(), false)
                    .sorted((i1, i2) -> i1.getNome().compareToIgnoreCase(i2.getNome()))
                    .collect(Collectors.toList());
            model.addAttribute("ingredienti", ingredienti);
            return "cuoco/modificaRicetta";
        }

        // Controlla se ingredientiIds è null o vuoto
        if (ingredientiIds == null) {
            ingredientiIds = new ArrayList<>();
        }

        if (quantitaList == null) {
            quantitaList = new ArrayList<>();
        }

        try {
            ricettaService.updateRicetta(id, ricetta, files, ingredientiIds, quantitaList);
            return "redirect:/cuoco/ricetteCuoco";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nel caricamento delle immagini");
            return "cuoco/modificaRicetta";
        }
    }

}
