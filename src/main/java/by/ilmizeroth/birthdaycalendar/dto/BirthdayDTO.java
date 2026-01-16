package by.ilmizeroth.birthdaycalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayDTO {
    private String name;
    private LocalDate birthday;
    private String description;
}
