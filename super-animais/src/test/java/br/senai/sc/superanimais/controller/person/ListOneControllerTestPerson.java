package br.senai.sc.superanimais.controller.person;

import br.senai.sc.superanimais.controller.PersonController;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class ListOneControllerTestPerson {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService PersonService;

    @Test
    public void listOneControllerPersonTest() throws Exception {
        Long id = 1L; // trocar para long

        Person person = new Person(1L, "Gabriel", "Gabriel@gmail.com", "123");

        when(PersonService.listOne(id))
                .thenReturn(person);

        mockMvc.perform(get("/person/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(person));

    }
}
