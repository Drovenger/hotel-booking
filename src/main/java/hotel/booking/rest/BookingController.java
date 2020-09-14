package hotel.booking.rest;

import hotel.booking.AccessTokenMapper;
import hotel.booking.HotelServiceDAO;
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
    HotelServiceDAO hotelServiceDAO;

    @RequestMapping(value = "/book/confirm", method = RequestMethod.GET)
    public ResponseEntity<Object> bookingHotel(@RequestParam(required = true, name = "rooms", defaultValue = "") String rooms, @RequestParam(required = true, name = "id", defaultValue = "") String id,
                                               @RequestParam(required = true, name = "date", defaultValue = "") String date) {
        AccessTokenMapper accessTokenMapper = (AccessTokenMapper) ((OAuth2AuthenticationDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails())
                .getDecodedDetails();
        hotelServiceDAO.creteBooking(rooms, id, date, accessTokenMapper.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
