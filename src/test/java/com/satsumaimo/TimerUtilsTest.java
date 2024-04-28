package com.satsumaimo;

import com.satsumaimo.info.TimerInfo;
import com.satsumaimo.jobs.GreetingJob;
import com.satsumaimo.util.TimerUtils;
import org.junit.jupiter.api.*;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.impl.JobDetailImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimerUtilsTest {

    TimerUtils timerUtils;
    int testCount;

    @BeforeAll
    static void theMostBeginning() {
        System.out.println("theMostBeginning ...");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        testCount++;
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("A method call is finished");
    }

    @Test
    void testBuildJobDetail() {

        TimerInfo timerInfo = new TimerInfo();
        timerInfo.setTotalFireCount(5);
        timerInfo.setRemainingFireCount(timerInfo.getTotalFireCount());
        timerInfo.setRepeatIntervalMs(5000);
        timerInfo.setInitialOffsetMs(1000);
        timerInfo.setCallbackData("Hello test 123");

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(GreetingJob.class.getSimpleName(), timerInfo);

        JobDetailImpl expectedJobDetail = new JobDetailImpl();
        expectedJobDetail.setJobClass(GreetingJob.class);
        expectedJobDetail.setJobDataMap(jobDataMap);
        expectedJobDetail.setKey(new JobKey(GreetingJob.class.getSimpleName(), null));

        JobDetail actualJobDetail = TimerUtils.buildJobDetail(GreetingJob.class, timerInfo);
        assertEquals(expectedJobDetail, actualJobDetail);
    }
}
