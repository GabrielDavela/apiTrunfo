package br.senai.sc.superanimais.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String name;
    @Positive
    private int lifeTime;
    @Positive
    private double weight;
    @Positive
    private double length;
    @Positive
    private int paws;
    @Positive
    private int gestation;
    private String imageRef;

}
