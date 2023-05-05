package br.senai.sc.superanimais.controller.card;

import br.senai.sc.superanimais.controller.CardController;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CardController.class)
public class ListAllControllerTestCard {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CardService cardService;

    @Test
    public void listAllControllerTest() throws Exception {
        Card card1 = new Card(1L, "Gabriel", 1, 1.0, 2.0, 2, 2, "https://example.com/image1", null);
        Card card2 = new Card(2L, "Aline", 2, 2.0, 3.0, 3, 3, "https://example.com/image2", null);
        List<Card> cards = List.of(card1, card2);

        when(cardService.listAll())
                .thenReturn(cards);

        mockMvc.perform(get("/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value(cards.get(0)))
                .andExpect(jsonPath("$[1]").value(cards.get(1)));
    }

}
