package pl.pg.driver.maessage;

public class MessageContent {
    private MessageContent() {
    }

    public static final String OK = "Ok";
    public static final String ERROR = "Error";
    public static final String PAGE_IN_URL = "?page=";

    /*--------------Common Validation------------------*/
    public static final String VALID_NOT_NULL = "Cannot be null";
    public static final String VALID_NOT_BALNK = "Cannot be blank";
    public static final String VALID_MAX_SIZE = "Size cannot be larger than ";
    public static final String VALID_EMAIL = "Not valid email address";
    public static final String VALID_NIP = "Not valid NIP";
    public static final String VALID_FIELD_VALID = "[Valid] field ";

    /*---------------- User -----------------*/
    public static final String USER_NOT_FOUND = "No user found for id ";
    public static final String USER_DELETED = "Successfully deleted user on id ";

    /*---------------- Tag -----------------*/
    public static final String TAG_NOT_FOUND = "No tag found for id ";
    public static final String TAG_DELETED = "Successfully deleted tag on id ";

    /*---------------- Workout -----------------*/
    public static final String WORKOUT_NOT_FOUND = "No workout found for id ";
    public static final String WORKOUT_DELETED = "Successfully deleted workout on id ";
}
