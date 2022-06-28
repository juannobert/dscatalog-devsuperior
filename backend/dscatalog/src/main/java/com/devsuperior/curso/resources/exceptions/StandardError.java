package com.devsuperior.curso.resources.exceptions;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StandardError {
	
	private Instant timestamp;
	
	private Integer status;
	
	private String error;
	
	private String path;
	
	private String message;
}
