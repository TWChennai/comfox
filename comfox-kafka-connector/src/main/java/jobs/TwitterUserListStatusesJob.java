package jobs;

import data.source.twitter.TwitterKafkaUserStreamListener;
import kafka.Producer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import twitter4j.*;

import java.util.List;

public class TwitterUserListStatusesJob implements Job {

    private static final int PAGE_NO = 1;
    private static final int NO_OF_TWEETS = 10;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            pollUserListStatusesAndStoreInKafka();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void pollUserListStatusesAndStoreInKafka() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        List<UserList> userLists = twitter.getUserLists(twitter.getId());
        for (UserList userList : userLists) {
            getStatuesFromUserList(twitter, userList);
        }
    }

    private void getStatuesFromUserList(Twitter twitter, UserList userList) throws TwitterException {
        Producer producer = new Producer("twitter");
        Paging page = new Paging(PAGE_NO, NO_OF_TWEETS);
        TwitterKafkaUserStreamListener listener = new TwitterKafkaUserStreamListener(producer);
        List<Status> statuses = twitter.getUserListStatuses(userList.getId(), page);
        for (Status status : statuses) {
            listener.onStatus(status, true);
        }
    }
}
