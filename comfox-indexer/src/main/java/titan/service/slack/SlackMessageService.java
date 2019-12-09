package titan.service.slack;


import com.thinkaurelius.titan.core.TitanVertex;
import config.ConfigManager;
import model.slack.SlackMessage;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.json.JSONArray;
import titan.service.MessageService;
import util.SimpleHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class SlackMessageService extends MessageService<SlackMessage> {

    private static final String SLACK_RELATED_MESSAGES_URL = ConfigManager.getSlackProperty("slack_related_messages_url");
    private static final String SLACK_ENV = ConfigManager.getSlackProperty("env");

    @Override
    public void interpretMessage(SlackMessage message) throws URISyntaxException, IOException {
        boolean isAPopMessage = asList(message.getMessage().split("\\s")).contains(":pop");
        message.setIsAStatus("false");
        message.formatMessageContent();
        super.interpretMessage(message);
        if (isAPopMessage && !SLACK_ENV.equals("local")) {
            postRelatedMessagesToSlack(message);
        }
    }

    public Vertex createMessageNode(SlackMessage slackMessage) {
        TitanVertex titanVertex = titanGraph.addVertex(
                T.label, "message",
                "channel", "slack",
                "username", slackMessage.getUsername(),
                "content", slackMessage.getMessage(),
                "timestamp", String.valueOf(LocalDateTime.now()),
                "useremail", slackMessage.getUseremail(),
                "imageurl", slackMessage.getImageURL(),
                "isAStatus", "false");
        titanGraph.tx().commit();
        return titanVertex;
    }

    private void postRelatedMessagesToSlack(SlackMessage message) throws URISyntaxException, IOException {
        SimpleHttpClient httpClient = new SimpleHttpClient();

        JSONArray relatedMessages = getRelatedMessages(message.getMessage(), httpClient);
        if (!relatedMessages.isNull(0)) {
            String formattedContent = String.format("{\"text\":\"%s\"}", relatedMessages.join("\n\n").replaceAll("\"", ""));
            StringEntity postData = new StringEntity(formattedContent);
            postData.setContentType("application/json");

            URI slackIncomingWebhookUri = new URIBuilder(getClackIncomingWebhookUrlForChannel(message.getChannel())).build();
            HttpPost slackIncomingWebhookPostRequest = httpClient.httpPostRequest(slackIncomingWebhookUri, postData);
            httpClient.getResponse(slackIncomingWebhookPostRequest);
        }
    }

    private String getClackIncomingWebhookUrlForChannel(String channel) {
        return ConfigManager.getSlackProperty(channel + "_slack_channel_incoming_webhook_url");
    }

    private JSONArray getRelatedMessages(String message, SimpleHttpClient httpClient) throws URISyntaxException, IOException {
        URI slackRelatedMessagesUri = new URIBuilder(SLACK_RELATED_MESSAGES_URL).setParameter("content", message).build();
        HttpGet relatedMessagesGetRequest = httpClient.httpGetRequest(slackRelatedMessagesUri, Optional.empty());
        String relatedMessagesResponse = httpClient.getResponse(relatedMessagesGetRequest);
        return new JSONArray(relatedMessagesResponse);
    }
}
