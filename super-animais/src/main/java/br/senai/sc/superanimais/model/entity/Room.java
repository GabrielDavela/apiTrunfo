package br.senai.sc.superanimais.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @OneToMany(mappedBy = "room_id", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Person> players;

}
