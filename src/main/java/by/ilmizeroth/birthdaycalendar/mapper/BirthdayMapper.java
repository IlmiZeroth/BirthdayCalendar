package by.ilmizeroth.birthdaycalendar.mapper;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;

public class BirthdayMapper {
    public static BirthdayDTO toDTO(BirthdayEntity birthday) {
        return BirthdayDTO.builder().
                id(birthday.getId()).
                birthday(birthday.getBirthday()).
                name(birthday.getName()).
                description(birthday.getDescription()).
                photo(birthday.getPhoto()).
                build();
    }
}
