package com.devsuperior.curso.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.curso.entities.Category;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@GetMapping()
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L,"Eletronics"));
		list.add(new Category(2L,"Books"));
		return ResponseEntity.ok(list);
	}
}
