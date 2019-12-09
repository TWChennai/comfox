import data.source.twitter.TwitterUserStream;
import jobs.TwitterUserListStatusesJob;
import kafka.Producer;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import twitter4j.TwitterException;


public class ComfoxKafkaConnectorApp {

    public static void main(String[] args) throws Exception {
        ComfoxKafkaConnectorApp comfoxKafkaConnectorApp = new ComfoxKafkaConnectorApp();

        comfoxKafkaConnectorApp.streamFromTwitterAndStoreInKafka();
        comfoxKafkaConnectorApp.pollTwitterForUserListStatusesAndStoreInKafka();
    }


    private void streamFromTwitterAndStoreInKafka() throws TwitterException {
        Producer producer = new Producer("twitter");
        TwitterUserStream twitterUserStream = new TwitterUserStream();

        twitterUserStream.listen(producer);
    }

    private void pollTwitterForUserListStatusesAndStoreInKafka() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(TwitterUserListStatusesJob.class).build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(30).repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
