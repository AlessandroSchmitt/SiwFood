package siw.uniroma3.it.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.Credenziali;
import siw.uniroma3.it.model.Utente;
import siw.uniroma3.it.service.CredenzialiService;

@Component
public class CredenzialiValidator implements Validator{
		@Autowired
	 	private CredenzialiService credenzialiService;

	    final Integer MAX_USERNAME_LENGTH = 20;
	    final Integer MIN_USERNAME_LENGTH = 4;
	    final Integer MAX_PASSWORD_LENGTH = 20;
	    final Integer MIN_PASSWORD_LENGTH = 6;

	    @Override
	    public void validate(Object o, Errors errors) {
	    	Credenziali credenziali = (Credenziali) o;
	        String username = credenziali.getUsername().trim();
	        String password = credenziali.getPassword().trim();

	        if (username.isEmpty())
	            errors.rejectValue("username", "required");
	        else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
	            errors.rejectValue("username", "size");
	        else if (this.credenzialiService.getCredenziali(username) != null)
	            errors.rejectValue("username", "duplicate");

	        if (password.isEmpty())
	            errors.rejectValue("password", "required");
	        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
	            errors.rejectValue("password", "size");
	    }

	    @Override
	    public boolean supports(Class<?> clazz) {
	        return Utente.class.equals(clazz);
	    }
}
