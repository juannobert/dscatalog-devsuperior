package com.devsuperior.curso.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
}
