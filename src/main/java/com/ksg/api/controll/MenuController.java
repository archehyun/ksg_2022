package com.ksg.api.controll;

import java.util.List;
import java.util.stream.Collectors;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Menu;
import com.ksg.api.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuController extends AbstractController{

    private MenuService service;
    public MenuController()
    {
        service = new MenuService();
    }
    
    @ControlMethod(serviceId = "selectMenu")
    public CommandMap selectMenu(CommandMap param)
    {
        CommandMap model = new CommandMap();

        var resultArray= service.selectAll().stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());
        model.put("service_id", param.get("service_id"));                    
        model.put("success", true);
        model.put("data", resultArray);

        return model;
    }
    
}

