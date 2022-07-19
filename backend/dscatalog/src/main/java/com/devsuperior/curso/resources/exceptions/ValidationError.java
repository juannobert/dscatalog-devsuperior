package com.devsuperior.curso.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	
	private List<FieldMessage> fieldMessages = new ArrayList<>();
	
	public ValidationError() {}

	public void add(String fieldName,String fieldMessage) {
		fieldMessages.add(new FieldMessage(fieldName, fieldMessage));
	}
	public List<FieldMessage> getFieldMessages() {
		return fieldMessages;
	}
	
	

}
