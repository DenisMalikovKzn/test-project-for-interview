package ru.project.model;

public enum ExceptionResponseCode {
    UNKNOWN(0, "Unknown exeption"),
    USER_DOES_NOT_EXISTS(1, "User does not exists"),
    USER_ALREADY_EXISTS(2, "User already exists"),
    WRONG_PASSWORD(3, "Wrong password"),
    AUTHORIZATION_HEADER_EMPTY(4, "Authorization header empty"),
    AUTHORIZATION_HEADER_WRONG_FORMAT(5, "Authorization header wrong format"),
    AUTHORIZATION_WRONG_TOKEN(6, "Authorization wrong token"),
    ANIMAL_ALREADY_EXISTS(7, "Animal already exists"),
    ANIMAL_DOES_NOT_EXISTS(8, "Animal does not exists"),
    TOKEN_NOT_FOUND(9, "Token not found"),
    USER_WITH_THIS_TOKEN_NOT_FOUND(10, "User with this token not found"),
    ANIMAL_HAS_ANOTHER_OWNER_PERMISSION_DENIED(11, "Animal has another owner. Permission denied"),
    TOO_MANY_REQUESTS(12, "Too many requests")
    ;

    private final int code;

    private final String message;

    ExceptionResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
