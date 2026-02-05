package by.ilmizeroth.birthdaycalendar.view;

import java.util.List;

public record MonthView(
        String name,
        List<DayView> days
) {}
