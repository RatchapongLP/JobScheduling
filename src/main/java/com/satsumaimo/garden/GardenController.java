package com.satsumaimo.garden;

import com.satsumaimo.info.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
public class GardenController {

    private final GardenService gardenService;

    @Autowired
    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    @PostMapping("rungreeting")
    public void runGreetingJob() {
        gardenService.runGreetingJob();
    }

    @GetMapping("job")
    public List<TimerInfo> getAllJobTimerInfo() {
        return gardenService.getAllJobTimerInfo();
    }

    @GetMapping("job/{jobClass}")
    public TimerInfo getJobTimerInfo(@PathVariable String jobClass) {
        return gardenService.getJobTimerInfo(jobClass);
    }

    @PostMapping("job")
    public void updateJobTimerInfo(@RequestParam String jobClass, @RequestParam TimerInfo timerInfo) {
        gardenService.updateJobTimerInfo(jobClass, timerInfo);
    }

    @DeleteMapping("job/{jobClass}")
    public boolean deleteJob(@PathVariable String jobClass) {
        return gardenService.deleteJob(jobClass);
    }
}
