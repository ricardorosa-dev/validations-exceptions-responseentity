package dev.ricardorosa.DTO.Jackson.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import dev.ricardorosa.DTO.Jackson.entity.User;
import dev.ricardorosa.DTO.Jackson.exceptions.AlreadyExistsException;
import dev.ricardorosa.DTO.Jackson.exceptions.IncompleteBodyException;
import dev.ricardorosa.DTO.Jackson.exceptions.IncompleteBodyException;
import dev.ricardorosa.DTO.Jackson.exceptions.NotFoundException;
import dev.ricardorosa.DTO.Jackson.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	
	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException("user", "id", id.toString()));
	}
	
	public User save(User newUser) {
		if (newUser.getName() == null
			|| newUser.getDateOfBirth() == null) {
			throw new IncompleteBodyException("user", "'name' and 'dateOfBirth'");
		}
		
		User exists = repository.findByName(newUser.getName())
				.orElseThrow(() -> new NotFoundException("user", "name", newUser.getName()));
		if (exists != null) {
			throw new AlreadyExistsException("user", "name", exists.getName());
		}
		
		return repository.save(newUser);
	}
		
	public User update(Long id, User updateUser) {
		if (updateUser.getName() == null
				|| updateUser.getDateOfBirth() == null) {
				throw new IncompleteBodyException("user", "'name' and 'dateOfBirth'");
			}
		
		User foundUser = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("user", "id", id.toString()));
		
		foundUser.setName(updateUser.getName());
		foundUser.setDateOfBirth(updateUser.getDateOfBirth());
		
		return repository.save(foundUser);
	}
	
	public void delete(Long id) {
		User foundBand = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("user", "id", id.toString()));
//		System.out.println(foundBand);
		
		repository.delete(foundBand);
	}

}
