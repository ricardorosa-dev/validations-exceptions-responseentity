package dev.ricardorosa.DTO.Jackson.Jackson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import dev.ricardorosa.DTO.Jackson.entity.Email;
import dev.ricardorosa.DTO.Jackson.entity.User;

public class UserJsonDeserializer extends JsonDeserializer<User>{

	@Override
	public User deserialize(JsonParser p, DeserializationContext ctxt) 
			throws IOException, JacksonException {

		ObjectCodec codec = p.getCodec();
		JsonNode root = codec.readTree(p);
		
		User user = new User();
		user.setName(root.get("name").asText());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		user.setDateOfBirth(LocalDate.parse(root.get("date_of_birth").asText(), formatter));
		
		List<Email> Emails = new ArrayList<>();
		user.setEmails(Emails);
		
		return user;
	}

}
