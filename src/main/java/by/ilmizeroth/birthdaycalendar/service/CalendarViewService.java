package by.ilmizeroth.birthdaycalendar.service;

import by.ilmizeroth.birthdaycalendar.dto.BirthdayDTO;
import by.ilmizeroth.birthdaycalendar.view.DayView;
import by.ilmizeroth.birthdaycalendar.view.MonthView;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarViewService {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("LLLL", new Locale("ru"));

    public List<MonthView> buildYear(Year year, List<BirthdayDTO> birthdays) {
        List<MonthView> months = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            YearMonth ym = year.atMonth(i);
            months.add(buildMonth(ym, birthdays));
        }

        return months;
    }

    private MonthView buildMonth(YearMonth ym, List<BirthdayDTO> birthdays) {
        List<DayView> days = new ArrayList<>();
        MonthDay today = MonthDay.from(LocalDate.now());
        // ПН = 1, ВС = 7, определяем с какого дня недели начинается месяц
        int firstDayIndex = ym.atDay(1).getDayOfWeek().getValue();
        // Если месяц начинается не с начала недели, то все прошлые дни заполняем "пробелами"
        for (int i = 1; i < firstDayIndex; i++) {
            days.add(new DayView(null, true, false, false, null));
        }

        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            LocalDate date = ym.atDay(day);
            boolean hasBirthday = birthdays.
                    stream().
                    anyMatch(b -> MonthDay.from(b.getBirthday()).
                            equals(MonthDay.from(date)));
            days.add(new DayView(day, false, hasBirthday, today.equals(MonthDay.from(date)), date));
        }

        String monthName = ym.format(formatter);

        return new MonthView(monthName, days);
    }
}
