package com.ksg.view.workbench.masterdata.dialog;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;


import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.CompanyController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;

import java.awt.event.ComponentEvent;


public class CompanyUpdatePopup extends CommonPopup{

    

    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfCompanyName = new JTextField(10);
    private JTextField txfCompanyAbbr = new JTextField(10);
    private JTextField txfAgentName = new JTextField(10);
    private JTextField txfAgentAbbr = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);

    CommandMap param;

    
    public CompanyUpdatePopup(CommandMap param)
    {
        super();
        this.setTitle("선사 정보 수정");
        this.controller = new CompanyController();
        this.addComponentListener(this);
        this.param =param;
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

            CommandMap inputParam  = new CommandMap();            

            inputParam.setService_id("update");
            
            inputParam.put("id", this.param.get("id"));
            inputParam.put("company_name", company_name);
            inputParam.put("company_abbr", company_abbr);
            inputParam.put("agent_name", agent_name); 
            inputParam.put("agent_abbr", agent_abbr);
            inputParam.put("contents", contents);
            
            this.callApi("updateCompany",inputParam);
            


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
        String company_name = (String) param.get("company_name");
        String company_abbr = (String) param.get("company_abbr");
        String agent_name = (String) param.get("agent_name");
        String agent_abbr = (String) param.get("agent_abbr");
        String contents = (String) param.get("agent_abbr");

        txfCompanyName.setText(company_name);
        txfCompanyAbbr.setText(company_abbr);
        txfAgentName.setText(agent_name);
        txfAgentAbbr.setText(agent_abbr);
        txAContents.setText(contents);

        
    }


}
