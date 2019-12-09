import jobs.TitanDbCleanerJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ComfoxTitanCleaner {
    public static void main(String[] args) throws SchedulerException {
        ComfoxTitanCleaner comfoxTitanCleaner = new ComfoxTitanCleaner();
        comfoxTitanCleaner.purgeOldMessages();
    }

    private void purgeOldMessages() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(TitanDbCleanerJob.class).build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(12))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
