package com.satsumaimo.util;

import com.satsumaimo.info.TimerInfo;
import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

public class TimerUtils {

    private TimerUtils() {}

    public static <T extends Job> JobDetail buildJobDetail(final Class<T> jobClass, final TimerInfo timerInfo) {

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), timerInfo);

        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName()) // Binds the job class to a key field of the builder
                .setJobData(jobDataMap) // Assigns job data map to the builder's field
                .build();
    }

    public static <T extends Job> Trigger buildTrigger(final Class<T> jobClass, final TimerInfo timerInfo) {

        // A builder for building a simple trigger
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(timerInfo.getRepeatIntervalMs());

        if (timerInfo.isRunForever()) {
            // Same result as builder.withRepeatCount(-1);
            builder = builder.repeatForever();
        } else {
            // Trigger will launch the first fire without counting, making it over fire by one time
            builder = builder.withRepeatCount(timerInfo.getTotalFireCount() - 1);
        }

        final SimpleTriggerImpl trigger = (SimpleTriggerImpl) TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName()) // Relates the job class with the 'key' field of the builder
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + timerInfo.getInitialOffsetMs()))
                .build(); // Calls build() on the wrapped scheduleBuilder internally, which gives a SimpleTrigger
                          // having time related fields, job name, job data map, job group, job key, key, etc.
                          // Now, only some of the time fields and 'key' field is assigned.
        // A TriggerKey comes out, being associated with the job class's name
        trigger.getKey();
        // Nothing comes out
        trigger.getJobKey();
        trigger.getJobDataMap();
        return trigger;
    }
}
