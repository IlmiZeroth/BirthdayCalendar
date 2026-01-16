package by.ilmizeroth.birthdaycalendar.repository;

import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BirthdayRepository extends JpaRepository<BirthdayEntity, Long> {

    List<BirthdayEntity> findAllByOwnerId(Long ownerId);

    List<BirthdayEntity> findAllByOwnerIdAndBirthdayBetween(
            Long ownerId,
            LocalDate start,
            LocalDate end
    );
}
