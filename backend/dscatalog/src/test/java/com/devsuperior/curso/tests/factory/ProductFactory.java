package com.devsuperior.curso.tests.factory;

import java.time.Instant;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L,"Eletronics"));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product,product.getCategories());
		
	}
	
}
