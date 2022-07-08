package com.devsuperior.curso.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.curso.repositories.ProductRepository;
import com.devsuperior.curso.services.exceptions.DataBaseException;
import com.devsuperior.curso.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository repository;
	
	private Long existingId;
	
	private Long nonExistingId;
	
	private Long dependentId;
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 5L;
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
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
	
	
	
	
}
