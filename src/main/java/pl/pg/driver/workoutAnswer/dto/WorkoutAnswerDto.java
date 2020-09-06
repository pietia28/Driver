package pl.pg.driver.workoutAnswer.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.dto.QuestionDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class WorkoutAnswerDto {
    Long id;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    String answer;

    Boolean isCorrect;

    QuestionDto question;
}
