package pl.pg.driver.workoutsPassed.dto;

import lombok.Builder;

import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.user.dto.UserDto;
import pl.pg.driver.workout.dto.WorkoutDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class WorkoutPassedDto {
    Long id;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    LocalDateTime passedDate;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    UserDto user;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    WorkoutDto workout;
}
