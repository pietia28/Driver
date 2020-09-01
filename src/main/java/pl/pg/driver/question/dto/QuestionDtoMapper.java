package pl.pg.driver.question.dto;

import pl.pg.driver.question.Question;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;
import java.util.Optional;

public class QuestionDtoMapper {
    private QuestionDtoMapper() {
    }

    public static QuestionDto EntityToDtoShow(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .contents(question.getContents())
                .imgPath(question.getImgPath())
                .workout(
                        Optional.ofNullable(question.getWorkout())
                        .map(WorkoutDtoMapper::EntityToDtoShow)
                        .orElse(null)
                )
                .build();
    }

    public static Question DtoToEntity(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .contents(questionDto.getContents())
                .imgPath(questionDto.getImgPath())
                .workout(
                        Optional.ofNullable(questionDto.getWorkout())
                        .map(WorkoutDtoMapper::DtoToEntity)
                        .orElse(null)
                )
                .build();
    }
}
