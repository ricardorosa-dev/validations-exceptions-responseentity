package dev.ricardorosa.Validations.jackson;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import dev.ricardorosa.Validations.entity.Email;
import dev.ricardorosa.Validations.entity.User;
import dev.ricardorosa.Validations.exceptions.NotFoundException;
import dev.ricardorosa.Validations.repository.UserRepository;

public class EmailJsonDeserializer extends JsonDeserializer<Email>{
	
	private final UserRepository userRepository;
	
	@Autowired
	public EmailJsonDeserializer(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	public Email deserialize(JsonParser p, DeserializationContext ctxt) 
			throws IOException, JacksonException {

		ObjectCodec codec = p.getCodec();
		JsonNode root = codec.readTree(p);
		
		Email email = new Email();
		
		if (root.get("address") != null) {
			email.setAddress(root.get("address").asText());
		}
		
		if (root.get("user") != null) {
			String userName = root.get("user").asText();
			
			User incomingUser = userRepository.findByName(userName)
					.orElseThrow(() -> new NotFoundException("user", "name", userName));
			email.setUser(incomingUser);
		}
		
		return email;
	}

}
