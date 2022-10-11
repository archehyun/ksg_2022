package com.ksg.view.workbench.masterdata.dialog;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.workbench.admin.dialog.CompanySearchPopup;

import java.awt.event.ComponentEvent;
public class VesselInsertPopup extends CommonPopup{

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private JButton butSearchCompany = new IconButton("images/search.png");


    private KSGForm form = new KSGForm();
    private JTextField txfVesselName = new JTextField(10);
    private JTextField txfVesselAbbr = new JTextField(10);
    
    private JTextField txfVesselCompany = new JTextField(10);
    

    private JTextField txfVesselMMSI = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);

    private KSGComboBox<MyEnum> cbxVesselUse = new KSGComboBox<MyEnum>();
    private KSGComboBox<MyEnum> cbxVesselType = new KSGComboBox<MyEnum>();


    public VesselInsertPopup()
    {
        super();
        this.addComponentListener(this);
        this.setTitle("선박 정보 추가");
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


        butSearchCompany.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                CompanySearchPopup companySearchPopup = new CompanySearchPopup();
                companySearchPopup.createAndUpdateUI();

                if(companySearchPopup.STATUS==CommonPopup.OK)
                {
                    txfVesselCompany.setText((String)companySearchPopup.result);
                }

                
            }});
        txfVesselCompany.setEditable(false);
        KSGPanel pnMain = new KSGPanel();
        form.addFormItem("선박명", txfVesselName, 100);
        form.addFormItem("선박명약어", txfVesselAbbr, 100);
        KSGPanel pnCompany = new KSGPanel();
        pnCompany.add(txfVesselCompany);
        pnCompany.add(butSearchCompany,BorderLayout.EAST);

        form.addFormItem("선사", pnCompany, 100);


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
            
            String vessel_mmsi = txfVesselMMSI.getText();
            String vessel_company = txfVesselCompany.getText();
            
            String contents = txAContents.getText();

            MyEnum type= (MyEnum) cbxVesselType.getSelectedItem();
            String vessel_type  = type.getField();

            MyEnum use= (MyEnum) cbxVesselUse.getSelectedItem();
            String vessel_use  = use.getField();

            


            CommandMap param = new CommandMap();            
    
            param.put("vessel_name", vessel_name);
            param.put("vessel_abbr", vessel_abbr);
            param.put("vessel_type", vessel_type);
            param.put("vessel_mmsi", vessel_mmsi);
            param.put("vessel_company", vessel_company);
            param.put("vessel_use", vessel_use);
            param.put("contents", contents);
            
            this.callApi("insertVessel",param);

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
        
    }
}
