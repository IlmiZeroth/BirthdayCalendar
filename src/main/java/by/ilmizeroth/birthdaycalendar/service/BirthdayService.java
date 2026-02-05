package by.ilmizeroth.birthdaycalendar.service;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.exception.BirthdayUpdateException;
import by.ilmizeroth.birthdaycalendar.mapper.BirthdayMapper;
import by.ilmizeroth.birthdaycalendar.repository.BirthdayRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayService {
    private final BirthdayRepository birthdayRepository;

    @Autowired
    public BirthdayService(BirthdayRepository birthdayRepository) {
        this.birthdayRepository = birthdayRepository;
    }

    public BirthdayDTO createBirthday(BirthdayDTO request, UserEntity user) {
        BirthdayEntity birthday = BirthdayEntity.builder().
                name(request.getName()).
                birthday(request.getBirthday()).
                description(request.getDescription()).
                photo(request.getPhoto()).
                owner(user).
                build();
        BirthdayEntity savedBirthday = birthdayRepository.save(birthday);

        return BirthdayMapper.toDTO(savedBirthday);
    }

    public void updateBirthday(BirthdayDTO request) {
        BirthdayEntity birthday = birthdayRepository.
                findById(request.getId()).
                orElseThrow(() -> new BirthdayUpdateException("Birthday not found"));

        if(!birthday.getOwner().getId().equals(request.getOwnerID())) {
            throw new BirthdayUpdateException("The owners don't match");
        }
        birthday.setName(request.getName());
        birthday.setDescription(request.getDescription());
        birthday.setPhoto(request.getPhoto());
        birthday.setBirthday(request.getBirthday());
        birthdayRepository.save(birthday);
    }
    public void deleteBirthday(BirthdayDTO request) {
        BirthdayEntity birthday = birthdayRepository.
                findById(request.getId()).
                orElseThrow(() -> new BirthdayUpdateException("Birthday not found"));

        if(!birthday.getOwner().getId().equals(request.getOwnerID())) {
            throw new BirthdayUpdateException("The owners don't match");
        }
        birthdayRepository.delete(birthday);
    }
    public List<BirthdayDTO> getSortedBirthdaysForUser(UserEntity user, Comparator<BirthdayEntity> comparator) {
        return birthdayRepository.
                findAllByOwnerId(user.getId()).
                stream().
                sorted(comparator).
                map(BirthdayMapper::toDTO).
                collect(Collectors.toList());
    }

    public List<BirthdayDTO> getNearBirthdays(List<BirthdayDTO> birthdays, int dayOffset) {
        LocalDate today = LocalDate.now();
        return birthdays.stream()
                .filter(b -> {
                    LocalDate bThisYear = b.getBirthday().withYear(today.getYear());
                    LocalDate bNextYear = b.getBirthday().withYear(today.getYear() + 1);

                    return Math.abs(ChronoUnit.DAYS.between(bThisYear, today)) <= dayOffset ||
                            Math.abs(ChronoUnit.DAYS.between(bNextYear, today)) <= dayOffset;
                })
                .toList();
    }
}
