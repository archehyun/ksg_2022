package com.ksg.api.controll;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Schedule;
import com.ksg.api.service.ScheduleService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleController extends AbstractController{

    private ScheduleService service;
    
    public ScheduleController()
    {
        super();
        service = new ScheduleService();
    }

    @ControlMethod(serviceId = "selectScheduleByKey")
    public CommandMap selectByKey(CommandMap param)
    {
        log.info("select");

        String table_id =(String) param.get("table_id");

        List<Schedule> result = service.selectListByKey(table_id);
        
        CommandMap model = new CommandMap();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());
        

        model.put("success", true);
        model.put("data", resultArry);

        return model;
        
    }


    @ControlMethod(serviceId = "selectSchedule")
    public CommandMap selectByCondtion(CommandMap param)
    {
        log.info("select");

        String company_name =(String) param.get("comapny_name");

        String inout_type = (String) param.get("inout_type");

        String from_port = (String) param.get("from_port");

        String to_port = (String) param.get("to_port");
        
        Schedule schedule = Schedule.builder()
                                    .company_name(company_name)
                                    .inout_type(inout_type)
                                    .from_port(from_port)
                                    .to_port(to_port)
                                    .build();

        List<Schedule> result = service.selectByCondtion(schedule);

        var eventDateList=result.stream().map(o-> o.getEvent_date()).collect(Collectors.toList());

        CommandMap model = new CommandMap();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());

        var eventArry=eventDateList.stream()
                        .map(o -> Schedule.builder().event_date(o).build())
                        .collect(Collectors.toList());                        

        

        model.put("success", true);
        model.put("data", resultArry);

        return model;
        
    }
    @ControlMethod(serviceId = "createSchedule")
    public CommandMap createSchedule(CommandMap param) throws IOException
    {
        Schedule createParam = objectMapper.convertValue(param, Schedule.class);
        service.createSchedule(createParam);
        CommandMap model = new CommandMap();
        model.put("success", true);
        return model;
    }

    


    
}
