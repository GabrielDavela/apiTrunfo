package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.entity.Game;
import br.senai.sc.superanimais.model.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {

    public Game createGame(Person person) {
        Game game = new Game();
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(person);
        return game;
    }



}
