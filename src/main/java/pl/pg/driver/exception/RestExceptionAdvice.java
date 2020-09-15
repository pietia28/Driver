package pl.pg.driver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<ResponseDetails> handleObjectNotFoundException(ObjectNotFoundException ex, HttpServletRequest req) {
        log.warn(ex.getMessage());

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .status(HttpStatus.NOT_FOUND.value())
                .description(ex.getMessage())
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(BadFileTypeException.class)
    ResponseEntity<ResponseDetails> handleBadFileTypeException(BadFileTypeException ex, HttpServletRequest req) {
        log.warn(ex.getMessage());

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .description(ex.getMessage())
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoValidCredentialException.class)
    ResponseEntity<ResponseDetails> handleNoValidCredentialException(NoValidCredentialException ex, HttpServletRequest req) {
        log.warn(ex.getMessage());

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .status(HttpStatus.UNAUTHORIZED.value())
                .description(ex.getMessage())
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserAccessForbiddenException.class)
    ResponseEntity<ResponseDetails> handleUserAccessForbiddenException(UserAccessForbiddenException ex, HttpServletRequest req) {
        log.warn(ex.getMessage());

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .status(HttpStatus.FORBIDDEN.value())
                .description(ex.getMessage())
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    ResponseEntity<ResponseDetails> handleNoMediaException(HttpServletRequest req) {
        log.warn(MessageContent.MEDIA_NO_FILE);

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .description(MessageContent.MEDIA_NO_FILE)
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoTipOfTheWeekendException.class)
    ResponseEntity<ResponseDetails> handleNoTipOfTheWeekendException(HttpServletRequest req) {
        log.warn(MessageContent.TIP_NOT_FOUND);

        List<Object> errorsList = new ArrayList<>();
        errorsList.add(ResponseErrorDetails.builder()
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .status(HttpStatus.NOT_FOUND.value())
                .description(MessageContent.TIP_NOT_FOUND)
                .url(req.getRequestURL().toString())
                .build());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseDetails> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<Object> errorsList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                e -> {
                    errorsList.add(ResponseErrorDetails.builder()
                            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .description(MessageContent.VALID_FIELD_VALID + e.getField() + ". " + e.getDefaultMessage())
                            .url(req.getRequestURL().toString())
                            .build());
                    log.warn(MessageContent.VALID_FIELD_VALID + e.getField() + ". " + e.getDefaultMessage());
                }
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDetails.builder()
                        .status(MessageContent.ERROR)
                        .errors(errorsList)
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseErrorDetails handleSQLIntegrityConstraintViolationException (DataIntegrityViolationException ex) {
        return ResponseErrorDetails.builder()
                .message(ex.getMostSpecificCause().getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .description(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseErrorDetails handleEntityNotFoundException (EntityNotFoundException ex) {
        return ResponseErrorDetails.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .description(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }


}
//TODO poprawic wyswietlanie (formatowanie) handleEntityNotFoundException i handleSQLIntegrityConstraintViolationException
//TODO Dodać obsługę status": 415, "error": "Unsupported Media Type",
