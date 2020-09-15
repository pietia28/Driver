package pl.pg.driver.question.dto;

import pl.pg.driver.question.Question;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;
import java.util.Optional;

public class QuestionDtoMapper {
    private QuestionDtoMapper() {
    }

    public static QuestionDto entityToDtoShow(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .contents(question.getContents())
                .workout(
                        Optional.ofNullable(question.getWorkout())
                        .map(WorkoutDtoMapper::entityToDtoShow)
                        .orElse(null)
                )
                .build();
    }

    public static QuestionDtoNoWorkouts entityToDtoNoWorkoutShow(Question question) {
        return QuestionDtoNoWorkouts.builder()
                .id(question.getId())
                .contents(question.getContents())
                .build();
    }

    public static Question dtoToEntity(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .contents(questionDto.getContents())
                .workout(
                        Optional.ofNullable(questionDto.getWorkout())
                        .map(WorkoutDtoMapper::dtoToEntity)
                        .orElse(null)
                )
                .build();
    }
}
