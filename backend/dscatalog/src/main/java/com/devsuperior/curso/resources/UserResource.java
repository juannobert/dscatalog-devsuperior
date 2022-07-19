package com.devsuperior.curso.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.curso.dto.UserDTO;
import com.devsuperior.curso.dto.UserInsertDTO;
import com.devsuperior.curso.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping()
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
		Page<UserDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserInsertDTO dto){
		UserDTO newUserDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newUserDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newUserDto);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody UserDTO dto){
		dto = service.update(id,dto);
		return ResponseEntity.ok(dto);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
}
