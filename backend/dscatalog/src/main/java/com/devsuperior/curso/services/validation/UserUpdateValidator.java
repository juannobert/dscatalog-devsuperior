package com.devsuperior.curso.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.devsuperior.curso.dto.UserUpdateDTO;
import com.devsuperior.curso.entities.User;
import com.devsuperior.curso.repositories.UserRepository;
import com.devsuperior.curso.resources.exceptions.FieldMessage;


public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		var varsUri = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); //atributos da uri
		
		long userId = Long.parseLong(varsUri.get("id")); //pega id da uri
		
		User user = repository.findByEmail(dto.getEmail());
		
		if(user != null & user.getId() != userId) 
			list.add(new FieldMessage("email", "O email digitado já está cadastrado"));
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}


}