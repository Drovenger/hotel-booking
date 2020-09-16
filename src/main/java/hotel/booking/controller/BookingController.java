package hotel.booking.controller;

import hotel.booking.model.AccessTokenMapper;
import hotel.booking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "/book/confirm", method = RequestMethod.GET)
    public ResponseEntity<Object> bookingHotel(@RequestParam(name = "rooms", defaultValue = "") String rooms, @RequestParam(name = "id", defaultValue = "") String id,
                                               @RequestParam(name = "date", defaultValue = "") String date) {
        AccessTokenMapper accessTokenMapper = (AccessTokenMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails())
                .getDecodedDetails();
        hotelService.creteBooking(rooms, id, date, accessTokenMapper.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
