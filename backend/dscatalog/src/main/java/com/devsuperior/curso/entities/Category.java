package com.devsuperior.curso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {

	@EqualsAndHashCode.Include
	private Long id;
	
	private String name;
	
	
}
