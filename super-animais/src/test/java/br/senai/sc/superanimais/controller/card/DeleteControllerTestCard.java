package br.senai.sc.superanimais.controller.card;

import br.senai.sc.superanimais.controller.CardController;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class DeleteControllerTestCard {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void deleteControllerTest() throws Exception {
        long id = 1L;
        Card card = new Card(1L, "Gabriel", 1, 1, 2, 2,
                2, "https://example.com/image1", null);

        when(cardService.delete(id))
                .thenReturn(card);

        mockMvc.perform(delete("/card/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(card));

    }
}
