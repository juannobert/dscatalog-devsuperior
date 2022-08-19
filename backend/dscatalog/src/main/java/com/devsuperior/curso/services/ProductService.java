package com.devsuperior.curso.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.curso.dto.CategoryDTO;
import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.entities.Product;
import com.devsuperior.curso.repositories.CategoryRepository;
import com.devsuperior.curso.repositories.ProductRepository;
import com.devsuperior.curso.services.exceptions.DataBaseException;
import com.devsuperior.curso.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService{

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable,Long categoryId,String name) {
		List<Category> categories = (categoryId == 0 ? null : Arrays.asList(categoryRepository.getReferenceById(categoryId)));
		Page<Product> page = repository.find(categories,pageable,name);
		repository.findProductsWithCategories(page.getContent());
		return page.map(x -> new ProductDTO(x,x.getCategories()));
	}

	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity,entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity,entity.getCategories());
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity,entity.getCategories());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Not exist entity with id " + id);
		}

	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Not exist entity with id " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Data integrity violation");
		}
		
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);			
		}
	}	

}
