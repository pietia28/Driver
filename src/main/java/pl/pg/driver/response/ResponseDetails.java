package pl.pg.driver.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ResponseDetails {
    private String status;
    private String message;
    private Object data;
    private List<Object> errors;
    private Integer pageNumber;
    private String nextPage;
    private Long totalPages;
}
