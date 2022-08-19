package com.devsuperior.curso.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.curso.entities.Category;
import com.devsuperior.curso.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value = "SELECT DISTINCT obj FROM Product obj "
			+ "INNER JOIN obj.categories cats "
			+ "WHERE (:category IS NULL OR :category IN cats) AND "
			+ "(UPPER(obj.name) LIKE UPPER(CONCAT('%',:name,'%')) )")
	Page<Product> find(Category category,Pageable pageable,String name);
}
