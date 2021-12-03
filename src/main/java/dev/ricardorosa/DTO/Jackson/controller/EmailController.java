package dev.ricardorosa.DTO.Jackson.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ricardorosa.DTO.Jackson.dto.EmailDTO;
import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.repository.EmailRepository;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	private EmailRepository repository;
	
	@Autowired
	public EmailController(EmailRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public List<EmailDTO> findAll() {
		return repository.findAll().stream()
				.map(email -> this.toEmailDTO(email))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public EmailDTO findById(Long id) {
		return this.toEmailDTO(repository.findById(id)
				.orElseThrow(RuntimeException::new));
	}
	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody Email newEmail) {
		if (newEmail.getUser() == null) {
			return new ResponseEntity<>(
					"The user associated with this name doesn't exist.", 
					HttpStatus.NOT_FOUND);
		}
		
		repository.save(newEmail);
		return new ResponseEntity<>("Email created.", HttpStatus.CREATED);
	}
	
	EmailDTO toEmailDTO(Email email) {
		EmailDTO dto = new EmailDTO();
		dto.setAddress(email.getAddress());
		dto.setUser(email.getUser().getName());
		
		return dto;
	}

}
