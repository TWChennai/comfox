package model;

import titan.service.slack.SlackMessageInterpretService;

public class ComfoxMessage {
    protected String message;
    protected String username;
    protected String isAStatus;


    public String getMessage() {

        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getIsAStatus() {
        return isAStatus;
    }
}
