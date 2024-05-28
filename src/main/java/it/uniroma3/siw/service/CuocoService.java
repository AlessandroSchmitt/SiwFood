package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.repository.CuocoRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;

    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @Autowired
    private FileService fileService;

    private static final String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";

    public Cuoco findById(Long id) {
        return cuocoRepository.findById(id).orElse(null);
    }

    public boolean existsByNomeAndCognome(String nome, String cognome) {
        return cuocoRepository.existsByNomeAndCognome(nome, cognome);
    }

    public void save(Cuoco cuoco) {
        cuocoRepository.save(cuoco);
    }

    @Transactional
    public void registerCuoco(Cuoco cuoco, Credenziali credenziali) {
        if (!existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
            cuocoRepository.save(cuoco);
            credenzialiRepository.save(credenziali);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Cuoco> cuoco = cuocoRepository.findById(id);
        if (cuoco.isPresent()) {
            // Elimina le credenziali associate
            Credenziali credenziali = credenzialiRepository.findByCuoco(cuoco.get());
            if (credenziali != null) {
                credenzialiRepository.delete(credenziali);
            }

            // Elimina l'immagine del cuoco
            if (!cuoco.get().getUrlsImages().isEmpty()) {
                try {
                    fileService.deleteFile(cuoco.get().getUrlsImages().get(0), UPLOADED_FOLDER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Elimina il cuoco dal database
            cuocoRepository.deleteById(id);
        } else {
            // Log se il cuoco non viene trovato
            System.out.println("Cuoco non trovato con ID: " + id);
        }
    }

    public void updateCuoco(Cuoco existingCuoco, Cuoco updatedCuoco, MultipartFile file) throws IOException {
        existingCuoco.setNome(updatedCuoco.getNome());
        existingCuoco.setCognome(updatedCuoco.getCognome());
        existingCuoco.setDataDiNascita(updatedCuoco.getDataDiNascita());

        if (!file.isEmpty()) {
            if (!existingCuoco.getUrlsImages().isEmpty()) {
                fileService.deleteFile(existingCuoco.getUrlsImages().get(0), UPLOADED_FOLDER);
            }

            String newImageUrl = fileService.saveFile(file, UPLOADED_FOLDER);
            existingCuoco.setUrlsImages(List.of(newImageUrl));
        }

        cuocoRepository.save(existingCuoco);
    }

    public Cuoco getCuoco(Long id) {
        return cuocoRepository.findById(id).orElse(null);
    }

    public Cuoco saveCuoco(Cuoco cuoco) {
        return cuocoRepository.save(cuoco);
    }

    public Iterable<Cuoco> findAll() {
        return cuocoRepository.findAll();
    }
}
