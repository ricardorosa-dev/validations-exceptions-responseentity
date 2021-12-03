package dev.ricardorosa.DTO.Jackson.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String name;
	private String dateOfBirth;
	private List<String> emails;

}
