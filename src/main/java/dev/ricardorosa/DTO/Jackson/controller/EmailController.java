package dev.ricardorosa.DTO.Jackson.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ricardorosa.DTO.Jackson.dto.EmailDTO;
import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.exceptions.NotFoundException;
import dev.ricardorosa.DTO.Jackson.repository.EmailRepository;
import dev.ricardorosa.DTO.Jackson.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	private EmailService service;
	
	@Autowired
	public EmailController(EmailService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<EmailDTO>> findAll() {
		List<EmailDTO> allEmails = service.findAll().stream()
				.map(email -> this.toEmailDTO(email))
				.collect(Collectors.toList());
		
		return new ResponseEntity<>(allEmails, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmailDTO> findById(@PathVariable("id") Long id) {
		EmailDTO email = this.toEmailDTO(service.findById(id));
		
		return new ResponseEntity<>(email, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EmailDTO> save(@RequestBody Email newEmail) {
		EmailDTO email = this.toEmailDTO(service.save(newEmail));
		
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmailDTO> update(
			@PathVariable("id") Long id, 
			@RequestBody Email updateEmail) {
		EmailDTO email = this.toEmailDTO(service.update(id, updateEmail));
		
		return new ResponseEntity<>(email, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		service.delete(id);
		
		return new ResponseEntity<>("Email deleted.", HttpStatus.NO_CONTENT);
	}
	
	EmailDTO toEmailDTO(Email email) {
		EmailDTO dto = new EmailDTO();
		dto.setAddress(email.getAddress());
		dto.setUser(email.getUser().getName());
		
		return dto;
	}

}
