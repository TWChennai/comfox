import kafka.ComfoxKafkaConsumer;
import titan.service.slack.SlackMessageService;
import titan.service.twitter.TwitterMessageService;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.util.Arrays.asList;

public class ComfoxIndexerApp {
    private final ComfoxKafkaConsumer comfoxKafkaConsumer;

    public ComfoxIndexerApp(ComfoxKafkaConsumer comfoxKafkaConsumer) {
        this.comfoxKafkaConsumer = comfoxKafkaConsumer;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ComfoxKafkaConsumer comfoxKafkaConsumer = new ComfoxKafkaConsumer(asList("slack", "twitter"), new TwitterMessageService(), new SlackMessageService());
        ComfoxIndexerApp comfoxIndexerApp = new ComfoxIndexerApp(comfoxKafkaConsumer);
        comfoxIndexerApp.comfoxKafkaConsumer.consumeMessages();
    }
}