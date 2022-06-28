package com.devsuperior.curso.dto;

import com.devsuperior.curso.entities.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryDTO {
	
	private Long id;
	
	private String name;
	
	public CategoryDTO(Category entity) {
		this.name = entity.getName();
		this.id = entity.getId();
	}
	
	
}
