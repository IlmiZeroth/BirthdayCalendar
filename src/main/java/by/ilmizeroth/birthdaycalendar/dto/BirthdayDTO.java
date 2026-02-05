package by.ilmizeroth.birthdaycalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayDTO {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String description;
    private String photo;
    private Long ownerID;

    public String getSafePhoto(){
        return Stream.of(".png", ".jpg", ".jpeg", ".svg", ".gif", ".bmp", ".webp")
                .anyMatch(photo::endsWith) ? photo : "img/user.svg";
    }

    public boolean isToday(){
        return MonthDay.from(LocalDate.now()).equals(MonthDay.from(birthday));
    }
}
