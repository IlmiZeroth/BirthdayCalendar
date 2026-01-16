package by.ilmizeroth.birthdaycalendar.controller;

import by.ilmizeroth.birthdaycalendar.service.BirthdayService;
import by.ilmizeroth.birthdaycalendar.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirthdayController {

    private final BirthdayService birthdayService;
    private final UserService userService;

    public BirthdayController(BirthdayService birthdayService, UserService userService) {
        this.birthdayService = birthdayService;
        this.userService = userService;
    }

    @GetMapping("/birthdays")
    public String getAll() {
        return "Birthday List for " + userService.getCurrentUser().getName();
    }
}
