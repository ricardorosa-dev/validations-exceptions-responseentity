package dev.ricardorosa.DTO.Jackson.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.entity.User;
import dev.ricardorosa.DTO.Jackson.exceptions.AlreadyExistsException;
import dev.ricardorosa.DTO.Jackson.exceptions.NotFoundException;
import dev.ricardorosa.DTO.Jackson.exceptions.WrongEmailFormatException;
import dev.ricardorosa.DTO.Jackson.exceptions.IncompleteBodyException;
import dev.ricardorosa.DTO.Jackson.repository.EmailRepository;
import dev.ricardorosa.DTO.Jackson.repository.UserRepository;

@Service
public class EmailService {
	
private final EmailRepository repository;
	
	@Autowired
	public EmailService(EmailRepository repository) {
		this.repository = repository;
	}
	
	public List<Email> findAll() {
		return repository.findAll();
	}
	
	public Email findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException("email", "id", id.toString()));
	}
	
	public Email save(Email newEmail) {
		if (newEmail.getAddress() == null
			|| newEmail.getUser() == null) {
			throw new IncompleteBodyException("email", "'address' and 'user'");
		}
		
		Pattern emailPattern = Pattern.compile(
				".+@.+\\.[a-z0-9]{2,4}(\\.[a-z0-9]{2,3})?", 
				Pattern.CASE_INSENSITIVE);
		Matcher emailMatcher = emailPattern.matcher(newEmail.getAddress());
		if (emailMatcher.find() == false) {
			throw new WrongEmailFormatException();
		}
		
		Email exists = repository.findByAddress(newEmail.getAddress());
		if (exists != null) {
			throw new AlreadyExistsException("email", "address", exists.getAddress());
		}
		
		return repository.save(newEmail);
	}
		
	public Email update(Long id, Email updateEmail) {
		if (updateEmail.getAddress() == null
			|| updateEmail.getUser() == null) {
			throw new IncompleteBodyException("email", "'address' and 'user'");
		}
		
		Email foundEmail = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("email", "id", id.toString()));
		
		foundEmail.setAddress(updateEmail.getAddress());
		foundEmail.setUser(updateEmail.getUser());
		
		return repository.save(foundEmail);
	}
	
	public void delete(Long id) {
		Email foundEmail = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("email", "id", id.toString()));
		
		repository.delete(foundEmail);
	}

}
