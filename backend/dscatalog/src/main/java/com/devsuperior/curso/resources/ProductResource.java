package com.devsuperior.curso.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping()
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			Pageable pageable){
		Page<ProductDTO> list = service.findAllPaged(pageable,categoryId,name.trim());
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody @Valid ProductDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id,@RequestBody @Valid ProductDTO dto){
		dto = service.update(id,dto);
		return ResponseEntity.ok(dto);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
}
