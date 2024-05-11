package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Utente;
import siw.uniroma3.it.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
    private UtenteRepository utenteRepository;

    public Utente getUser(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    public Utente saveUser(Utente utente) {
        return utenteRepository.save(utente);
    }

}
