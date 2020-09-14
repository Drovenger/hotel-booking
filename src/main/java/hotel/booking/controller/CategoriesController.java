package hotel.booking.controller;

import hotel.booking.HotelServiceDAO;
import hotel.booking.model.ApiResponse;
import hotel.booking.model.Category;
import hotel.booking.util.HotelAppErrorCodes;
import hotel.booking.util.HotelAppErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {

	@Autowired
	HotelServiceDAO hotelServiceDAO;

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<Object> getCategories() {
		Category category = hotelServiceDAO.getCategories();
		if (category == null || category.getCategories().isEmpty()) {
			return new ResponseEntity<>(new ApiResponse(HotelAppErrorCodes.NOT_FOUND, HotelAppErrorMessage.NOT_FOUND), HttpStatus.OK);
		}
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
}
