package by.ilmizeroth.birthdaycalendar.controller;

import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AuthorizationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "authorization/login-page";
    }
    @GetMapping("/register")
    public String getRegistrationPage(){
        return "authorization/registration-page";
    }
    @PostMapping("/register")
    public String registration(@RequestParam String username, @RequestParam String password){
        String encodedPassword = passwordEncoder.encode(password);
        List<BirthdayEntity> birthdayEntities = new ArrayList<>();
        userService.save(UserEntity.builder().
                name(username).
                password(encodedPassword).
                birthdays(birthdayEntities).
                build());
        return "redirect:/login?registered";
    }
}
