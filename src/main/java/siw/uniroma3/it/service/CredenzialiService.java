package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@Autowired
    private CredenzialiRepository credenzialiRepository;
	
	@Transactional
    public Credenziali getCredenziali(Long id) {
        return credenzialiRepository.findById(id).orElse(null);
    }

	@Transactional
    public Credenziali getCredenziali(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }

	@Transactional
    public Credenziali saveCredenziali(Credenziali credenziali) {
		credenziali.setRole(Credenziali.DEFAULT_ROLE);
        credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        return credenzialiRepository.save(credenziali);
    }
}
