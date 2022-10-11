package com.ksg.api.controll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksg.api.annotation.ControlMethod;
import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ApiCallExcption;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.template.View;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
public abstract class AbstractController{
    
    protected View view;    

    protected ObjectMapper objectMapper;
    
    public AbstractController()
    {
        objectMapper = new ObjectMapper();
    }

    public void setView(View view)
    {
        this.view = view; 
    }

    /**
     * 서비스 호출 API
     * @param serviceId
     * @param param
     * @param view
     */
    public void call(String serviceId, CommandMap param, View view) 
    {
        setView(view);

        call(serviceId, param);

    }
    /**
     * 
     * @param serviceId
     * @param param
     */
    public void call(String serviceId, CommandMap param) 
    {
        log.debug("serviceId:{}, param:{}",serviceId, param);

        CommandMap model =new CommandMap();

        String errMessage =null;

        try {

            Method[] declaredMethods = getClass().getDeclaredMethods();
            for(Method method :declaredMethods)
            {
                // Check if PrintAnnotation is applied
                if (method.isAnnotationPresent(ControlMethod.class))
                {
                    ControlMethod methoAnnotation = method.getAnnotation(ControlMethod.class);
                    if(methoAnnotation.serviceId().equals(serviceId))
                    {   
                        
                        model=(CommandMap)method.invoke(this, param );                            
                        if(model==null) model = new CommandMap();

                        model.put("success", true);
                        return;
                    }

                }
            }
    
       
            throw new ApiCallExcption("service not founded : "+serviceId);

    } catch (ApiCallExcption e) {
        model.put("success", false);
        model.put("error", e.getMessage());
    }
    
    catch(InvocationTargetException e)
    {
        e.printStackTrace();
        Exception targetExcpetion=(Exception) e.getTargetException();
        if(targetExcpetion instanceof AlreadyExistException || targetExcpetion instanceof ResourceNotFoundException)
        {
            
            errMessage = targetExcpetion.getMessage();
            model.put("success", false);
            model.put("error", errMessage);
        }
        else{
            errMessage ="unhandeld error : "+targetExcpetion.getMessage();                          
            model.put("success", false);
            model.put("error", errMessage);

        }
    }

    catch (Exception e) {
        
        e.printStackTrace();
        errMessage ="unhandeld error:"+e.getMessage();                          
        model.put("success", false);
        model.put("error", errMessage);

        
    }
    finally
    {
        model.setService_id(serviceId);
        view.setModel( model);
        view.updateView();
    }
    
}
    
}
