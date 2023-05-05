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

    @NotNull
    private String name;
    @NotNull
    @Positive
    private int lifeTime;
    @NotNull
    @Positive
    private double weight;
    @NotNull
    @Positive
    private double length;
    @NotNull
    @Positive
    private int paws;
    @NotNull
    @Positive
    private int gestation;
    @NotNull
    private String image;

}
