package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.repository.PersonRepository;
import br.senai.sc.superanimais.security.model.PersonDetails;
import br.senai.sc.superanimais.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private JpaService jpaService;

    public Person create(PersonDTO personDTO){
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        person.setPassword(encoder.encode(person.getPassword()));

        PersonDetails pd = new PersonDetails();
        pd.setPerson(person);
        pd.setEnabled(true);
        pd.setAuthorities(new ArrayList<>());
        pd.setAccountNonLocked(true);
        pd.setCredentialsNonExpired(true);
        pd.setAccountNonExpired(true);

        return jpaService.create(pd).getPerson();
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public List<Person> listAll() {
        return personRepository.findAll();
    }

    public Person listOne(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            return person.get();
        }
        throw new RuntimeException("Pessoa não existe");
    }

    public Person update(PersonDTO personDTO, Long id){
        Person person = listOne(id);
        BeanUtils.copyProperties(personDTO, person);
        return personRepository.save(person);
    }

    public Person delete(Long id) {
        Person person = listOne(id);
        personRepository.delete(person);
        return person;
    }

    public Person login(String email, String password) {
        Optional<Person> optionalPerson = personRepository.findByEmailAndPassword(email, password);
        if(optionalPerson.isPresent()) {
            return  optionalPerson.get();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

}
