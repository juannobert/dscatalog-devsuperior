package com.devsuperior.curso.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

	@EqualsAndHashCode.Include
	private Long id;
	
	private String name;
	
	
}
