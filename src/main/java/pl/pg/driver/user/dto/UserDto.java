package pl.pg.driver.user.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class UserDto {
    Long id;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE + 30)
    String firstName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE + 30)
    String lastName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE + 30)
    String nick;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Email(message = MessageContent.VALID_EMAIL)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE)
    String email;
}
