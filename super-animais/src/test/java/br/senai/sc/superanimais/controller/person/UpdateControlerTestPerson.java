package br.senai.sc.superanimais.controller.person;

import br.senai.sc.superanimais.controller.PersonController;
import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class UpdateControlerTestPerson {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @Test
    public void editControllerTest() throws Exception {
        Long id = 1L;
        Person person = new Person(1L, "Gabriel", "Gabriel@gmail.com", "1234");

        PersonDTO personDTO = new PersonDTO("Gabriel", "Gabriel@gmail.com", "123");

        when(personService.update(personDTO, id))
                .thenReturn(person);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(personDTO);

        mockMvc.perform(put("/person/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Gabriel")))
                .andExpect(jsonPath("$.email", equalTo("Gabriel@gmail.com")))
                .andExpect(jsonPath("$.password", equalTo("1234")));
    }


}
