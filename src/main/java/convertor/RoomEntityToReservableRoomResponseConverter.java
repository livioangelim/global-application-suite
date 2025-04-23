package convertor;

import entity.RoomEntity;
import model.Links;
import model.Self;
import model.response.ReservableRoomResponse;
import rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse> {

	@Override
	public ReservableRoomResponse convert(@NonNull RoomEntity source) {

		ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
		if (null != source.getId())
			reservationResponse.setId(source.getId());
		reservationResponse.setRoomNumber(source.getRoomNumber());
		reservationResponse.setPrice(Integer.valueOf(source.getPrice()));

		Links links = new Links();
		Self self = new Self();
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
		links.setSelf(self);

		reservationResponse.setLinks(links);

		return reservationResponse;
	}

}
