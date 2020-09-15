package pl.pg.driver.workoutAnswer.dto;

import pl.pg.driver.question.dto.QuestionDtoMapper;
import pl.pg.driver.workoutAnswer.WorkoutAnswer;

import java.util.Optional;

public class WorkoutAnswerDtoMapper {
    private WorkoutAnswerDtoMapper() {
    }

    public static WorkoutAnswerShowDto entityToDtoShow(WorkoutAnswer workoutAnswer) {
        return WorkoutAnswerShowDto.builder()
                .id(workoutAnswer.getId())
                .answer(workoutAnswer.getAnswer())
                .question(
                        Optional.ofNullable(workoutAnswer.getQuestion())
                        .map(QuestionDtoMapper::entityToDtoNoWorkoutShow)
                        .orElse(null)
                )
                .build();
    }

    public static WorkoutAnswer dtoToEntity(WorkoutAnswerDto workoutAnswerDto) {
        return WorkoutAnswer.builder()
                .id(workoutAnswerDto.getId())
                .answer(workoutAnswerDto.getAnswer())
                .question(
                        Optional.ofNullable(workoutAnswerDto.getQuestion())
                        .map(QuestionDtoMapper::dtoToEntity)
                        .orElse(null)
                )
                .build();
    }
}
