package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RigaRicetta;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.RicettaRepository;
import jakarta.transaction.Transactional;
import it.uniroma3.siw.repository.CuocoRepository;

@Service
public class RicettaService {
    @Autowired
    private RicettaRepository ricettaRepository;
    
    @Autowired
    private CuocoRepository cuocoRepository;
    
    @Autowired
    private IngredienteService ingredienteService;
    
    @Autowired
    private FileService fileService;
    
    private static final String UPLOAD_DIR = "uploads/ricetteAggiunte/";
    
    // Trova una ricetta per ID
    public Ricetta findById(Long id) {
        return ricettaRepository.findById(id).orElse(null);
    }

    // Trova tutte le ricette
    public Iterable<Ricetta> findAll() {
        return ricettaRepository.findAll();
    }

    // Salva una nuova ricetta o aggiorna una esistente
    public Ricetta save(Ricetta ricetta) {
        return ricettaRepository.save(ricetta);
    }

    // Cancella una ricetta per ID
    public void deleteById(Long id) {
        ricettaRepository.deleteById(id);
    }

    // Aggiunge una nuova ricetta per un dato cuoco
    public boolean addNewRicetta(Long cuocoId, Ricetta ricetta) {
        Cuoco cuoco = cuocoRepository.findById(cuocoId).orElse(null);
        if (cuoco != null) {
            ricetta.setCuoco(cuoco);
            save(ricetta);
            return true;
        }
        return false;
    }

    // Trova una ricetta da modificare se appartiene al cuoco dato
    public Ricetta getRicettaForModification(Long cuocoId, Long ricettaId) {
        Ricetta ricetta = findById(ricettaId);
        if (ricetta != null && ricetta.getCuoco().getId().equals(cuocoId)) {
            return ricetta;
        }
        return null;
    }

    // Aggiorna una ricetta esistente se appartiene al cuoco dato
    public boolean updateRicetta(Long cuocoId, Long ricettaId, Ricetta ricetta) {
        Ricetta existingRicetta = findById(ricettaId);
        if (existingRicetta != null && existingRicetta.getCuoco().getId().equals(cuocoId)) {
            existingRicetta.setNome(ricetta.getNome());
            existingRicetta.setDescrizione(ricetta.getDescrizione());
            save(existingRicetta);
            return true;
        }
        return false;
    }

    // Cancella una ricetta se appartiene al cuoco dato
    public void deleteRicetta(Long cuocoId, Long ricettaId) {
        Ricetta ricetta = findById(ricettaId);
        if (ricetta != null && ricetta.getCuoco().getId().equals(cuocoId)) {
            deleteById(ricettaId);
        }
    }
    
    @Transactional
    public void updateRicetta(Long id, Ricetta updatedRicetta, MultipartFile[] files, List<Long> ingredientiIds, List<String> quantitaList) throws IOException {
        Ricetta existingRicetta = findById(id);
        if (existingRicetta != null) {
            existingRicetta.setNome(updatedRicetta.getNome());
            existingRicetta.setDescrizione(updatedRicetta.getDescrizione());

            if (files != null && files.length > 0 && !files[0].isEmpty()) {
                List<String> urlsImages = handleFileUpload(files);
                existingRicetta.setUrlsImages(urlsImages);
            }

            updateRigheRicetta(existingRicetta, ingredientiIds, quantitaList);
            save(existingRicetta);
        }
    }
    
    private void updateRigheRicetta(Ricetta existingRicetta, List<Long> ingredientiIds, List<String> quantitaList) {
        List<RigaRicetta> existingRigheRicetta = existingRicetta.getRigheRicetta();
        existingRigheRicetta.clear();  // Clear existing collection

        for (int i = 0; i < ingredientiIds.size(); i++) {
            Ingrediente ingrediente = ingredienteService.findById(ingredientiIds.get(i));
            if (ingrediente != null) {
                RigaRicetta riga = new RigaRicetta();
                riga.setIngrediente(ingrediente);
                riga.setQuantita(quantitaList.get(i));
                riga.setRicetta(existingRicetta);
                existingRigheRicetta.add(riga);
            }
        }
    }


    private List<String> handleFileUpload(MultipartFile[] files) throws IOException {
        List<String> urlsImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = fileService.saveFile(file, UPLOAD_DIR);
                urlsImages.add(imageUrl);
            }
        }
        return urlsImages;
    }
}
