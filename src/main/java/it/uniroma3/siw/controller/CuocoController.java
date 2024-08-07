package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.FileService;

import java.util.List;


@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Costante per il percorso della cartella di upload
    private static final String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";

    // Mostra la pagina con l'elenco di tutti i cuochi.
    @GetMapping("/cuochi")
    public String showCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "cuochi";
    }

    // Mostra la pagina principale del cuoco.
    @GetMapping("/cuoco/indexCuoco")
    public String indexCuoco() {
        return "cuoco/indexCuoco";
    }
    
  //Mostra la pagina con l'elenco dei cuochi da aggiornare per l'admin.
    @GetMapping("/admin/indexUpdateCuoco")
    public String indexUpdateCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexUpdateCuoco";
    }

    //Mostra i dettagli di un cuoco specifico.
    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.findById(id));
        return "cuoco";
    }

    //Mostra il form per aggiungere un nuovo cuoco.
    @GetMapping("/admin/new/cuoco")
    public String formNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "admin/formNewCuoco";
    }
    
    //Mostra il form per modificare un cuoco esistente.
    @GetMapping("/admin/edit/cuoco/{id}")
    public String formModifyCuoco(@PathVariable("id") Long id, Model model) {    
        model.addAttribute("cuoco", cuocoService.findById(id));
        return "admin/formModifyCuoco";
    }

    //Gestisce l'invio del form per aggiungere un nuovo cuoco.
    @PostMapping("/admin/new/cuoco")
    public String addNewCuoco(@ModelAttribute("cuoco") Cuoco cuoco, 
    						  @RequestParam("username") String username, 
    						  @RequestParam("password") String password,
    						  @RequestParam("confirmPassword") String confirmPassword, 
    						  @RequestParam("fileImage") MultipartFile file,
    						  Model model) throws Exception{
        // Salva l'immagine del cuoco se presente
        if (!file.isEmpty()) {     
            String imageUrl = fileService.saveFile(file, UPLOADED_FOLDER);
            cuoco.setUrlsImages(List.of(imageUrl));         
        }

        // Controlla se le password coincidono
        if (!password.equals(confirmPassword)) {
            model.addAttribute("messaggioErrore", "Le password non coincidono");
            return "admin/formNewCuoco";
        }

        // Registra il nuovo cuoco e le sue credenziali
        cuocoService.registerCuoco(cuoco, createCredenziali(username, password, cuoco));
        return "redirect:/admin/indexUpdateCuoco";
    }

    // Metodo per creare le credenziali di un cuoco.
    private Credenziali createCredenziali(String username, String password, Cuoco cuoco) {
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setPassword(passwordEncoder.encode(password));
        credenziali.setCuoco(cuoco);
        credenziali.setRuolo(Credenziali.COUCO_ROLE);
        return credenziali;
    }

    //Gestisce l'invio del form per modificare un cuoco esistente.
    @PostMapping("/admin/update/cuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id,
    						  @ModelAttribute("cuoco") Cuoco cuoco,
    						  @RequestParam("fileImage") MultipartFile file, 
    						  Model model) throws Exception{       
            cuocoService.updateCuoco(id, cuoco, file);
            return "redirect:/admin/indexUpdateCuoco";
    }

    //Gestisce l'eliminazione di un cuoco.
    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id, Model model) throws Exception {
            cuocoService.deleteById(id);
            return "redirect:/admin/indexUpdateCuoco";      
    }
}