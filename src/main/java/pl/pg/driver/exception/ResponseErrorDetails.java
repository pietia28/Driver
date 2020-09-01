package pl.pg.driver.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseErrorDetails {
    private int status;
    private String message;
    private String description;
    private String url;
}
