package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ricetta;

import it.uniroma3.siw.model.Cuoco;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{
	public boolean  existsByNomeAndCuoco(String nome, Cuoco cuoco);
}