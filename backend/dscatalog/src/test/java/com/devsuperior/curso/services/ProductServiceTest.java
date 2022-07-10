package com.devsuperior.curso.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.entities.Product;
import com.devsuperior.curso.repositories.CategoryRepository;
import com.devsuperior.curso.repositories.ProductRepository;
import com.devsuperior.curso.services.exceptions.DataBaseException;
import com.devsuperior.curso.services.exceptions.ResourceNotFoundException;
import com.devsuperior.curso.tests.factory.CategoryFactory;
import com.devsuperior.curso.tests.factory.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private ProductRepository repository;
	
	private Long existingId;
	
	private Long nonExistingId;
	
	private Long dependentId;
	
	private Product product;
	
	private Category category;
	
	private PageImpl<Product> page;
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 5L;
		product = ProductFactory.createProduct();
		page = new PageImpl<>(List.of(product));
		category = CategoryFactory.createCategory();
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		Mockito.doNothing().when(repository).deleteById(existingId);
		
		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);
		
		Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() ->{
			productService.delete(existingId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class,() ->{
			productService.delete(nonExistingId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DataBaseException.class, () ->{
			productService.delete(dependentId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO product = productService.findById(existingId);
		
		Assertions.assertNotNull(product);
		
	}
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			productService.findById(nonExistingId);
		});
		
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() ->{
			ProductDTO product = new ProductDTO(ProductFactory.createProduct());
			productService.update(existingId, product);
		});
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			ProductDTO product = ProductFactory.createProductDTO();
			productService.update(nonExistingId, product);
		});
	}
	

	
	
	
}
