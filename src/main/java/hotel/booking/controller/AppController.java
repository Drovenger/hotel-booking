package hotel.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(value = {"/", "/index", "/welcome"})
    public String indexPage() {
        return "index";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @GetMapping("/hotel")
    public String hotel() {
        return "hotel";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/booking-page")
    public String bookingPage() {
        return "booking-page";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
