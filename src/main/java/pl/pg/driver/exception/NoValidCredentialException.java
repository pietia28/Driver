package pl.pg.driver.exception;

public class NoValidCredentialException extends RuntimeException{
    public NoValidCredentialException(String message) {
        super(message);
    }
}
