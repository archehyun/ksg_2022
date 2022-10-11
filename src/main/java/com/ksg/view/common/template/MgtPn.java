package com.ksg.view.common.template;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.ksg.api.controll.AbstractController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.service.CodeService;
import com.ksg.view.common.panel.KSGPanel;


public abstract class MgtPn extends KSGPanel implements View,  ActionListener, ComponentListener{
    protected CommandMap model;

    private AbstractController controller;

    protected CodeService codeService = new CodeService();
    
    public MgtPn(String name)
    {
        super();
        this.setName(name);
    }

    public CommandMap getModel() {
        
        return model;
    }
    public void callApi(String serviceId, CommandMap param)
    {
        if(this.controller!=null)
            this.controller.call(serviceId, param, this);
    }


   
    public void setModel(CommandMap model) {
        this. model = model;
        
    }

    public void setController(AbstractController constroller)
    {
        this.controller =constroller;
    }
    @Override
    public void componentResized(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
