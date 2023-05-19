package br.senai.sc.superanimais.controller;

import br.senai.sc.superanimais.model.dto.RoomDTO;
import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.model.entity.Room;
import br.senai.sc.superanimais.service.PersonService;
import br.senai.sc.superanimais.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final PersonService personService;

    @MessageMapping("/room.createRoom")
    @SendTo("/topic/publicRoom")
    public Room createRoom(@Payload RoomDTO dto) {
        return roomService.createRoom(dto.getPlayerId());
    }

//    @MessageMapping("/room.enterRoom")
//    @SendTo("/topic/publicRoom")
//    public Room addUser(@Payload Room room, SimpMessageHeaderAccessor headerAccessor) {
//        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("player2", room.getPlayer2());
//        return room;
//    }

}