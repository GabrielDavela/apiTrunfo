package br.senai.sc.superanimais.controller.card;

import br.senai.sc.superanimais.controller.CardController;
import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class UpdateControllerTestCard {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void editControllerTest() throws Exception {
        long id = 1L;
        Card card = new Card(1L, "Gabriel", 2, 1, 2, 2,
                2, "https://example.com/image1", null);

        CardDTO cardDTO = new CardDTO("Gabriel", 2, 1, 2, 2,
                2, "https://example.com/image1");

        when(cardService.uptade(cardDTO, id))
                .thenReturn(card);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(cardDTO);

        mockMvc.perform(put("/card/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Gabriel")))
                .andExpect(jsonPath("$.lifeTime", equalTo(2)))
                .andExpect(jsonPath("$.weight", equalTo(1.0)))
                .andExpect(jsonPath("$.length", equalTo(2.0)))
                .andExpect(jsonPath("$.paws", equalTo(2)))
                .andExpect(jsonPath("$.gestation", equalTo(2)))
                .andExpect(jsonPath("$.image", equalTo("https://example.com/image1")));
    }

}
