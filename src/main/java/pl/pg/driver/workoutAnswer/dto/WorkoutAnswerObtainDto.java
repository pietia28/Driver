package pl.pg.driver.workoutAnswer.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkoutAnswerObtainDto {
    Long questionId;

    Long answerId;
}
