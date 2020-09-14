package hotel.booking.controller;

import hotel.booking.HotelServiceDAO;
import hotel.booking.model.ApiResponse;
import hotel.booking.model.Hotel;
import hotel.booking.model.SearchResults;
import hotel.booking.util.HotelAppErrorCodes;
import hotel.booking.util.HotelAppErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    @Autowired
    HotelServiceDAO hotelServiceDAO;

    @RequestMapping(value = "/search/term", method = RequestMethod.GET)
    public ResponseEntity<Object> getSearchTerm(
            @RequestParam(required = false, name = "term", defaultValue = "") String term) {
        List<String> searchTermList = hotelServiceDAO.searchTerm(term);
        if (searchTermList == null || searchTermList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HotelAppErrorCodes.NOT_FOUND, HotelAppErrorMessage.NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(searchTermList, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/hotels", method = RequestMethod.GET)
    public ResponseEntity<Object> getHotels(
            @RequestParam(required = false, name = "term", defaultValue = "") String term,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortcolumn,
            @RequestParam(required = false, name = "sorttype", defaultValue = "asc") String sorttype) {

        List<Hotel> searchResultList = hotelServiceDAO.searchHotels(term, offset, limit, sortcolumn, sorttype);
        if (searchResultList == null || searchResultList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HotelAppErrorCodes.NOT_FOUND, HotelAppErrorMessage.NOT_FOUND), HttpStatus.OK);
        }

        int count = hotelServiceDAO.getCount(term);
        SearchResults searchResults = new SearchResults();
        searchResults.setSearchResults(searchResultList);
        searchResults.setTotalCount(count);

        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/hotels/category", method = RequestMethod.GET)
    public ResponseEntity<Object> categorySearch(
            @RequestParam(required = false, name = "categorylist", defaultValue = "") String categorylist,
            @RequestParam(required = false, name = "offset", defaultValue = "0") int offset,
            @RequestParam(required = false, name = "limit", defaultValue = "10") int limit,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortcolumn,
            @RequestParam(required = false, name = "sorttype", defaultValue = "asc") String sorttype) {

        List<Hotel> searchResultList = hotelServiceDAO.categorySearch(categorylist, offset, limit, sortcolumn, sorttype);
        if (searchResultList == null || searchResultList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HotelAppErrorCodes.NOT_FOUND, HotelAppErrorMessage.NOT_FOUND), HttpStatus.OK);
        }

        int count = hotelServiceDAO.getCategoryCount(categorylist);
        SearchResults searchResults = new SearchResults();
        searchResults.setSearchResults(searchResultList);
        searchResults.setTotalCount(count);

        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }


    @RequestMapping(value = "/search/hotels/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getHotelById(
            @PathVariable("id") String id) {
        List<Hotel> searchResultList = hotelServiceDAO.searchHotelById(id);
        if (searchResultList == null || searchResultList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HotelAppErrorCodes.NOT_FOUND, HotelAppErrorMessage.NOT_FOUND), HttpStatus.OK);
        }
        SearchResults searchResults = new SearchResults();
        searchResults.setSearchResults(searchResultList);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public ResponseEntity<Object> newUser(
            @RequestParam(required = true, name = "fname", defaultValue = "") String fname,
            @RequestParam(required = true, name = "lname", defaultValue = "") String lname,
            @RequestParam(required = true, name = "emailTxt", defaultValue = "") String emailTxt,
            @RequestParam(required = true, name = "pwd", defaultValue = "") String pwd) {
        hotelServiceDAO.createUser(fname, lname, emailTxt, pwd);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
