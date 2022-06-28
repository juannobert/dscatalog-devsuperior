package com.devsuperior.curso.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.curso.dto.CategoryDTO;
import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<CategoryDTO> findAll(){
		List<Category> list =  repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
}
