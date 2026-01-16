package by.ilmizeroth.birthdaycalendar.service;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.repository.BirthdayRepository;
import org.springframework.stereotype.Service;

@Service
public class BirthdayService {
    private final BirthdayRepository birthdayRepository;
    private final UserService userService;

    public BirthdayService(BirthdayRepository birthdayRepository, UserService userService) {
        this.birthdayRepository = birthdayRepository;
        this.userService = userService;
    }

    public BirthdayEntity createBirthday(BirthdayDTO request) {
        UserEntity user = userService.getCurrentUser();

        BirthdayEntity birthday = BirthdayEntity.builder().
                name(request.getName()).
                birthday(request.getBirthday()).
                description(request.getDescription()).
                build();

        return birthdayRepository.save(birthday);
    }
}
