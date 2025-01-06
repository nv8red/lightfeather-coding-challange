package io.lightfeather.springtemplate.model;

public class PostSVNotificationResponse {

    private static final String SUCCESS = "Success!";

    private final String message;

    public PostSVNotificationResponse() {
        this.message = SUCCESS;
    }

    public PostSVNotificationResponse(String warnMessage) {
        this.message = warnMessage;
    }

    public String getMessage() {
        return message;
    }
}
