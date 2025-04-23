package convertor;

import entity.ReservationEntity;

import java.util.List;

public interface ReservationService {
    public ReservationEntity findLast();

    public List<ReservationEntity> findAll();
}
