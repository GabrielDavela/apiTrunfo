package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.LoginReqDTO;
import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/person")
@CrossOrigin
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody @Valid PersonDTO personDTO){
        return ResponseEntity.ok(personService.create(personDTO));
    }

    @GetMapping
    public ResponseEntity<List<Person>> listAll(){
        return ResponseEntity.ok(personService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> listOne(@PathVariable Long id){
        return ResponseEntity.ok(personService.listOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@RequestBody @Valid PersonDTO personDTO, @PathVariable Long id) {
        return ResponseEntity.ok(personService.update(personDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id){
        return ResponseEntity.ok(personService.delete(id));
    }

    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestBody LoginReqDTO loginReqDTO) {
        Person person = personService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return ResponseEntity.ok(person);
    }

}
