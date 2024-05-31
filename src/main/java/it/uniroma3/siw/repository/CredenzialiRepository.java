package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long>{
	public Optional<Credenziali> findByUsername(String username);
	public boolean existsByUsername(String username);
	Credenziali findByCuoco(Cuoco cuoco);
}