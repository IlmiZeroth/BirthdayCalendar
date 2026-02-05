package by.ilmizeroth.birthdaycalendar.view;

import java.time.LocalDate;

public record DayView(
        Integer day,
        boolean isEmpty,
        boolean hasBirthday,
        boolean isToday,
        LocalDate date
) {}
