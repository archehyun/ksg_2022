package com.ksg.view.workbench.masterdata.dialog;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.VesselController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.api.service.CodeService;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;

import java.awt.event.ComponentEvent;

public class VesselUpdatePopup extends CommonPopup{

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");


    private KSGComboBox<MyEnum> cbxVesselUse = new KSGComboBox<MyEnum>();
    private KSGComboBox<MyEnum> cbxVesselType = new KSGComboBox<MyEnum>();
    CommandMap param;
    private KSGForm form = new KSGForm();
    private JTextField txfVesselName = new JTextField(10);
    private JTextField txfVesselAbbr = new JTextField(10);
    private JTextField txfVesselType = new JTextField(10);
    private JTextField txfVesselCompany = new JTextField(10);
    private JTextField txfVesselUse = new JTextField(10);

    private JTextField txfVesselMMSI = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);


    public VesselUpdatePopup(CommandMap param)
    {
        super();
        this.param = param;
        this.addComponentListener(this);
        this.setTitle("선박 정보 수정");

        this.controller = new VesselController();
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
        form.addFormItem("선박명", txfVesselName, 100);
        form.addFormItem("선박명약어", txfVesselAbbr, 100);
        form.addFormItem("선사", txfVesselCompany, 100);
        form.addFormItem("선박타입", cbxVesselType, 100);
        form.addFormItem("MMSI", txfVesselMMSI, 100);
        form.addFormItem("사용유무", cbxVesselUse, 100);
        form.addFormItem("비고", txAContents, 100);

        pnMain.add(form);
        return pnMain;
    }  

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            
            String vessel_name = txfVesselName.getText();
            String vessel_abbr = txfVesselAbbr.getText();
            MyEnum seletType= (MyEnum) cbxVesselType.getSelectedItem();
            if(seletType== null)return;
            String vessel_type = seletType.getField();
            String vessel_mmsi = txfVesselMMSI.getText();
            String vessel_company = txfVesselCompany.getText();
            MyEnum seletUse= (MyEnum) cbxVesselUse.getSelectedItem();
            if(seletUse== null)return;

            String vessel_use = seletUse.getField();
            String contents = txAContents.getText();


            CommandMap upparam = new CommandMap();            
            upparam.put("id", this.param.get("id"));
            upparam.put("vessel_name", vessel_name);
            upparam.put("vessel_abbr", vessel_abbr);
            upparam.put("vessel_type", vessel_type);
            upparam.put("vessel_mmsi", vessel_mmsi);
            upparam.put("vessel_company", vessel_company);
            upparam.put("vessel_use", vessel_use);
            upparam.put("contents", contents);
            
            callApi("updateVessel",upparam);
        }
        else if("취소".equals(command))
        {
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

        cbxVesselUse.loadData(new CodeService().selectEnumById("vessel_use"));
        cbxVesselType.loadData(new CodeService().selectEnumById("vessel_type"));

        

        String vessel_name = (String) param.get("vessel_name");
        String vessel_abbr = (String) param.get("vessel_abbr");
        String vessel_type = (String) param.get("vessel_type");
        String vessel_mmsi = (String) param.get("vessel_mmsi");
        String vessel_use = (String) param.get("vessel_use");
        String vessel_company = (String) param.get("vessel_company");
        String contents = (String) param.get("agent_abbr");

        cbxVesselType.setSelectedValue(vessel_type);
        cbxVesselUse.setSelectedValue(vessel_use);

        txfVesselName.setText(vessel_name);
        txfVesselAbbr.setText(vessel_abbr);
        txfVesselType.setText(vessel_type);
        txfVesselCompany.setText(vessel_company);
        txfVesselMMSI.setText(vessel_mmsi);
        txfVesselUse.setText(String.valueOf(vessel_use));
        txAContents.setText(contents);

        
    }    
}
