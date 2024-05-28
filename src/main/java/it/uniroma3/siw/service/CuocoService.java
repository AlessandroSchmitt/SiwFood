package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CuocoRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;

    @Autowired
    private FileService fileService;

    private static final String UPLOADED_FOLDER = "uploads/cuochiAggiunti/";

    public Cuoco findById(Long id) {
        return cuocoRepository.findById(id).get();
    }

    public Iterable<Cuoco> findAll() {
        return cuocoRepository.findAll();
    }

    public boolean existsByNomeAndCognome(String nome, String cognome) {
        return cuocoRepository.existsByNomeAndCognome(nome, cognome);
    }

    public void save(Cuoco cuoco) {
        cuocoRepository.save(cuoco);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Cuoco> cuoco = cuocoRepository.findById(id);
        if (cuoco.isPresent()) {
            // Elimina l'immagine del cuoco
            if (!cuoco.get().getUrlsImages().isEmpty()) {
                try {
                    fileService.deleteFile(cuoco.get().getUrlsImages().get(0), UPLOADED_FOLDER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Elimina il cuoco
            cuocoRepository.deleteById(id);
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
            List<String> newUrlsImages = new ArrayList<>();
            newUrlsImages.add(newImageUrl);
            existingCuoco.setUrlsImages(newUrlsImages);
        }

        cuocoRepository.save(existingCuoco);
    }
}
