package com.ksg.api.service;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.ksg.api.model.Schedule;
public class ScheduleServiceTest {

    ScheduleService service;
    
    @Test
    public void createTest() throws IOException
    {
        service = new ScheduleService();
        Schedule param = new Schedule();
        param.setEvent_date("20221004");
        param.setInout_type("I");
        service.createSchedule( param);
    }
}
