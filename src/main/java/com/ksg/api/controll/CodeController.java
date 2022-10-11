package com.ksg.api.controll;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.model.Code;
import com.ksg.api.model.CodeClass;
import com.ksg.api.model.CommandMap;
import com.ksg.api.service.CodeService;

import com.ksg.view.util.DateUtil;


import lombok.extern.slf4j.Slf4j;


/**
 * 코드 관리 컨트롤
 */
@Slf4j
public class CodeController extends AbstractController{

    CodeService service;

    public CodeController() {
        super();
        service = new CodeService();
    }
    @ControlMethod(serviceId = "selectCode")
    public CommandMap selectByCondtion(CommandMap param) throws Exception
    {
        log.info("param:{}",param);

        String code_class_name =(String) param.get("code_class_name");
        
        CodeClass codeClass = CodeClass.builder().code_class_name(code_class_name).build();

        List<CodeClass> result = service.selectByCondition(codeClass);

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
        model.put("service_id", param.get("service_id"));                    
        model.put("success", true);
        model.put("data", resultArray);

        return model;
    }
    @ControlMethod(serviceId = "selectCodeDetail")
    public CommandMap selectDetailByCondtion(CommandMap param)
    {
        log.info("param:{}",param);

        String code_class_name =(String) param.get("code_class_name");
        String code_class_id = (String) param.get("code_class_id");
        
        Code code = Code.builder()
                        .code_class_name(code_class_name)
                        .code_class_id(code_class_id)
                        .build();

        List<Code> result = service.selectDetailByCondition(code);

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
        model.put("service_id", param.get("service_id"));                    
        model.put("success", true);
        model.put("data", resultArray);

        return model;        
    }

    @ControlMethod(serviceId = "insertCode")
    public CommandMap insertCode(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        String code_class_id = (String) param.get("code_class_id");
        String code_class_name = (String) param.get("code_class_name");
        
        CodeClass code = CodeClass.builder()
                                .code_class_id(code_class_id)
                                .code_class_name(code_class_name)
                           
                                .build();

        int result = service.insert(code);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }

    @ControlMethod(serviceId = "insertCodeDetail")
    public CommandMap insertCodeDetail(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        String code_class_id = (String) param.get("code_class_id");
        //String code_id = (String) param.get("code_id");
        String code_name = (String) param.get("code_name");
        String code_field1 = (String) param.get("code_field1");
        String code_field2 = (String) param.get("code_field2");
        String code_field3 = (String) param.get("code_field3");
        
        Code code = Code.builder()
                                .code_class_id(code_class_id)
                                .code_name(code_name)
                                .code_field1(code_field1)
                                .code_field2(code_field2)
                                .code_field3(code_field3)
                                .build();

        int result = service.insertDetail(code);                                
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
    }

    

    @ControlMethod(serviceId = "deleteCodeClass")
    public CommandMap deleteCodeClass(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        var id = (String)param.get("code_class_id");
        var result=service.delete(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }
    @ControlMethod(serviceId = "deleteCodeDetail")
    public CommandMap deleteCode(CommandMap param) throws Exception
    {
        log.info("param:{}",param);
        var id = (int)param.get("code_id");
        var result=service.deleteDetail(id);
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);

        return returnMap;
    }    

    @ControlMethod(serviceId = "updateCode")
    public CommandMap updateCode(CommandMap param)
    {
        log.info("param:{}",param);
        return null;
    }

    /**
     * 코드 상세 정보 수정
     * @param param
     * @return
     * @throws Exception
     */
    @ControlMethod(serviceId = "updateCodeDetail")
    public CommandMap updateCodeDetail(CommandMap param) throws Exception{
        
        String code_class_id = (String) param.get("code_class_id");
        int code_id = (int) param.get("code_id");
        String code_name = (String) param.get("code_name");
        String code_field1 = (String) param.get("code_field1");
        String code_field2 = (String) param.get("code_field2");
        String code_field3 = (String) param.get("code_field3");

        Code code = Code.builder().code_class_id(code_class_id)
                                    .code_id(code_id)
                                    .code_name(code_name)
                                    .code_field1(code_field1)
                                    .code_field2(code_field2)
                                    .code_field3(code_field3)
                                    .build();



        var result = service.updateDetail(code);
        
        CommandMap returnMap = new CommandMap();
        returnMap.put("success", true);
        returnMap.put("data", result);
        return returnMap;
        
    }
}
