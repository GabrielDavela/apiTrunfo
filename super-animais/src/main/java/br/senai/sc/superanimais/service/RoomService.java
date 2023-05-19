package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.model.entity.Room;
import br.senai.sc.superanimais.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final PersonService personService;

    private Room createRoom() {
        return roomRepository.save(new Room());
    }

    public Room createRoom(Long idPlayer) {
        Person person = personService.listOne(idPlayer);
        Room room = createRoom();
        person.setRoom_id(room);
        personService.create(person);
        return room;
    }

    public Room listOne(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent()){
            return room.get();
        }
        throw new RuntimeException("Sala não existe");
    }

    public void join(Long idRoom, Long idPlayer) {
        Room room = listOne(idRoom);
        if(room.getPlayers().size() == 2) {
            throw new RuntimeException("Sala cheia");
        }

    }

}
