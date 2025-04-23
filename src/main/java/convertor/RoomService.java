package convertor;

import entity.RoomEntity;
import java.util.List;

public interface RoomService {

    public RoomEntity findById(long theId);

    public List<RoomEntity> findAll();

}
