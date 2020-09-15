package pl.pg.driver.tag.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class TagDto {
    Long id;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE + 30)
    String contents;
}
