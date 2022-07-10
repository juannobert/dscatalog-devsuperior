package com.devsuperior.curso.tests.factory;

import com.devsuperior.curso.dto.CategoryDTO;
import com.devsuperior.curso.entities.Category;

public class CategoryFactory {
	
	public static Category createCategory() {
		return new Category(1L,"Books");
	}
	
	public static CategoryDTO createCategoryDTO() {
		return new CategoryDTO(createCategory());
		
	}
}
