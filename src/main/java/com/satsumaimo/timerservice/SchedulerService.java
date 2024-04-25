package com.satsumaimo.timerservice;

import com.satsumaimo.info.TimerInfo;
import com.satsumaimo.util.TimerUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public <T extends Job> void schedule(final Class<T> jobClass, final TimerInfo timerInfo) {

        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, timerInfo);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, timerInfo);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<TimerInfo> getAllRunningTimers() {

        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDesTroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
