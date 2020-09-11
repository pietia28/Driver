package pl.pg.driver.exception;

public class UserAccessForbiddenException extends RuntimeException{
    public UserAccessForbiddenException(String message) {
        super(message);
    }
}
