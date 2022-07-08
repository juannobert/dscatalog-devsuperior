package com.devsuperior.curso.repositories;

 import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.curso.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		long exintingId = 1L;
		
		productRepository.deleteById(exintingId);
		Optional<Product> obj = productRepository.findById(exintingId);
		
		Assertions.assertFalse(obj.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenDoesNotIdExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
			long nonExistingId = 100L;
			productRepository.deleteById(nonExistingId);
		});
	}
}
