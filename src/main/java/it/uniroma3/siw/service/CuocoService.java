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

	// Trova un cuoco per ID
	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).orElse(null);
	}

	// Verifica se esiste un cuoco con il nome e cognome specificati
	public boolean existsByNomeAndCognome(String nome, String cognome) {
		return cuocoRepository.existsByNomeAndCognome(nome, cognome);
	}

	// Ritorna una lista di tutti i cuochi
	public List<Cuoco> findAll() {
		return (List<Cuoco>) cuocoRepository.findAll();
	}

	// Registra un nuovo cuoco e le sue credenziali, se non esiste già
	@Transactional
	public void registerCuoco(Cuoco cuoco, Credenziali credenziali) {
		if (!existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
			cuocoRepository.save(cuoco);
			credenzialiRepository.save(credenziali);
		}
	}

	// Elimina un cuoco per ID, inclusi i suoi file associati e le credenziali
	@Transactional
	public void deleteById(Long id) {
		Optional<Cuoco> cuoco = cuocoRepository.findById(id);
		if (cuoco.isPresent()) {
			deleteCuocoAndCredenziali(cuoco.get());
		} else {
			System.out.println("Cuoco non trovato con ID: " + id);
		}
	}

	// Aggiorna le informazioni di un cuoco esistente
	@Transactional
	public void updateCuoco(Long id, Cuoco updatedCuoco, MultipartFile file) throws IOException {
		Cuoco existingCuoco = cuocoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cuoco non trovato"));

		// Aggiorna i campi del cuoco
		existingCuoco.setNome(updatedCuoco.getNome());
		existingCuoco.setCognome(updatedCuoco.getCognome());
		existingCuoco.setDataDiNascita(updatedCuoco.getDataDiNascita());

		// Se è presente un nuovo file di immagine, aggiorna l'immagine del cuoco
		if (!file.isEmpty()) {
			updateCuocoImage(existingCuoco, file);
		}

		// Salva le modifiche del cuoco
		cuocoRepository.save(existingCuoco);
	}

	// Aggiorna l'immagine del cuoco
	private void updateCuocoImage(Cuoco cuoco, MultipartFile file) throws IOException {
		// Elimina l'immagine esistente se presente
		if (!cuoco.getUrlsImages().isEmpty()) {
			fileService.deleteFile(cuoco.getUrlsImages().get(0), UPLOADED_FOLDER);
		}

		// Salva la nuova immagine e aggiorna il cuoco
		String newImageUrl = fileService.saveFile(file, UPLOADED_FOLDER);
		cuoco.setUrlsImages(List.of(newImageUrl));
	}

	// Elimina un cuoco e le sue credenziali
	private void deleteCuocoAndCredenziali(Cuoco cuoco) {
		// Trova e elimina le credenziali associate
		Credenziali credenziali = credenzialiRepository.findByCuoco(cuoco);
		if (credenziali != null) {
			credenzialiRepository.delete(credenziali);
		}

		// Elimina l'immagine del cuoco se presente
		if (!cuoco.getUrlsImages().isEmpty()) {
			try {
				fileService.deleteFile(cuoco.getUrlsImages().get(0), UPLOADED_FOLDER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Elimina il cuoco dal database
		cuocoRepository.delete(cuoco);
	}
}
