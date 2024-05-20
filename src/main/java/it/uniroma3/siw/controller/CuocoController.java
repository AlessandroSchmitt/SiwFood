package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class CuocoController {
	@Autowired
	private CuocoService cuocoService;

	private static String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";
	private static final Logger logger = Logger.getLogger(CuocoController.class.getName());

	@GetMapping(value = "/admin/indexUpdateCuoco")
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
	    // Aggiungi eventuali attributi al modello se necessario
	    return "cuoco/indexCuoco";
	}


	@GetMapping("/cuoco/{id}")
	public String getCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		return "cuoco.html";
	}

	@PostMapping("/admin/delete/cuoco/{id}")
	public String deleteCuoco(@PathVariable("id") Long id) {
		Cuoco cuoco = cuocoService.findById(id);
		if (cuoco != null) {
			// Elimina tutti i file associati al cuoco
			try {
				for (String urlImage : cuoco.getUrlsImages()) {
					Path path = Paths.get(UPLOADED_FOLDER).resolve(Paths.get(urlImage).getFileName().toString());
					if (Files.exists(path)) {
						Files.delete(path);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			cuocoService.deleteById(id);
		}
		return "redirect:/admin/indexUpdateCuoco";
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
	            Path uploadDir = Paths.get(UPLOADED_FOLDER);
	            if (!Files.exists(uploadDir)) {
	                Files.createDirectories(uploadDir);
	            }

	            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	            Path path = uploadDir.resolve(fileName);
	            Files.write(path, file.getBytes());

	            List<String> urlsImages = new ArrayList<>();
	            urlsImages.add("/cuochiAggiunti/" + fileName);
	            cuoco.setUrlsImages(urlsImages);
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
	public String updateCuoco(@PathVariable("id") Long id, @ModelAttribute("cuoco") Cuoco cuoco,
			@RequestParam("fileImage") MultipartFile file, Model model) {
		Cuoco existingCuoco = cuocoService.findById(id);

		// Aggiorna i dettagli del cuoco
		existingCuoco.setNome(cuoco.getNome());
		existingCuoco.setCognome(cuoco.getCognome());
		existingCuoco.setDataDiNascita(cuoco.getDataDiNascita());

		if (!file.isEmpty()) {
			// Elimina il file esistente
			try {
				if (!existingCuoco.getUrlsImages().isEmpty()) {
					Path oldPath = Paths.get(UPLOADED_FOLDER)
							.resolve(Paths.get(existingCuoco.getUrlsImages().get(0)).getFileName().toString());
					if (Files.exists(oldPath)) {
						Files.delete(oldPath);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("messaggioErrore", "Errore nella cancellazione dell'immagine esistente");
				return "admin/formModifyCuoco";
			}

			// Salva il nuovo file nel server
			try {
				Path uploadDir = Paths.get(UPLOADED_FOLDER);
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Usa il nome originale del file
				Path path = uploadDir.resolve(fileName);
				Files.write(path, file.getBytes());

				// Imposta il nuovo URL dell'immagine
				List<String> newUrlsImages = new ArrayList<>();
				newUrlsImages.add("/cuochiAggiunti/" + fileName);
				existingCuoco.setUrlsImages(newUrlsImages);
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
				return "admin/formModifyCuoco";
			}
		}

		// Salva le modifiche del cuoco
		cuocoService.save(existingCuoco);
		return "redirect:/admin/indexUpdateCuoco";
	}

}
