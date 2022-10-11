package com.ksg.view.workbench.shippertable.dialog;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;


import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ComponentEvent;
import java.awt.*;
import com.ksg.api.controll.CompanyController;
import com.ksg.api.controll.ShipperTableController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.workbench.admin.dialog.CompanySearchPopup;



public class ShipperTableInsertPopup extends CommonPopup{

    

    private JButton butOk = new JButton("확인");

    private JButton butCancel = new JButton("취소");

    private KSGForm form = new KSGForm();

    private JTextField txfTableId = new JTextField(10);

    private JTextField txfTitle = new JTextField(10);
    private JTextField txfAgent = new JTextField(10);
    private JTextField txfCompany = new JTextField(10);
    private JTextField txfPage = new JTextField(10);
    private JTextField txfInboundIndex = new JTextField(10);
    private JTextField txfOutboundIndex = new JTextField(10);
    private JTextArea txAData = new JTextArea(5,5);
    private JButton butSearchCompany = new IconButton("images/search.png");
    private KSGComboBox cbxType = new  KSGComboBox<>();
    

    public ShipperTableInsertPopup()
    {
        super();
        this.setTitle("광고 테이블정보 추가");
        this.addComponentListener(this);
        this.controller = new ShipperTableController();
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
                    txfCompany.setText((String)companySearchPopup.result);
                }

                
            }});
        KSGPanel pnMain = new KSGPanel();
        form.addFormItem("테이블ID", txfTableId, 100);
        form.addFormItem("타이틀", txfTitle, 100);
        form.addFormItem("에이전트", txfAgent, 100);
        
        KSGPanel pnCompany = new KSGPanel();
        txfCompany.setEditable(false);
        pnCompany.add(txfCompany);
        pnCompany.add(butSearchCompany,BorderLayout.EAST);

        form.addFormItem("선사", pnCompany, 100);
        form.addFormItem("타입", cbxType, 100);
        form.addFormItem("페이지", txfPage, 100);
        form.addFormItem("InboundIndex", txfInboundIndex, 100);
        form.addFormItem("OutboundIndex", txfOutboundIndex, 100);
        form.addFormItem("Data", txAData, 100);
        
        pnMain.add(form);
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            String table_id = txfTableId.getText();
            String title = txfTitle.getText();
            String agent = txfAgent.getText();
            String company = txfCompany.getText();
            String inbound_index = txfInboundIndex.getText();
            String outbound_index = txfOutboundIndex.getText();
            int page = Integer.parseInt(txfPage.getText());
            String data = txAData.getText();
            MyEnum typeEnum = (MyEnum) cbxType.getSelectedItem();



            CommandMap param = new CommandMap();            
    
            param.put("table_id", table_id);
            param.put("title", title);
            param.put("agent_name", agent);
            param.put("company", company);
            param.put("table_type", typeEnum.getField());
            param.put("page", page);
            param.put("outbound_index", outbound_index);
            param.put("inbound_index", inbound_index);            

            this.callApi("insertShipperTable", param);
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

        cbxType.loadData(codeService.selectEnumById("table_type"));
        // cbxSearch.loadData(codeService.selectEnumById("table_search"));
    }

}

