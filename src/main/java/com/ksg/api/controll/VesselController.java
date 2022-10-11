package com.ksg.api.controll;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Vessel;
import com.ksg.api.service.VesselService;
import com.ksg.view.common.template.View;
import com.ksg.view.util.DateUtil;
import com.ksg.view.util.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 선박 정보  컨트롤러
 */
@Slf4j
public class VesselController extends AbstractController {

    private VesselService service;

    public VesselController() {
        super();
        service = new VesselService();
    }

    @ControlMethod(serviceId = "selectVessel")
    public CommandMap selectByCondtion(CommandMap param)
    {
        log.info("param:{}",param);

        String vessel_name =(String) param.get("vessel_name");
        String vessel_type =(String) param.get("vessel_type");
        String vessel_use =(String) param.get("vessel_use");
        String vessel_company =(String) param.get("vessel_company");
        Vessel vessel = Vessel.builder()
                                .vessel_name(vessel_name)
                                .vessel_use(vessel_use)
                                .vessel_type(vessel_type)
                                .vessel_company(vessel_company)
                                .build();

        List<Vessel> result = service.selectByCondtion(vessel);

        result.stream().forEach(o -> {
            try {
                o.setEvent_date( DateUtil.convertType(o.getEvent_date()));
            } catch (Exception e) {
                //o.setEvent_date()
            }
        });

        CommandMap model = new CommandMap();

        var resultArray=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());
                        
        model.put("success", true);
        model.put("data", resultArray);

        return model;
        
    }
    
    @ControlMethod(serviceId = "insertVessel")
    public CommandMap insertVessel(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        String vessel_name = (String) param.get("vessel_name");
        String vessel_abbr = (String) param.get("vessel_abbr");
        String vessel_type = (String) param.get("vessel_type");
        String vessel_company = (String) param.get("vessel_company");
        String vessel_mmsi = (String) param.get("vessel_mmsi");
        String contents =(String) param.get("contents");
        String vessel_use = (String) param.get("vessel_use");
        

        Vessel vessel = Vessel.builder()
                                .vessel_name(vessel_name)
                                .vessel_abbr(vessel_abbr)
                                .vessel_type(vessel_type)
                                .vessel_company(vessel_company)
                                .vessel_mmsi(vessel_mmsi)
                                .vessel_use(vessel_use)                                
                                .contents(contents)
                                .build();

        int result = service.insert(vessel);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }

    @ControlMethod(serviceId = "deleteVessel")
    public CommandMap deleteVessel(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        var id = (int) param.get("id");
        var result=service.delete(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }

    @ControlMethod(serviceId = "updateVessel")
    public CommandMap updateVessel(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        int id = (int) param.get("id");
        String vessel_name = (String) param.get("vessel_name");
        String vessel_abbr = (String) param.get("vessel_abbr");
        String vessel_type = (String) param.get("vessel_type");
        String vessel_mmsi = (String) param.get("vessel_mmsi");
        String contents =(String) param.get("contents");
        String vessel_use = (String) param.get("vessel_use");
        

        Vessel vessel = Vessel.builder()
                                .id(id)
                                .vessel_name(vessel_name)
                                .vessel_abbr(vessel_abbr)
                                .vessel_type(vessel_type)
                                .vessel_mmsi(vessel_mmsi)
                                .vessel_use(vessel_use)                                
                                .contents(contents)
                                .build();
                                
        var result = service.update(vessel);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return null;
    }
    @ControlMethod(serviceId = "exportVessel")
    public CommandMap exportVessel(CommandMap param) throws Exception
    {
        String filePath = (String) param.get("filePath");

        List<Vessel> result = service.selectAll();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());

        ExcelUtil excelUtil = new ExcelUtil();

        String colName[] ={"vessel_name", "vessel_abbr","vessel_type","vessel_company", "vessel_mmsi","vessel_use", "contents" };
        
        excelUtil.createExcelToFile(resultArry, filePath, colName);
       
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", filePath);
        return returnMap;
    }
}
