package pl.pg.driver.advice.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.media.dto.MediaOnlyIdDto;
import pl.pg.driver.tag.dto.TagDto;
import pl.pg.driver.workout.dto.WorkoutDto;
import java.util.List;

@Value
@Builder
public class AdviceDto {
    Long id;

    String title;

    String contents;

    Long number;

    Long likes;

    Long shows;

    WorkoutDto workout;

    List<TagDto> tags;

    MediaOnlyIdDto media;
}
