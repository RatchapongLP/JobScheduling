package com.satsumaimo.Garden;

import com.satsumaimo.info.TimerInfo;
import com.satsumaimo.jobs.GreetingJob;
import com.satsumaimo.timerservice.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TimerInfo> getAllJobTimerInfo() {
        return scheduler.getAllJobTimerInfo();
    }

    public TimerInfo getJobTimerInfo(String jobClass) {
        return scheduler.getJobTimerInfo(jobClass);
    }

    public void updateJobTimerInfo(String jobClass, TimerInfo timerInfo) {
        scheduler.updateJobTimerInfo(jobClass, timerInfo);
    }

    public boolean deleteJob(String jobClass) {
        return scheduler.deleteJob(jobClass);
    }
}
