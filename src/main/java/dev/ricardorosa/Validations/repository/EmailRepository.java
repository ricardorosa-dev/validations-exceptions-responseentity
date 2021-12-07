package dev.ricardorosa.Validations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ricardorosa.Validations.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long>{
	
	Email findByAddress(String address);

}
