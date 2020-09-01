package pl.pg.driver.question.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.workout.dto.WorkoutDto;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class QuestionDto {
    Long id;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    String contents;

    String imgPath;

    WorkoutDto workout;
}
