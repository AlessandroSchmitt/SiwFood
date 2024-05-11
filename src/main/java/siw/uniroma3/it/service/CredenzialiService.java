package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	@Autowired
    private CredenzialiRepository credenzialiRepository;

    public Credenziali getCredenziali(Long id) {
        return credenzialiRepository.findById(id).orElse(null);
    }

    public Credenziali getCredenziali(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }

    public Credenziali saveCredenziali(Credenziali credenziali) {
        return credenzialiRepository.save(credenziali);
    }
}
