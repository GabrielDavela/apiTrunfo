package br.senai.sc.superanimais.controller.person;

import br.senai.sc.superanimais.controller.CardController;
import br.senai.sc.superanimais.controller.PersonController;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class ListAllControllerTestPerson {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @Test
    public void listAllControllerPersonTest() throws Exception {
        Person person = new Person(1L, "Gabriel", "Gabriel@gmail.com", "123", null);
        Person person1 = new Person(2L, "Aline", "Aline@gmail.com", "123", null);
        List<Person> persons = List.of(person, person1);

        when(personService.listAll())
                .thenReturn(persons);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value(persons.get(0)))
                .andExpect(jsonPath("$[1]").value(persons.get(1)));
    }

}
