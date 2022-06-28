package com.devsuperior.curso.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	private CategoryService service;
	
	@GetMapping()
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = service.findAll();
		return ResponseEntity.ok(list);
	}
}
