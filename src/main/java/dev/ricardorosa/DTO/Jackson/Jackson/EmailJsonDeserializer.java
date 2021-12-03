package dev.ricardorosa.DTO.Jackson.Jackson;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.entity.User;
import dev.ricardorosa.DTO.Jackson.repository.UserRepository;

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
		email.setAddress(root.get("address").asText());
		
		String userName = root.get("user").asText();
		User incomingUser = userRepository.findByName(userName).orElse(null);
		email.setUser(incomingUser);
		
		return email;
	}

}
