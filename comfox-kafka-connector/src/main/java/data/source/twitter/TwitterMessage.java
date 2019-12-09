package data.source.twitter;

import org.codehaus.jackson.annotate.JsonProperty;
import twitter4j.Status;

public class TwitterMessage {
    @JsonProperty
    private String username;
    @JsonProperty
    private String message;
    @JsonProperty
    private String image;
    @JsonProperty
    private boolean isAStatus;


    public TwitterMessage(String username, String message, String image) {
        this.username = username;
        this.message = message;
        this.image = image;
        isAStatus = false;
    }

    public void setIsAStatus(boolean isAStatus){
        this.isAStatus = isAStatus;
    }

    public static TwitterMessage create(Status status) {
        return new TwitterMessage(
                status.getUser().getName(),
                status.getText(),
                status.getUser().getBiggerProfileImageURL());
    }
}
