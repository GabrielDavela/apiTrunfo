package br.senai.sc.superanimais.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRoom {

    private Person player1;
    private Person player2;

    

}
