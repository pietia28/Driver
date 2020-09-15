package pl.pg.driver.workoutAnswer.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.dto.QuestionDto;
import pl.pg.driver.question.dto.QuestionDtoNoWorkouts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class WorkoutAnswerShowDto {
    Long id;

    String answer;

    QuestionDtoNoWorkouts question;
}
