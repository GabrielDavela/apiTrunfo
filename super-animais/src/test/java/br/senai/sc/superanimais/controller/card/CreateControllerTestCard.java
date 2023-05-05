package br.senai.sc.superanimais.controller.card;

import br.senai.sc.superanimais.controller.CardController;
import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.service.CardService;
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

@WebMvcTest(CardController.class)
public class CreateControllerTestCard {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void cadastrarControllerTest() throws Exception {
        CardDTO cardDTO = new CardDTO("Gabriel", 1, 1, 2, 2,
                2, "https://example.com/image1");
        Card card = new Card();
        BeanUtils.copyProperties(cardDTO, card);
        card.setId(1L);

        when(cardService.create(cardDTO))
                .thenReturn(card);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(card);

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(card));
    }


}
