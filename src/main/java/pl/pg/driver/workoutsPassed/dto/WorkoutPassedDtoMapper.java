package pl.pg.driver.workoutsPassed.dto;

import pl.pg.driver.user.dto.UserDtoMapper;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;
import pl.pg.driver.workoutsPassed.WorkoutPassed;
import java.util.Optional;

public class WorkoutPassedDtoMapper {
    private WorkoutPassedDtoMapper() {
    }

    public static WorkoutPassedDto entityToDtoShow(WorkoutPassed workoutPassed) {
        return WorkoutPassedDto.builder()
                .id(workoutPassed.getId())
                .passedDate(workoutPassed.getPassedDate())
                .workout(
                        Optional.ofNullable(workoutPassed.getWorkout())
                        .map(WorkoutDtoMapper::entityToDtoShow)
                        .orElse(null)
                )
                .user(
                        Optional.ofNullable(workoutPassed.getUser())
                        .map(UserDtoMapper::entityToDtoShow)
                        .orElse(null)
                )
                .build();
    }

    public static WorkoutPassed dtoToEntity(WorkoutPassedDto workoutPassedDto) {
        return WorkoutPassed.builder()
                .id(workoutPassedDto.getId())
                .passedDate(workoutPassedDto.getPassedDate())
                .workout(
                        Optional.ofNullable(workoutPassedDto.getWorkout())
                        .map(WorkoutDtoMapper::dtoToEntity)
                        .orElse(null)
                )
                .user(
                        Optional.ofNullable(workoutPassedDto.getUser())
                                .map(UserDtoMapper::dtoToEntity)
                                .orElse(null)
                )
                .build();
    }
}
