package com.satsumaimo.timerservice;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
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
