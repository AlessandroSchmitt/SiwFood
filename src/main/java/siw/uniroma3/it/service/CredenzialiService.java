package siw.uniroma3.it.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredenzialiRepository credenzialiRepository;

    @Transactional
    public Credenziali getCredenziali(Long id) {
        Optional<Credenziali> result = this.credenzialiRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credenziali getCredenziali(String username) {
        Optional<Credenziali> result = this.credenzialiRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credenziali saveCredenziali(Credenziali credenziali) {
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
        return this.credenzialiRepository.save(credenziali);
    }
}