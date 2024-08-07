package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long>{
	public boolean existsByNomeAndCognome(String nome, String cognome);	
	Optional<Cuoco> findById(Long id);
}