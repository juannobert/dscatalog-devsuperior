package com.devsuperior.curso.resources.exceptions;

public class FieldMessage {
	
	private String fieldName;
	
	private String fieldMessage;
	
	public FieldMessage() {}

	public FieldMessage(String fieldName, String fieldMessage) {
		super();
		this.fieldName = fieldName;
		this.fieldMessage = fieldMessage;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldMessage() {
		return fieldMessage;
	}

	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
	
	
}
