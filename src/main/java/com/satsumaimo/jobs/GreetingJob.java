package com.satsumaimo.jobs;

import com.satsumaimo.info.TimerInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GreetingJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingJob.class);

    @Override
    public void execute(JobExecutionContext context) {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerInfo timerInfo = (TimerInfo) jobDataMap.get(GreetingJob.class.getSimpleName());
        LOGGER.info("How do you do?");
        LOGGER.info("Remaining fire count = '{}'", timerInfo.getRemainingFireCount());
    }
}
