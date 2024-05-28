package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private FileService fileService;

    private static final Logger logger = Logger.getLogger(CuocoController.class.getName());
    private static String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";

    @GetMapping("/admin/indexUpdateCuoco")
    public String indexUpdateCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexUpdateCuoco.html";
    }

    @GetMapping("/cuochi")
    public String showCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "cuochi.html";
    }

    @GetMapping("/cuoco/indexCuoco")
    public String indexCuoco(Model model) {
        return "cuoco/indexCuoco";
    }

    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }

    @GetMapping("/admin/new/cuoco")
    public String formNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "admin/formNewCuoco.html";
    }

    @PostMapping("/admin/new/cuoco")
    public String addNewCuoco(@ModelAttribute("cuoco") Cuoco cuoco, @RequestParam("fileImage") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                String imageUrl = fileService.saveFile(file, UPLOADED_FOLDER);
                cuoco.setUrlsImages(List.of(imageUrl));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
                return "admin/formNewCuoco";
            }
        }

        cuocoService.save(cuoco);
        return "redirect:/admin/indexUpdateCuoco";
    }

    @GetMapping("/admin/edit/cuoco/{id}")
    public String formModifyCuoco(@PathVariable("id") Long id, Model model) {
        Cuoco cuoco = cuocoService.findById(id);
        if (cuoco != null) {
            model.addAttribute("cuoco", cuoco);
            return "admin/formModifyCuoco.html";
        } else {
            return "redirect:/admin/indexUpdateCuoco";
        }
    }

    @PostMapping("/admin/update/cuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id,
                              @ModelAttribute("cuoco") Cuoco cuoco,
                              @RequestParam("fileImage") MultipartFile file,
                              Model model) {
        Cuoco existingCuoco = cuocoService.findById(id);

        if (existingCuoco != null) {
            try {
                cuocoService.updateCuoco(existingCuoco, cuoco, file);
                return "redirect:/admin/indexUpdateCuoco";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nella gestione dell'immagine");
                return "admin/formModifyCuoco.html";
            }
        } else {
            model.addAttribute("messaggioErrore", "Cuoco non trovato");
            return "admin/formModifyCuoco.html";
        }
    }
    
    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id, Model model) {
        try {
            cuocoService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messaggioErrore", "Errore nell'eliminazione del cuoco");
            return "admin/indexUpdateCuoco";
        }
        return "redirect:/admin/indexUpdateCuoco";
    }


}
