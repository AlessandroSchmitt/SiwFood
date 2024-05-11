package siw.uniroma3.it.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Utente;

public interface UtenteRepository  extends CrudRepository<Utente, Long>{
	Optional<Utente> findById(Long id);
}
