package br.senai.sc.superanimais.service;

import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.model.entity.Room;
import br.senai.sc.superanimais.model.exception.RoomNotFoundException;
import br.senai.sc.superanimais.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final PersonService personService;

    private Room createRoom() {
        return roomRepository.save(new Room());
    }

    public Room createRoom(Long idPlayer) {
        Room room = createRoom();
        join(room.getGameId(), idPlayer);
        return room;
    }

    public Room listOne(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Sala não existe"));
    }

    public void join(Long idRoom, Long idPlayer) {
        Room room = listOne(idRoom);
        if (room.getPlayers().size() == 2) {
            throw new RuntimeException("Sala cheia");
        }

        Person person = personService.listOne(idPlayer);
        person.setRoom_id(room);
        personService.save(person);

        room.getPlayers().add(person);
        roomRepository.save(room);
    }


}
