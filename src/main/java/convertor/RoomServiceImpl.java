package convertor;

import entity.RoomEntity;
import repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomEntity findById(long theId) {

        Optional<RoomEntity> result = roomRepository.findById(theId);

        RoomEntity theRoom = null;

        if (result.isPresent()) {
            theRoom = result.get();
        } else {

            return null;
        }

        return theRoom;

    }

    @Override
    public List<RoomEntity> findAll() {
        return (List<RoomEntity>) roomRepository.findAll();
    }

}
