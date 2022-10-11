package com.ksg.view.common.template;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

import com.ksg.api.controll.AbstractController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.service.CodeService;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.util.ViewUtil;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public abstract class CommonPopup extends JDialog implements ActionListener, ComponentListener, View{

    public int STATUS;

    public static final int CANCEL=0;
    public static final int OK=1;
    
    protected AbstractController controller;

    protected CodeService codeService = new CodeService();
    
    public Object result;

    CommandMap model =new CommandMap();

    public CommonPopup()
    {
        this.setModal(true);
    }

    public void createAndUpdateUI() {

        this.getContentPane().add(buildCenter());

        this.getContentPane().add(buildControl(),BorderLayout.SOUTH);
        
        ViewUtil.center(this,true);

		this.setResizable(false);
        
		this.setVisible(true);
    }

    public void callApi(String serviceId, CommandMap param)
    {
        if(this.controller!=null)
            this.controller.call(serviceId, param, this);
    }
    
    public void close() {
		this.setVisible(false);
		this.dispose();
		
	}
    protected abstract KSGPanel buildControl();

    protected abstract KSGPanel buildCenter();

    
    @Override
    public CommandMap getModel() {
      
        return model;
    }

    @Override
    public void setModel(CommandMap model) {
        this.model = model;
        
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
