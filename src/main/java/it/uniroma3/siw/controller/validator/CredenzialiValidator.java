package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Component
public class CredenzialiValidator implements Validator {
    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Credenziali.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Credenziali credenziali = (Credenziali) target;
        if (credenziali.getUsername() != null && credenziali.getPassword() != null
        		&& this.credenzialiRepository.existsByUsername(credenziali.getUsername())) {
        	errors.reject("credenziali.duplicate");
        }
    }
}