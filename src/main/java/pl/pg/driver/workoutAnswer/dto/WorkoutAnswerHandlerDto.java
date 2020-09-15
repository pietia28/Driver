package pl.pg.driver.workoutAnswer.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class WorkoutAnswerHandlerDto {
    Long workoutId;

    List<WorkoutAnswerObtainDto> workoutAnswerList;
}
