package model.slack;

import model.ComfoxMessage;
import titan.service.slack.SlackMessageInterpretService;

public class SlackMessage extends ComfoxMessage {
    private String channel;
    private String useremail;
    private String image;
    private static final SlackMessageInterpretService slackMessageInterpretService = new SlackMessageInterpretService();

    public SlackMessage(String username, String message, String channel, String useremail, String image) {
        this.username = username;
        this.message = message;
        this.channel = channel;
        this.useremail = useremail;
        this.image = image;
        isAStatus = "false";
    }

    public String getMessage() {
        return message;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getImageURL() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getIsAStatus() {
        return isAStatus;
    }


    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "SlackMessage{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", useremail='" + useremail + '\'' +
                '}';
    }

    public void setIsAStatus(String isAStatus) {
        this.isAStatus = isAStatus;
    }

    public void formatMessageContent(){
        this.message = slackMessageInterpretService.replaceUserAndChannelId(message);
    }
}

