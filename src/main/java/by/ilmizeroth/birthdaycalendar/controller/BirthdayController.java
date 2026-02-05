package by.ilmizeroth.birthdaycalendar.controller;

import by.ilmizeroth.birthdaycalendar.comparator.BirthdayEntityComparator;
import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.service.BirthdayService;
import by.ilmizeroth.birthdaycalendar.service.CalendarViewService;
import by.ilmizeroth.birthdaycalendar.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class BirthdayController {

    private final BirthdayService birthdayService;
    private final UserService userService;
    private final CalendarViewService calendarViewService;

    public BirthdayController(BirthdayService birthdayService, UserService userService, CalendarViewService calendarViewService) {
        this.birthdayService = birthdayService;
        this.userService = userService;
        this.calendarViewService = calendarViewService;
    }

    @GetMapping("/birthdays")
    public String birthdays(Model model) {
        UserEntity userEntity = userService.getCurrentUser();
        List<BirthdayDTO> allBirthdays = birthdayService.getSortedBirthdaysForUser(userEntity, new BirthdayEntityComparator());
        List<BirthdayDTO> nearBirthdays = birthdayService.getNearBirthdays(allBirthdays, 3);

        model.addAttribute("currentYear", Year.now());
        model.addAttribute("months", calendarViewService.buildYear(Year.now(), allBirthdays));
        model.addAttribute("allBirthdays", allBirthdays);
        model.addAttribute("nearBirthdays", nearBirthdays);
        model.addAttribute("currentUserName", userEntity.getName());
        return "birthday/birthdays-page";
    }

    @PostMapping("/birthdays/add")
    public String birthdays_add(@RequestParam String name, @RequestParam LocalDate date, @RequestParam String description, @RequestParam String photo) {
        UserEntity userEntity = userService.getCurrentUser();

        BirthdayDTO birthday = BirthdayDTO.builder().
                name(name).
                birthday(date).
                description(description).
                photo(photo).
                build();
        birthdayService.createBirthday(birthday, userEntity);
        return "redirect:/birthdays";
    }
    @PostMapping("/birthdays/update")
    public String birthdays_update(@RequestParam Long id, @RequestParam String name, @RequestParam LocalDate date, @RequestParam String description, @RequestParam String photo) {
        UserEntity userEntity = userService.getCurrentUser();

        BirthdayDTO birthday = BirthdayDTO.builder().
                id(id).
                name(name).
                birthday(date).
                description(description).
                photo(photo).
                ownerID(userEntity.getId()).
                build();
        birthdayService.updateBirthday(birthday);
        return "redirect:/birthdays";
    }
    @PostMapping("/birthdays/delete")
    public String birthdays_delete(@RequestParam Long id) {
        UserEntity userEntity = userService.getCurrentUser();
        BirthdayDTO birthdayDTO = BirthdayDTO.builder().
                id(id).
                ownerID(userEntity.getId()).
                build();
        birthdayService.deleteBirthday(birthdayDTO);
        return "redirect:/birthdays";
    }
}
