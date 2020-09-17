package pl.pg.driver.advice.dto;

import pl.pg.driver.advice.Advice;
import pl.pg.driver.media.dto.MediaDtoMapper;
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
                .likes(advice.getLikes())
                .number(advice.getNumber())
                .shows(advice.getShows())
                .title(advice.getTitle())
                .media(
                        Optional.ofNullable(advice.getMedia())
                                .map(MediaDtoMapper::entityToDtoOnlyId)
                                .orElse(null)
                )
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

    public static AdviceDtoNoLoggedUser entityToDtoNoLoggedUserShow(Advice advice) {
        return AdviceDtoNoLoggedUser.builder()
                .id(advice.getId())
                .contents(advice.getContents())
                .likes(advice.getLikes())
                .number(advice.getNumber())
                .shows(advice.getShows())
                .title(advice.getTitle())
                .tags(advice.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(TagDtoMapper::entityToDtoShow)
                        .collect(Collectors.toList()))
                .media(
                        Optional.ofNullable(advice.getMedia())
                                .map(MediaDtoMapper::entityToDtoOnlyId)
                                .orElse(null)
                )
                .build();
    }

    public static Advice dtoToEntity(AdviceDto adviceDto) {
        return Advice.builder()
                .id(adviceDto.getId())
                .contents(adviceDto.getContents())
                .likes(adviceDto.getLikes())
                .shows(adviceDto.getShows())
                .title(adviceDto.getTitle())
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

    public static Advice dtoToEntityCreate(AdviceDtoCreate adviceDtoCreate) {
        return Advice.builder()
                .contents(adviceDtoCreate.getContents())
                .title(adviceDtoCreate.getTitle())
                .workout(
                        Optional.ofNullable(adviceDtoCreate.getWorkout())
                                .map(WorkoutDtoMapper::dtoToEntity)
                                .orElse(null)
                )
                .media(
                        Optional.ofNullable(adviceDtoCreate.getMedia())
                        .orElse(null)
                )
                .tags(adviceDtoCreate.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(TagDtoMapper::dtoToEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Advice dtoToEntityUpdate(AdviceDtoUpdate adviceDtoUpdate) {
        return Advice.builder()
                .id(adviceDtoUpdate.getId())
                .contents(adviceDtoUpdate.getContents())
                .title(adviceDtoUpdate.getTitle())
                .workout(
                        Optional.ofNullable(adviceDtoUpdate.getWorkout())
                                .map(WorkoutDtoMapper::dtoToEntity)
                                .orElse(null)
                )
                .media(
                        Optional.ofNullable(adviceDtoUpdate.getMedia())
                        .orElse(null)
                )
                .tags(adviceDtoUpdate.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(TagDtoMapper::dtoToEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
