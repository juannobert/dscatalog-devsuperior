package com.devsuperior.curso.tests;

import java.time.Instant;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone12", "Good Phone", 800.0, "https://img.com/img.png", Instant.now());
		product.getCategories().add(new Category(1L, "Electronics"));
		return product;		
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
