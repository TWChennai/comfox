package data.source.twitter;

import kafka.Producer;
import org.codehaus.jackson.map.ObjectMapper;
import twitter4j.*;


import java.io.IOException;

public class TwitterKafkaUserStreamListener extends TwitterUserStreamListener {

    private Producer producer;
    private TwitterMessage twitterMessage;


    public TwitterKafkaUserStreamListener(Producer producer) {
        this.producer = producer;
    }

    public void onStatus(Status status, boolean isAStatus) {
        twitterMessage = TwitterMessage.create(status);
        twitterMessage.setIsAStatus(isAStatus);
        onStatus(status);
    }

    @Override
    public void onStatus(Status status) {
        twitterMessage = twitterMessage == null ? TwitterMessage.create(status) : twitterMessage;
        try {
            String twitterMessageAsString = new ObjectMapper().writeValueAsString(twitterMessage);
            producer.send(String.valueOf(status.getId()), twitterMessageAsString);
            twitterMessage = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
