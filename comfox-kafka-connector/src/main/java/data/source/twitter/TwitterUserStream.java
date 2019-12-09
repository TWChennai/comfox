package data.source.twitter;

import kafka.Producer;
import twitter4j.*;

public final class TwitterUserStream {

    public void listen(Producer producer) throws TwitterException {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        TwitterUserStreamListener listener = new TwitterKafkaUserStreamListener(producer);
        twitterStream.addListener(listener);

        // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.user();
    }

}
