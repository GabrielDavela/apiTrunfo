package br.senai.sc.superanimais.controller.person;

import br.senai.sc.superanimais.controller.PersonController;
import br.senai.sc.superanimais.model.dto.PersonDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class CreateControllerTestPerson {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @Test
    public void cadastrarControllerPersonTest() throws Exception {
        PersonDTO personDTO = new PersonDTO("Gabriel", "Gabriel@gmail.com", "123");
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        person.setId(1L);

        when(personService.create(personDTO))
                .thenReturn(person);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(person));
    }

}
