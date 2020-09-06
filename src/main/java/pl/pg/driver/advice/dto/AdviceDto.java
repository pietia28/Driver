package pl.pg.driver.advice.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.tag.dto.TagDto;
import pl.pg.driver.workout.dto.WorkoutDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class AdviceDto {
    Long id;


    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE)
    String title;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    String contents;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    Long number;

    Boolean hasLike;

    Boolean isTipOfTheWeek;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    Long shows;

    LocalDateTime created;

    LocalDateTime updated;

    WorkoutDto workout;

    List<TagDto> tags;
}
