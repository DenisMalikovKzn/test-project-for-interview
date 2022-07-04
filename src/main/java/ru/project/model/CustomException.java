package ru.project.model;

public class CustomException extends RuntimeException {

    private ExceptionResponseCode code;

    private String errorMessage;

    public CustomException(ExceptionResponseCode code) {
        super(code.getMessage());
        this.code = code;
        this.errorMessage = code.getMessage();
    }

    public ExceptionResponseCode getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
