package siw.uniroma3.it.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Credenziali;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long>{
	public Optional<Credenziali> findByUsername(String username);
}
