package pl.pg.driver.user.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserAuthenticateDto {
    String token;
    String role;
}
