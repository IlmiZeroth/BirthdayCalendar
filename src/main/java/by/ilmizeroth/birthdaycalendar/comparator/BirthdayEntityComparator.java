package by.ilmizeroth.birthdaycalendar.comparator;

import by.ilmizeroth.birthdaycalendar.entity.BirthdayEntity;

import java.time.MonthDay;
import java.util.Comparator;

public class BirthdayEntityComparator implements Comparator<BirthdayEntity> {

    @Override
    public int compare(BirthdayEntity o1, BirthdayEntity o2) {
        return MonthDay.from(o1.getBirthday()).compareTo(MonthDay.from(o2.getBirthday()));
    }
}
