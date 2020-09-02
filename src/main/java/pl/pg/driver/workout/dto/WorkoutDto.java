package pl.pg.driver.workout.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.Question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class WorkoutDto {
    Long id;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BALNK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE + 60)
    String title;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    Boolean isCorrect;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    Question question;
}
