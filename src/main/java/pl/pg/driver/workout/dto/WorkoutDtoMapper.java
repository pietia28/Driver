package pl.pg.driver.workout.dto;

import pl.pg.driver.workout.Workout;

public class WorkoutDtoMapper {
    private WorkoutDtoMapper() {
    }

    public static WorkoutDto EntityToDtoShow(Workout workout) {
        return WorkoutDto.builder()
                .id(workout.getId())
                .title(workout.getTitle())
                .build();
    }

    public static Workout DtoToEntity(WorkoutDto workoutDto) {
        return Workout.builder()
                .id(workoutDto.getId())
                .title(workoutDto.getTitle())
                .build();
    }
}
