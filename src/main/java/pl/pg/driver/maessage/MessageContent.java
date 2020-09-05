package pl.pg.driver.maessage;

public class MessageContent {
    private MessageContent() {
    }

    public static final String OK = "Ok";
    public static final String ERROR = "Error";
    public static final String PAGE_IN_URL = "?page=";
    public static final String ITEMS = "Items";

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

    /*---------------- Question -----------------*/
    public static final String QUESTION_NOT_FOUND = "No question found for id ";
    public static final String QUESTION_DELETED = "Successfully deleted question on id ";

    /*---------------- Workout Answer -----------------*/
    public static final String WORKOUT_ANSWER_NOT_FOUND = "No workout_answer found for id ";
    public static final String WORKOUT_ANSWER_DELETED = "Successfully deleted workout_answer on id ";

    /*---------------- Workout Passed -----------------*/
    public static final String WORKOUT_PASSED_NOT_FOUND = "No workout_passed found for id ";
    public static final String WORKOUT_PASSED_DELETED = "Successfully deleted workout_passed on id ";

    /*---------------- Advice -----------------*/
    public static final String ADVICE_NOT_FOUND = "No advice found for id ";
    public static final String ADVICE_DELETED = "Successfully deleted advice on id ";

    /*---------------- Media -----------------*/
    public static final String MEDIA_NOT_FOUND = "No media found for id ";
    public static final String MEDIA_DELETED = "Successfully deleted media on id ";
    public static final String MEDIA_UPLOADED = "File has been successfully uploaded as ";
    public static final String MEDIA_NO_FILE = "There is no file";
    public static final String MEDIA_BAD_FILE = "Invalid file type sent";
}
