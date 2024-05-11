package siw.uniroma3.it.repository;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{
	
}