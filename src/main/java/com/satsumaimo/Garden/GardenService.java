package com.satsumaimo.Garden;

import com.satsumaimo.info.TimerInfo;
import com.satsumaimo.jobs.GreetingJob;
import com.satsumaimo.timerservice.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GardenService {

    private final SchedulerService scheduler;

    @Autowired
    public GardenService(SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runGreetingJob() {
        TimerInfo timerInfo = new TimerInfo();
        timerInfo.setTotalFireCount(5);
        timerInfo.setRemainingFireCount(timerInfo.getTotalFireCount());
        timerInfo.setRepeatIntervalMs(5000);
        timerInfo.setInitialOffsetMs(1000);
        timerInfo.setCallbackData("Dummy callback data");

        scheduler.schedule(GreetingJob.class, timerInfo);
    }
}
