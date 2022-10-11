package com.ksg.api.controll;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Port;
import com.ksg.api.service.PortService;
import com.ksg.view.common.template.View;
import com.ksg.view.util.DateUtil;
import com.ksg.view.util.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortController extends AbstractController{

    

    private PortService service;    
    
    public PortController()
    {
        super();
        service = new PortService();

    }
    @ControlMethod(serviceId = "selectPort")
    public CommandMap selectByCondtion(CommandMap param)
    {
        log.info("param:{}",param);

        String port_name =(String) param.get("port_name");
        String area_code =(String) param.get("area_code");
        
        Port port = Port.builder().port_name(port_name)
                                    .area_code(area_code)
                                    .build();

        List<Port> result = service.selectByCondtion(port);
        result.stream().forEach(o -> {
            try {
                o.setEvent_date( DateUtil.convertType(o.getEvent_date()));
            } catch (Exception e) {
                //o.setEvent_date()
            }
        });

        CommandMap model = new CommandMap();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());

        model.put("success", true);
        model.put("data", resultArry);

        return model;
        
    }
    @ControlMethod(serviceId = "insertPort")
    public CommandMap insertPort(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        String port_name = (String) param.get("port_name");
        String port_area = (String) param.get("port_area");
        String port_nationality = (String) param.get("port_nationality");
        String area_code = (String) param.get("area_code");
        String contents = (String) param.get("contents");

        Port port = Port.builder()
                                .port_name(port_name)
                                .port_area(port_area)
                                .port_nationality(port_nationality)
                                .area_code(area_code)
                                .contents(contents)
                                .build();

        int result = service.insert(port);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }

    @ControlMethod(serviceId = "deletePort")
    public CommandMap deletePort(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        var id = (int) param.get("id");
        var result=service.delete(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }

    @ControlMethod(serviceId = "updatePort")
    public CommandMap updatePort(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        int id = (int) param.get("id");
        String port_name = (String) param.get("port_name");
        String port_area = (String) param.get("port_area");
        String port_nationality = (String) param.get("port_nationality");

        String area_code = (String) param.get("area_code");
        String contents = (String) param.get("contents");

        Port port = Port.builder()
                                .id(id)
                                .port_name(port_name)
                                .port_area(port_area)
                                .port_nationality(port_nationality)
                                .area_code(area_code)
                                .contents(contents)
                                .build();

        var result = service.update(port);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }

    @ControlMethod(serviceId = "exportPort")
    public CommandMap exportPort(CommandMap param) throws Exception
    {
        String filePath = (String) param.get("filePath");
        List<Port> result = service.selectAll();
        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());
        ExcelUtil excelUtil = new ExcelUtil();
        String colName[] ={"port_name", "port_nationality", "area_code","contents" };
        excelUtil.createExcelToFile(resultArry, filePath, colName);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", filePath);
        return returnMap;
    }

   
    
}
