package br.senai.sc.superanimais.security.repository;

import br.senai.sc.superanimais.security.model.PersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDetailsRepository extends JpaRepository<PersonDetails, Long> {

    PersonDetails findByPerson_Email(String email);
}
