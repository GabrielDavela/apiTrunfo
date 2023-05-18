package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.entity.Room;
import br.senai.sc.superanimais.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room create(Room room) {
        return roomRepository.save(room);
    }

}
