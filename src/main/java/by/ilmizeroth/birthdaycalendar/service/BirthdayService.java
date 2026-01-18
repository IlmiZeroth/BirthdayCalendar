package by.ilmizeroth.birthdaycalendar.service;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.repository.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthdayService {
    private final BirthdayRepository birthdayRepository;
    private final UserService userService;

    @Autowired
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
                owner(user).
                build();

        return birthdayRepository.save(birthday);
    }
    public List<BirthdayEntity> getBirthdays() {
        return birthdayRepository.findAllByOwnerId(userService.getCurrentUser().getId());
    }
}
