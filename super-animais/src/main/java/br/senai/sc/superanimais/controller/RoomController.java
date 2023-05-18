package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.RoomDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.model.entity.Room;
import br.senai.sc.superanimais.service.PersonService;
import br.senai.sc.superanimais.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final PersonService personService;

    @MessageMapping("/room.createRoom")
    @SendTo("/topic/publicRoom")
    public Room createRoom(@Payload RoomDTO roomdto) {
        Room room = new Room();
        BeanUtils.copyProperties(roomdto, room);
        room = roomService.create(room);

        for(Person person: roomdto.getPersonList()) {
            person.setRoom_id(room);
            personService.create(person);
        }
        return room;
    }

//    @MessageMapping("/room.enterRoom")
//    @SendTo("/topic/publicRoom")
//    public Room addUser(@Payload Room room, SimpMessageHeaderAccessor headerAccessor) {
//        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("player2", room.getPlayer2());
//        return room;
//    }

}