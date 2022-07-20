package com.devsuperior.curso.dto;

import java.io.Serializable;

import com.devsuperior.curso.entities.Category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;


	@EqualsAndHashCode.Include
	private Long id;
	
	private String name;
	
	public CategoryDTO(Category entity) {
		this.name = entity.getName();
		this.id = entity.getId();
	}

	public CategoryDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}	
}
