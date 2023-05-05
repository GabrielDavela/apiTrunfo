package br.senai.sc.superanimais.model.dto;

import br.senai.sc.superanimais.model.enums.Personas;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
//    @Enumerated(EnumType.STRING)
//    private Personas personas;

}
