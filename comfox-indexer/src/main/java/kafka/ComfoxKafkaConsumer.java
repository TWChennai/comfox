package kafka;

import com.google.gson.Gson;
import model.ComfoxMessage;
import model.slack.SlackMessage;
import model.twitter.TwitterMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import titan.service.slack.SlackMessageService;
import titan.service.twitter.TwitterMessageService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

public class ComfoxKafkaConsumer {
    private KafkaConsumer<String, String> kafkaConsumer;
    private TwitterMessageService twitterMessageService;
    private SlackMessageService slackMessageService;

    public ComfoxKafkaConsumer(List<String> topics, TwitterMessageService twitterMessageService, SlackMessageService slackMessageService) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "Slack-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(topics);
        this.twitterMessageService = twitterMessageService;
        this.slackMessageService = slackMessageService;
    }

    private <T extends ComfoxMessage> T unmarshallJson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, type);
    }

    public void consumeMessages() throws IOException, URISyntaxException {
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
                for (ConsumerRecord<String, String> record : records) {
                    String jsonData = record.value();
                    if (record.topic().equals("twitter")) {
                        twitterMessageService.interpretMessage(unmarshallJson(jsonData, TwitterMessage.class));
                    } else if (record.topic().equals("slack")) {
                        SlackMessage message = unmarshallJson(jsonData, SlackMessage.class);
                        slackMessageService.interpretMessage(message);
                    }
                }
            }
        } finally {
            kafkaConsumer.close();
        }
    }
}
