package com.satsumaimo.util;

import com.satsumaimo.info.TimerInfo;
import org.quartz.*;

import java.util.Date;

public class TimerUtils {

    private TimerUtils() {}

    public static <T extends Job> JobDetail buildJobDetail(final Class<T> jobClass, final TimerInfo timerInfo) {

        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), timerInfo);

        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static <T extends Job> Trigger buildTrigger(final Class<T> jobClass, final TimerInfo timerInfo) {

        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(timerInfo.getRepeatIntervalMs());

        if (timerInfo.isRunForever()) {
            // Same result as builder.withRepeatCount(-1);
            builder = builder.repeatForever();
        } else {
            // Trigger will launch the first fire without counting, making it over fire by one time
            builder = builder.withRepeatCount(timerInfo.getTotalFireCount() - 1);
        }

        return TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName()) // Sets the 'TriggerKey' field of the builder
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + timerInfo.getInitialOffsetMs()))
                .build(); // Calls build() on the ScheduleBuilder field internally
    }
}
