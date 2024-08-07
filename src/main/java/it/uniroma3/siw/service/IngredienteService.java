package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    // Recupera tutti gli ingredienti
    public Iterable<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    // Aggiunge una lista di ingredienti
    public boolean aggiungiIngredienti(List<String> ingredienti) {
        boolean tuttiAggiunti = true;
        for (String nomeIngrediente : ingredienti) {
            if (ingredienteRepository.findByNome(nomeIngrediente).isPresent()) {
                tuttiAggiunti = false;
            } else {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setNome(nomeIngrediente);
                ingredienteRepository.save(ingrediente);
            }
        }
        return tuttiAggiunti;
    }
    
    // Trova un ingrediente per ID
    public Ingrediente findById(Long id) {
        return ingredienteRepository.findById(id).orElse(null);
    }
}
