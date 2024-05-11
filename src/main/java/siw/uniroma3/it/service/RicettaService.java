package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Ricetta;
import siw.uniroma3.it.repository.RicettaRepository;

@Service
public class RicettaService {
	@Autowired
	private RicettaRepository ricettaRepository;
	
	public Ricetta findById(Long id) {
		return ricettaRepository.findById(id).get();
	}
	
	public Iterable<Ricetta> findAll() {
		return ricettaRepository.findAll();
	}

}