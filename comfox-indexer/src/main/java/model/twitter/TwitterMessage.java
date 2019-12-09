package model.twitter;

import model.ComfoxMessage;

public class TwitterMessage extends ComfoxMessage {
    private String image;

    public TwitterMessage(String username, String message, String image) {
        this.username = username;
        this.message = message;
        this.image = image;
        isAStatus = "false";
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getIsAStatus(){
        return isAStatus;
    }
}