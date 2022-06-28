package com.devsuperior.curso.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.curso.dto.CategoryDTO;
import com.devsuperior.curso.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok(list);
	}
}
