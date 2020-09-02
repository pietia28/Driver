package pl.pg.driver.advice.dto;

import pl.pg.driver.advice.Advice;
import pl.pg.driver.tag.dto.TagDtoMapper;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdviceDtoMapper {
    private AdviceDtoMapper() {
    }

    public static AdviceDto entityToDtoShow(Advice advice) {
        return AdviceDto.builder()
                .id(advice.getId())
                .contents(advice.getContents())
                .hasLike(advice.getHasLike())
                .number(advice.getNumber())
                .shows(advice.getShows())
                .isTipOfTheWeek(advice.getIsTipOfTheWeek())
                .title(advice.getTitle())
                .created(advice.getCreated())
                .updated(advice.getUpdated())
                .workout(
                        Optional.ofNullable(advice.getWorkout())
                        .map(WorkoutDtoMapper::entityToDtoShow)
                        .orElse(null)
                )
                .tags(advice.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(TagDtoMapper::entityToDtoShow)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Advice dtoToEntity(AdviceDto adviceDto) {
        return Advice.builder()
                .id(adviceDto.getId())
                .contents(adviceDto.getContents())
                .hasLike(adviceDto.getHasLike())
                .number(adviceDto.getNumber())
                .shows(adviceDto.getShows())
                .isTipOfTheWeek(adviceDto.getIsTipOfTheWeek())
                .title(adviceDto.getTitle())
                .created(adviceDto.getCreated())
                .updated(adviceDto.getUpdated())
                .workout(
                        Optional.ofNullable(adviceDto.getWorkout())
                        .map(WorkoutDtoMapper::dtoToEntity)
                        .orElse(null)
                )
                .tags(adviceDto.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(TagDtoMapper::dtoToEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
