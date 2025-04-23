package rest;

import convertor.*;
import entity.ReservationEntity;
import entity.RoomEntity;
import model.request.ReservationRequest;
import model.response.ReservableRoomResponse;
import model.response.ReservationResponse;
import repository.ReservationRepository;
import repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*; // Using wildcard import

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {

    @Autowired
    ApplicationContext context;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ConversionService conversionService;

    @Autowired
    private RoomEntityToReservableRoomResponseConverter converter;

    @Autowired // Added Autowired for service beans
    private RoomService roomService;

    @Autowired // Added Autowired for service beans
    private ReservationService reservationService;

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam(value = "checkout") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            Pageable pageable) {

        // Use autowired service beans directly
        List<RoomEntity> allRooms = roomService.findAll();
        List<ReservationEntity> allReservations = reservationService.findAll();

        // Create a modifiable list for removal
        List<RoomEntity> availableRooms = new ArrayList<>(allRooms);

        for (ReservationEntity reservationEntity : allReservations) {
            LocalDate rcheckin = reservationEntity.getCheckin();
            LocalDate rcheckout = reservationEntity.getCheckout();
            // Check for overlap
            if (!(rcheckout.isBefore(checkin) || rcheckin.isAfter(checkout))) {
                availableRooms.remove(reservationEntity.getRoomEntity());
            }
        }
        // Create Page from the filtered list
        // Note: This simple pagination might not be efficient for large datasets
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), availableRooms.size());
        Page<RoomEntity> page = new PageImpl<>(availableRooms.subList(start, end), pageable, availableRooms.size());

        return page.map(converter::convert);
    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable Long roomId) {

        Optional<RoomEntity> result = roomRepository.findById(roomId);
        RoomEntity roomEntity = null;

        if (result.isPresent()) {
            roomEntity = result.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roomEntity, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {

        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        // Ensure reservationEntity is not null before saving
        if (reservationEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRepository.save(reservationEntity);

        // Retrieve the saved entity to get the generated ID (if needed, depends on
        // findLast logic)
        ReservationEntity savedReservationEntity = reservationService.findLast(); // Use service bean

        Optional<RoomEntity> result = roomRepository.findById(reservationRequest.getRoomId());
        RoomEntity roomEntity = null;

        if (result.isPresent()) {
            roomEntity = result.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Room not found
        }

        // Associate the saved reservation with the room
        roomEntity.addReservationEntity(savedReservationEntity);
        roomRepository.save(roomEntity);
        // Ensure the bidirectional relationship is set correctly
        savedReservationEntity.setRoomEntity(roomEntity);

        ReservationResponse reservationResponse = conversionService.convert(savedReservationEntity,
                ReservationResponse.class);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(
            @RequestBody ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(
            @PathVariable long reservationId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
