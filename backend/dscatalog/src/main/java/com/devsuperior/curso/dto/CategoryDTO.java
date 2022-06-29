package com.devsuperior.curso.dto;

import java.io.Serializable;

import com.devsuperior.curso.entities.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	public CategoryDTO(Category entity) {
		this.name = entity.getName();
		this.id = entity.getId();
	}
	
	
}
