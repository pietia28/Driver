package pl.pg.driver.question.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionDtoNoWorkouts {
    Long id;

    String contents;
}
