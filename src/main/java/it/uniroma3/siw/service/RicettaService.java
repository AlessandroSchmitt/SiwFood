package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.RicettaRepository;
import it.uniroma3.siw.repository.CuocoRepository;

@Service
public class RicettaService {
    @Autowired
    private RicettaRepository ricettaRepository;
    
    @Autowired
    private CuocoRepository cuocoRepository;

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
}
