package com.satsumaimo.timerservice;

import com.satsumaimo.info.TimerInfo;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

public class TriggerListener implements org.quartz.TriggerListener {

    private final SchedulerService schedulerService;

    public TriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return TriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

        String jobClass = trigger.getKey().getName();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimerInfo timerInfo = (TimerInfo) jobDataMap.get(jobClass);

        if (!timerInfo.isRunForever()) {
            int remainingFireCount = timerInfo.getRemainingFireCount();
            if (remainingFireCount == 0) return;
            timerInfo.setRemainingFireCount(remainingFireCount - 1);
        }
        schedulerService.updateJobTimerInfo(jobClass, timerInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
