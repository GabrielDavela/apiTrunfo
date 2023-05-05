package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person create(PersonDTO personDTO){
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
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



}
