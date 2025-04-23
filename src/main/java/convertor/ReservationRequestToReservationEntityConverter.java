package convertor;

import entity.ReservationEntity;
import entity.RoomEntity;
import model.request.ReservationRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ReservationRequestToReservationEntityConverter
        implements Converter<ReservationRequest, ReservationEntity> {
    private final ApplicationContext context;

    public ReservationRequestToReservationEntityConverter(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ReservationEntity convert(@NonNull ReservationRequest source) { // Added @NonNull

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());
        RoomService roomService = context.getBean(RoomServiceImpl.class);
        RoomEntity associatedRoom = roomService.findById(source.getRoomId());

        reservationEntity.setRoomEntity(associatedRoom);
        if (null != source.getId())
            reservationEntity.setId(source.getId());

        return reservationEntity;
    }
}