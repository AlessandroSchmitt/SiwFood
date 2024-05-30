package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected CredenzialiRepository credentialsRepository;

    // Recupera le credenziali per ID
    @Transactional
    public Credenziali getCredentiziali(Long id) {
        Optional<Credenziali> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    // Recupera le credenziali per nome utente
    @Transactional
    public Credenziali getCredenziali(String username) {
        Optional<Credenziali> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    // Salva le credenziali con la password codificata e il ruolo impostato
    @Transactional
    public Credenziali saveCredenziali(Credenziali credentials) {
        credentials.setRuolo(Credenziali.COUCO_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
}
