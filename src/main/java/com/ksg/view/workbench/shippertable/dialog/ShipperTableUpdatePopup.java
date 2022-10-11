package com.ksg.view.workbench.shippertable.dialog;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;


import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;


import com.ksg.api.controll.ShipperTableController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.workbench.admin.dialog.CompanySearchPopup;

import java.awt.event.ComponentEvent;

public class ShipperTableUpdatePopup extends CommonPopup{

    

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfTableId = new JTextField(10);
    private JTextField txfTitle = new JTextField(10);
    private JTextField txfAgent = new JTextField(10);
    private JTextField txfCompany = new JTextField(10);
    private JTextField txfPage = new JTextField(10);
    private JTextField txfInboundFromIndex = new JTextField(10);
    private JTextField txfInboundToIndex = new JTextField(10);
    private JTextField txfOutboundFromIndex = new JTextField(10);
    private JTextField txfOutboundToIndex = new JTextField(10);
    private JTextArea txAData = new JTextArea(5,5);
    private KSGComboBox cbxType = new  KSGComboBox<>();
    private JButton butSearchCompany = new IconButton("images/search.png");
    
    private int id;
    private CommandMap param;
    public ShipperTableUpdatePopup(CommandMap param)
    {
        super();
        this.param = param;
        this.addComponentListener(this);
        this.setTitle("광고 테이블정보 수정");
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
        form.addFormItem("테이블 타입", cbxType, 100);
        form.addFormItem("페이지", txfPage, 100);
        form.addFormItem("InboundFromIndex", txfInboundFromIndex, 100);
        form.addFormItem("InboundToIndex", txfInboundToIndex, 100);
        form.addFormItem("OutboundFromIndex", txfOutboundFromIndex, 100);
        form.addFormItem("OutboundToIndex", txfOutboundToIndex, 100);
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
            String inbound_from_index = txfInboundFromIndex.getText();
            String inbound_to_index = txfInboundToIndex.getText();
            String outbound_from_index = txfOutboundFromIndex.getText();
            String outbound_to_index = txfOutboundToIndex.getText();
            int page = Integer.parseInt(txfPage.getText());
            MyEnum tabelType= (MyEnum) cbxType.getSelectedItem();
            if(tabelType== null)return;

            String tabel_type = tabelType.getField();

            CommandMap param = new CommandMap();
            
            param.put("id",this.param.get("id"));
            param.put("table_id", table_id);
            param.put("title", title);
            param.put("agent", agent);
            param.put("company", company);
            param.put("table_type",tabel_type);
            
            param.put("page", page);
            param.put("outbound_from_index", outbound_from_index);
            param.put("outbound_to_index", outbound_to_index);
            param.put("inbound_from_index", inbound_from_index);            
            param.put("inbound_to_index", inbound_to_index);            
            callApi("updateShipperTable",param);
            


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

        
       
        String table_id = (String) param.get("table_id");
        String title = (String) param.get("title");
        String company = (String) param.get("company");
        String outbound_from_index = (String) param.get("outbound_from_index");
        String outbound_to_index = (String) param.get("outbound_to_index");
        String inbound_from_index = (String) param.get("inbound_from_index");
        String inbound_to_index = (String) param.get("inbound_to_index");
        String agent_name = (String) param.get("agent");
        String table_type = (String) param.get("table_type");
        int page = (int)param.get("page");

        cbxType.setSelectedValue(table_type);
        
        txfTitle.setText(title);
        txfTableId.setText(table_id);
        txfCompany.setText(company);
        txfInboundFromIndex.setText(inbound_from_index);
        txfInboundToIndex.setText(inbound_to_index);
        txfOutboundFromIndex.setText(outbound_from_index);
        txfOutboundToIndex.setText(outbound_to_index);
        txfAgent.setText(agent_name);
        txfPage.setText(String.valueOf(page));
    }

}


