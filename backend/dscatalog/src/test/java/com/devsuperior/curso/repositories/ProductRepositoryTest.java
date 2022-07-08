package com.devsuperior.curso.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.curso.entities.Product;
import com.devsuperior.curso.tests.factory.ProductFactory;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	private long exintingId;
	private long nonExistingId;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExistingId = 100L;
		countTotalProducts = 25L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		productRepository.deleteById(exintingId);
		Optional<Product> obj = productRepository.findById(exintingId);

		Assertions.assertFalse(obj.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenDoesNotIdExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(nonExistingId);
		});
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		Product product = ProductFactory.createProduct();

		product.setId(null);
		product = productRepository.save(product);

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExist() {

		Optional<Product> obj = productRepository.findById(exintingId);

		Assertions.assertTrue(obj.isPresent());

	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNotExist() {

		Optional<Product> obj = productRepository.findById(nonExistingId);

		Assertions.assertFalse(obj.isPresent());
	}

}
