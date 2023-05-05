package br.senai.sc.superanimais.model.entity;

import br.senai.sc.superanimais.model.enums.Personas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
//    private ArrayList<Card> cards = new ArrayList<>();
//    @Enumerated(EnumType.STRING)
//    private Personas personas;
}
