package com.ksg.view.workbench.masterdata.dialog;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;


import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.CompanyController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;



public class CompanyInsertPopup extends CommonPopup{

    

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfCompanyName = new JTextField(10);
    private JTextField txfCompanyAbbr = new JTextField(10);
    private JTextField txfAgentName = new JTextField(10);
    private JTextField txfAgentAbbr = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);

    public CompanyInsertPopup()
    {
        super();
        this.setTitle("선사 정보 추가");
        this.controller = new CompanyController();
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
        form.addFormItem("선사명", txfCompanyName, 100);
        form.addFormItem("선사약어", txfCompanyAbbr, 100);
        form.addFormItem("에이전트명", txfAgentName, 100);
        form.addFormItem("에이전트약어", txfAgentAbbr, 100);
        form.addFormItem("비고", txAContents, 100);
        
        pnMain.add(form);
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            String company_name = txfCompanyName.getText();
            String company_abbr = txfCompanyAbbr.getText();
            String agent_name = txfAgentName.getText();
            String agent_abbr = txfAgentAbbr.getText();
            String contents = txAContents.getText();


            CommandMap param = new CommandMap();            
    
            param.put("company_name", company_name);
            param.put("company_abbr", company_abbr);
            param.put("agent_name", agent_name);
            param.put("agent_abbr", agent_abbr);
            param.put("contents", contents);
            

            this.controller.call("insertCompany",param, this);


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

}
