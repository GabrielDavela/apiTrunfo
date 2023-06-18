package br.senai.sc.superanimais.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int lifeTime;
    private double weight;
    private double length;
    private int paws;
    private int gestation;
    private String profileImage;

//    @OneToOne
//    private Person person;
    // Talvez não precise dessa relação

}
