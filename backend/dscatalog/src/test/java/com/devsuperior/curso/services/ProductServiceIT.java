package com.devsuperior.curso.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.repositories.ProductRepository;
import com.devsuperior.curso.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	public void setUp() throws Exception{
		existingId =1L;
		nonExistingId = 100L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		
		Assertions.assertEquals(countTotalProducts - 1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExcepionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class,() ->{
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPage0Size10() {
		
		PageRequest pageRquest = PageRequest.of(0, 10); // Página 0 com 10 elementos
		
		Page<ProductDTO> page = service.findAllPaged(pageRquest);
		
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals(0, page.getNumber());
		Assertions.assertEquals(10, page.getSize());
		Assertions.assertEquals(25, page.getTotalElements());
		
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenIdDoesNotExist() {
		
		PageRequest pageRquest = PageRequest.of(50, 10); // Página 0 com 10 elementos
		
		Page<ProductDTO> page = service.findAllPaged(pageRquest);
		
		Assertions.assertTrue(page.isEmpty());
		
	}
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortedByName() {
		
		PageRequest pageRquest = PageRequest.of(0, 10,Sort.by("name")); // Página 0 com 10 elementos
		
		Page<ProductDTO> page = service.findAllPaged(pageRquest);
		
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals("Macbook Pro", page.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", page.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", page.getContent().get(2).getName());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
