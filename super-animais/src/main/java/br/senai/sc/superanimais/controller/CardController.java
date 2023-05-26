package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.CardDTO;
import br.senai.sc.superanimais.model.entity.Card;
import br.senai.sc.superanimais.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/card")
@CrossOrigin
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody CardDTO cardDTO){
        return ResponseEntity.ok(cardService.create(cardDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Card>> listAll(){
        return ResponseEntity.ok(cardService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> listOne(@PathVariable long id){
        return ResponseEntity.ok(cardService.listOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> editCard(@RequestBody CardDTO cardDTO, @PathVariable long id) {
        return ResponseEntity.ok(cardService.update(cardDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Card> delete(@PathVariable long id) {
        return ResponseEntity.ok(cardService.delete(id));
    }

}
