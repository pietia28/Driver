package pl.pg.driver.user.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.maessage.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@Builder
public class UserDto {
    Long id;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String firstName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String lastName;

    @Size(max = 30, message = MessageContent.VALID_MAX_SIZE)
    String nick;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BALNK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE)
    String email;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BALNK)
    String password;

    LocalDateTime created;

    LocalDateTime updated;

    Integer workoutsPoints;
}
