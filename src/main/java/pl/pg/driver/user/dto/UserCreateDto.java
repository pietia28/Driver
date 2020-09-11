package pl.pg.driver.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.NonFinal;
import pl.pg.driver.maessage.MessageContent;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Data
@Builder
public class UserCreateDto {
    Long id;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String firstName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String lastName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String nick;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Email(message = MessageContent.VALID_EMAIL)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE)
    String email;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @NonFinal
    String password;
}
