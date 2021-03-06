package hotel.booking.service;

import hotel.booking.model.Category;
import hotel.booking.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
class HotelServiceImpl implements HotelService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Category getCategories() {
        Collection<Map<String, Object>> rows = jdbcTemplate.queryForList("select categories from hotel");
        Category category = new Category();
        rows.stream().map((row) -> (String) row.get("categories")).forEach((ss) -> {
            if (ss != null && ss.trim().length() > 0) {
                String[] strArray = ss.split(",");
                for (String s : strArray) {
                    if (s != null && s.trim().length() > 0) {
                        s = s.trim().toUpperCase();
                        // Extracting the correct category name from the comma separator string
                        if (s.startsWith("AND "))
                            s = s.substring(s.indexOf("AND ") + 4);
                        // Filtering duplicate and invalid category name
                        if (!category.getCategories().contains(s) && !s.contains("SOLRJSON")
                                && !s.equalsIgnoreCase("RESORTRESORT_HOTEL")) {
                            category.addCategory(s);
                        }
                    }
                }
            }
        });
        return category;
    }

    @Override
    public List<String> searchTerm(String term) {
        Collection<Map<String, Object>> rows = jdbcTemplate
                .queryForList("select * from hotel where name like ? or address like ? or city like ? or province like ? limit 50",
                        "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%");
        List<String> termList = new ArrayList<>();
        rows.stream().map((row) -> row.get("name") + ", " + row.get("address") + ", " + row.get("city") + ", " + row.get("province") + ", " + row.get("postalcode")).forEach((ss) -> {
            if (ss.trim().length() > 0) {
                termList.add(ss);
            }
        });
        return termList;
    }

    @Override
    public List<Hotel> searchHotels(String term, int offset, int limit, String sortcolumn, String sorttype) {


        Collection<Map<String, Object>> rows = jdbcTemplate
                .queryForList("select * from hotel where categories like ? or name like ? or address like ? or city like ? or province like ? order by ? ? limit ?,?",
                        "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", sortcolumn, sorttype, offset, limit);
        List<Hotel> searchList = new ArrayList<>();
        rows.stream().map((row) -> {
            Hotel hotel = new Hotel();
            hotel.setAddress((String) row.get("address"));
            hotel.setCategories((String) row.get("categories"));
            hotel.setCity((String) row.get("city"));
            hotel.setCountry((String) row.get("country"));
            hotel.setId(String.valueOf(row.get("id")));
            hotel.setLatitude((String) row.get("latitude"));
            hotel.setLongitude((String) row.get("longitude"));
            hotel.setName((String) row.get("name"));
            hotel.setPostalcode((String) row.get("postalcode"));
            hotel.setProvince((String) row.get("province"));
            hotel.setRatings(String.valueOf(row.get("ratings")));
            hotel.setReviewText((String) row.get("review_text"));
            hotel.setReviewTitle((String) row.get("review_title"));
            hotel.setReviewUsername((String) row.get("review_username"));
            return hotel;
        }).forEach(searchList::add);
        return searchList;
    }

    @Override
    public int getCount(String term) {
        return jdbcTemplate
                .queryForObject("select count(id) from hotel where categories like ? or name like ? or address like ? or city like ? or province like ?",
                        new Object[]{"%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%", "%" + term + "%"}, Integer.class);
    }

    @Override
    public int getCategoryCount(String categorylist) {


        String sql = "select count(id) from hotel where id>0 and (";
        StringBuilder string = new StringBuilder();
        if (categorylist != null && categorylist.trim().length() > 0) {

            for (int i = 0; i < categorylist.split(",").length; i++) {

                if (i == (categorylist.split(",").length - 1)) {
                    string.append("categories like '%").append(categorylist.split(",")[i].replaceAll("!", "&")).append("%') ");
                } else {
                    string.append("categories like '%").append(categorylist.split(",")[i].replaceAll("!", "&")).append("%' or ");
                }
            }


        }


        sql = sql + string.toString();


        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Hotel> categorySearch(String categorylist, int offset, int limit, String sortcolumn, String sorttype) {

        String sql = "select * from hotel where id>0 and (";
        StringBuilder string = new StringBuilder();
        if (categorylist != null && categorylist.trim().length() > 0) {

            for (int i = 0; i < categorylist.split(",").length; i++) {

                if (i == (categorylist.split(",").length - 1)) {
                    string.append("categories like '%").append(categorylist.split(",")[i].replaceAll("!", "&")).append("%') ");
                } else {

                    string.append("categories like '%").append(categorylist.split(",")[i].replaceAll("!", "&")).append("%' or ");
                }
            }


        }

        string.append(" order by ? ? limit ?,?");

        sql = sql + string.toString();


        Collection<Map<String, Object>> rows = jdbcTemplate
                .queryForList(sql, sortcolumn, sorttype, offset, limit);
        List<Hotel> searchList = new ArrayList<>();
        rows.stream().map((row) -> {
            Hotel hotel = new Hotel();
            hotel.setAddress((String) row.get("address"));
            hotel.setCategories((String) row.get("categories"));
            hotel.setCity((String) row.get("city"));
            hotel.setCountry((String) row.get("country"));
            hotel.setId(String.valueOf(row.get("id")));
            hotel.setLatitude((String) row.get("latitude"));
            hotel.setLongitude((String) row.get("longitude"));
            hotel.setName((String) row.get("name"));
            hotel.setPostalcode((String) row.get("postalcode"));
            hotel.setProvince((String) row.get("province"));
            hotel.setRatings(String.valueOf(row.get("ratings")));
            hotel.setReviewText((String) row.get("review_text"));
            hotel.setReviewTitle((String) row.get("review_title"));
            hotel.setReviewUsername((String) row.get("review_username"));
            return hotel;
        }).forEach(searchList::add);
        return searchList;
    }

    @Override
    public List<Hotel> searchHotelById(String id) {
        Collection<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from hotel where id=? ", id);
        List<Hotel> searchList = new ArrayList<>();
        rows.stream().map((row) -> {
            Hotel hotel = new Hotel();
            hotel.setAddress((String) row.get("address"));
            hotel.setCategories((String) row.get("categories"));
            hotel.setCity((String) row.get("city"));
            hotel.setCountry((String) row.get("country"));
            hotel.setId(String.valueOf(row.get("id")));
            hotel.setLatitude((String) row.get("latitude"));
            hotel.setLongitude((String) row.get("longitude"));
            hotel.setName((String) row.get("name"));
            hotel.setPostalcode((String) row.get("postalcode"));
            hotel.setProvince((String) row.get("province"));
            hotel.setRatings(String.valueOf(row.get("ratings")));
            hotel.setReviewText((String) row.get("review_text"));
            hotel.setReviewTitle((String) row.get("review_title"));
            hotel.setReviewUsername((String) row.get("review_username"));
            return hotel;
        }).forEach(searchList::add);
        return searchList;
    }

    @Override
    public void creteBooking(String rooms, String id, String date, String user_id) {
        jdbcTemplate.update("insert into booking (user_id, hotel_id, rooms, selectdate) values (?,?,?,?)", user_id, id, rooms, date);

    }

    @Override
    public void createUser(String fname, String lname, String emailTxt, String pwd) {
        jdbcTemplate.update("insert into users (first_name, last_name, email_id, password) values (?,?,?,?)", fname, lname, emailTxt, passwordEncoder.encode(pwd));

    }

}
