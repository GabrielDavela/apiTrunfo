package br.senai.sc.superanimais.repository;

import br.senai.sc.superanimais.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmailAndPassword(String email, String password);

}
