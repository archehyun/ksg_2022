package com.ksg.api.controll;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Company;
import com.ksg.api.service.CompanyService;
import com.ksg.view.common.template.View;
import com.ksg.view.util.DateUtil;
import com.ksg.view.util.ExcelUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 */
@Slf4j
//@Component("companyController")

public class CompanyController extends AbstractController{

    private CompanyService service;

    public CompanyController() {
        super();
        service = new CompanyService();
    }
    @ControlMethod(serviceId = "selectCompany")
    public CommandMap selectByCondtion(CommandMap param) throws Exception
    {
        log.info("select");

        String company_name =(String) param.get("company_name");
        String company_abbr =(String) param.get("company_abbr");

        Company company = Company.builder().company_name(company_name)
                                            .company_abbr(company_abbr)
                                            .build();

        List<Company> result = service.selectByCondtion(company);

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

    @ControlMethod(serviceId = "deleteCompany")
    public CommandMap deleteCompany(CommandMap param) throws Exception {
        var id = (int) param.get("id");
        var result=service.delete(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }
    @ControlMethod(serviceId = "insertCompany")
    public CommandMap insertCompany(CommandMap param) throws Exception{

        String company_name = (String) param.get("company_name");
        String company_abbr = (String) param.get("company_abbr");
        String agent_name = (String) param.get("agent_name");
        String agent_abbr = (String) param.get("agent_abbr");
        String contents = (String) param.get("contents");

        Company company = Company.builder()
                                .company_name(company_name)
                                .company_abbr(company_abbr)
                                .agent_name(agent_name)
                                .agent_abbr(agent_abbr)
                                .contents(contents)
                                .build();

        int result = service.insert(company);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }
    @ControlMethod(serviceId = "updateCompany")
    private CommandMap updateCompany(CommandMap param) throws Exception{
        int id = (int) param.get("id");
        String company_name = (String) param.get("company_name");
        String company_abbr = (String) param.get("company_abbr");
        String agent_name = (String) param.get("agent_name");
        String agent_abbr = (String) param.get("agent_abbr");
        String contents = (String) param.get("contents");

        Company company = Company.builder()
                                .id(id)
                                .company_name(company_name)
                                .company_abbr(company_abbr)
                                .agent_name(agent_name)
                                .agent_abbr(agent_abbr)
                                .contents(contents)
                                .build();

        var result = service.update(company);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
        
    }

    @ControlMethod(serviceId = "exportCompany")
    public CommandMap exportVessel(CommandMap param) throws Exception
    {
        String filePath = (String) param.get("filePath");

        List<Company> result = service.selectAll();

        var resultArry=result.stream()
                        .map(o -> objectMapper.convertValue(o, CommandMap.class))
                        .collect(Collectors.toList());
        ExcelUtil excelUtil = new ExcelUtil();

        String colName[] ={"company_name", "company_abbr","agent_name","agent_abbr", "contents" };

        excelUtil.createExcelToFile(resultArry, filePath, colName);

        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", filePath);
        return returnMap;
    }
    
}
