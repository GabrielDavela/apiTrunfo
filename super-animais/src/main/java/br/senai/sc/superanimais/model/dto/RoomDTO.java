package br.senai.sc.superanimais.model.dto;

import br.senai.sc.superanimais.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    List<Person> personList;

}
