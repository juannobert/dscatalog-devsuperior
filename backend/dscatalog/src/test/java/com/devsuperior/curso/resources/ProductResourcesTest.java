package com.devsuperior.curso.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.curso.dto.ProductDTO;
import com.devsuperior.curso.services.ProductService;
import com.devsuperior.curso.tests.factory.ProductFactory;



@WebMvcTest(ProductResource.class)
public class ProductResourcesTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	
	
	private PageImpl<ProductDTO> page;
	
	private ProductDTO productDTO;
	
	@BeforeEach
	public void setUp() throws Exception{
		productDTO = ProductFactory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		when(service.findAllPaged(any())).thenReturn(page);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions result = mockMvc.perform(get("/products")
					.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		
	}
}
