package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	@Autowired
    private CredenzialiRepository credenzialiRepository;

    public Credenziali getCredentials(Long id) {
        return credenzialiRepository.findById(id).orElse(null);
    }

    public Credenziali getCredentials(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }

    public Credenziali saveCredentials(Credenziali credentials) {
        return credenzialiRepository.save(credentials);
    }
}
