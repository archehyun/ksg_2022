package com.ksg.view.workbench.masterdata.dialog;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.PortController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.api.service.CodeService;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;

import java.awt.event.ComponentEvent;
import java.nio.channels.SelectableChannel;

public class PortInsertPopup extends CommonPopup{

    

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfPortName = new JTextField(10);
    private JTextField txfPortArea = new JTextField(10);
    private JTextField txfPortNationality = new JTextField(10);
    private JTextField txfAreaCode = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);
    private KSGComboBox<MyEnum> cbxArea = new KSGComboBox<MyEnum>();
    public PortInsertPopup()
    {
        super();
        this.setTitle("항구 정보 추가");
        this.addComponentListener(this);
        this.controller = new PortController();
    }

    @Override
    protected KSGPanel buildControl()
    {
        KSGPanel pnMain = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butOk.addActionListener(this);
        butCancel.addActionListener(this);
        pnMain.add(butOk);
        pnMain.add(butCancel);

        return pnMain;
    }

    @Override
    protected KSGPanel buildCenter()
    {
        KSGPanel pnMain = new KSGPanel();
        form.addFormItem("항구명", txfPortName, 100);
        form.addFormItem("지역", txfPortArea, 100);
        form.addFormItem("나라", txfPortNationality, 100);
        form.addFormItem("지역코드", cbxArea, 100);
        form.addFormItem("비고", txAContents, 100);

        pnMain.add(form);
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            String port_name = txfPortName.getText();
            String port_area = txfPortArea.getText();
            String port_nationality = txfPortNationality.getText();
            MyEnum select =(MyEnum) cbxArea.getSelectedItem();
            String area_code = select.getField();
            String contents = txAContents.getText();


            CommandMap param = new CommandMap();            
    
            param.put("port_name", port_name);
            param.put("port_area", port_area);
            param.put("port_nationality", port_nationality);
            param.put("area_code", area_code);
            param.put("contents", contents);
            
            callApi("insertPort",param);
        }
        else if("취소".equals(command))
        {
            STATUS = CommonPopup.CANCEL;
            close();
        }
        
    }



    @Override
    public void updateView() {

        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

        if(success)
        {
            STATUS = CommonPopup.OK;
            close();
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }        
    }
    @Override
    public void componentShown(ComponentEvent e) {
        cbxArea.loadData(new CodeService().selectEnumById("area"));
        
    }


}
