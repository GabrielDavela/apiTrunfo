package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card create(CardDTO cardDTO) {
        Card card = new Card();
        BeanUtils.copyProperties(cardDTO, card);
        return cardRepository.save(card);
    }

    public Page<Card> listAll() {
        Pageable pageable = PageRequest.of(0, 5);
        return cardRepository.findAll(pageable);
    }

    public Card listOne(long id) {
        Optional<Card> card = cardRepository.findById(id);
        if(card.isPresent()){
            return card.get();
        }
        throw new RuntimeException("Objeto não encontrado");
    }

    public Card delete(long id){
        Card card = listOne(id);
        cardRepository.delete(card);
        return card;
    }

    public Card update(CardDTO cardDTO, long id) {
        Card card = listOne(id);
        BeanUtils.copyProperties(cardDTO, card);
        return cardRepository.save(card);
    }

}
