package dev.ricardorosa.DTO.Jackson.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ricardorosa.DTO.Jackson.dto.UserDTO;
import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.entity.User;
import dev.ricardorosa.DTO.Jackson.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserRepository repository;
	
	@Autowired
	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public List<UserDTO> findAll() {
		return repository.findAll().stream()
				.map(this::toUserDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public UserDTO findById(Long id) {
		return this.toUserDTO(repository.findById(id)
				.orElseThrow(RuntimeException::new));
	}
	
	@PostMapping
	public UserDTO save(@RequestBody User newUser) {
		return this.toUserDTO(repository.save(newUser));
	}
	
	private UserDTO toUserDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setName(user.getName());
		
		List<String> userEmails = new ArrayList<>();
		for (Email email : user.getEmails()) {
			userEmails.add(email.getAddress());
		}
		
		dto.setEmails(userEmails);
		
		return dto;
	}

}
