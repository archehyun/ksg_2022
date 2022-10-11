package com.ksg.view.common.form;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.ksg.view.common.panel.KSGPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.HashMap;

public class KSGForm extends KSGPanel{

    Color labelBackground=new Color(220, 220, 220);

    Color labelForground=Color.black;

    Box mainForm = Box.createVerticalBox();

    HashMap<String, JComponent> compList = new HashMap<String, JComponent>();

    public KSGForm()
    {
        super();        
        this.add(mainForm);
        mainForm.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }

    public void setLabelBackground(Color color)
    {
        this.labelBackground = color;
    }
    public void setLabelForground(Color color)
    {
        this.labelForground = color;
    }

    public void addFormItem(String label, JComponent comp, int labelSize)
    {
        compList.put(label, comp);

        KSGPanel pnMain = new KSGPanel();
        KSGPanel pnLabel = new KSGPanel();
        KSGPanel pnComp = new KSGPanel();
        pnLabel.setBackground(labelBackground);
        JLabel lblName = new JLabel(label);
        
        lblName.setForeground(labelForground);

        lblName.setBorder(BorderFactory.createEmptyBorder(0,5, 0,0));
        
        pnLabel.add(lblName);

        pnLabel.setPreferredSize(new Dimension(labelSize,25));
        pnComp.add(comp);

        pnMain.add(pnLabel,BorderLayout.WEST);
        pnMain.add(pnComp);

        pnComp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        pnMain.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.lightGray));
        mainForm.add(pnMain);
    }
    public JComponent getFormComponent(String compName)
    {
        if(compList.containsKey(compName))
        {
            return compList.get(compName);
        }
        else{
            return null;
        }
    }
    
}
