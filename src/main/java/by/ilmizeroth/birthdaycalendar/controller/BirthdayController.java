package by.ilmizeroth.birthdaycalendar.controller;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.service.BirthdayService;
import by.ilmizeroth.birthdaycalendar.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BirthdayController {

    private final BirthdayService birthdayService;
    private final UserService userService;

    public BirthdayController(BirthdayService birthdayService, UserService userService) {
        this.birthdayService = birthdayService;
        this.userService = userService;
    }

    @GetMapping("/birthdays")
    public String birthdays(Model model) {

        UserEntity user = userService.getCurrentUser();

        List<BirthdayEntity> birthdays = birthdayService.getBirthdays();

        model.addAttribute("birthdays", birthdays);

        return "birthday/birthdays-page";
    }

    @GetMapping("/birthdays/add")
    public String birthdays_add_page() {
        return "birthday/birthdays-add";
    }

    @PostMapping("/birthdays/add")
    public String birthdays_add(@RequestParam String name, @RequestParam LocalDate date, @RequestParam String description) {
        BirthdayDTO birthday = BirthdayDTO.builder().
                name(name).
                birthday(date).
                description(description).
                build();
        birthdayService.createBirthday(birthday);
        return "redirect:/birthdays";
    }
}
