package titan.service.slack;


import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.vdurmont.emoji.EmojiParser;
import config.ConfigManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlackMessageInterpretService {

    private static final String SLACK_AUTH_TOKEN = ConfigManager.getSlackProperty("slack_auth_token");
    private static final SlackSession session = SlackSessionFactory.createWebSocketSlackSession(SLACK_AUTH_TOKEN);


    public SlackMessageInterpretService(){

        session.connect();
    }

    public String replaceUserAndChannelId(String message){
        String sanitizedMessage = sanitizeMessage(message);
        return replaceUserId(replaceChannelId(sanitizedMessage));
    }

    private String sanitizeMessage(String message) {
        message = EmojiParser.parseToUnicode(message);
        message = message.replaceAll(":[[a-z]_]+:", "");
        message = message.replaceAll(":pop ", "");
        message = message.replaceAll(" :pop$", "");
        return message.replaceAll("@comfox ", "");
    }

    private String replaceChannelId(String message) {

        String pattern = "\\<\\#\\S+\\>";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(message);
        while (m.find()) {
            String channelId = m.group(0);
            channelId = channelId.replaceAll("<#", "");
            channelId = channelId.replaceAll(">", "");
            SlackChannel channel = session.findChannelById(channelId);
            String channelName = channel.getName();
            message = message.replaceAll("<#" + channelId + ">", "#"+channelName);
        }
        return message;
    }

    private String replaceUserId(String message) {

        String pattern = "\\<\\@\\S+\\>";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(message);
        while (m.find()) {
            String userId = m.group(0);
            userId = userId.replaceAll("<@", "");
            userId = userId.replaceAll(">", "");
            SlackUser user = session.findUserById(userId);
            String userName = user.getUserName();
            message = message.replaceAll("<@" + userId + ">", userName);
        }
        return message;
    }
}
