package com.ksg.view.workbench.admin.dialog;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.CodeController;
import com.ksg.api.controll.PortController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;



public class CodeClassInsertPopup extends CommonPopup{



    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfCodeName = new JTextField(10);
    private JTextField txfCodeId = new JTextField(10);
    private JTextField txfPortNationality = new JTextField(10);
    private JTextField txfAreaCode = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);

    public CodeClassInsertPopup()
    {
        super();
        this.setTitle("항구 정보 추가");
        this.controller = new CodeController();
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
        form.addFormItem("코드ID", txfCodeId, 100);
        form.addFormItem("코드명", txfCodeName, 100);
        
        // form.addFormItem("나라", txfPortNationality, 100);
        // form.addFormItem("지역코드", txfAreaCode, 100);
        // form.addFormItem("비고", txAContents, 100);

        pnMain.add(form);
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            String code_class_name = txfCodeName.getText();
            String code_class_id = txfCodeId.getText();
            // String port_area = txfPortArea.getText();
            // String port_nationality = txfPortNationality.getText();
            // String area_code = txfAreaCode.getText();
            // String contents = txAContents.getText();


            CommandMap param = new CommandMap();            
    
            param.put("code_class_name", code_class_name);
            param.put("code_class_id", code_class_id);
            // param.put("port_area", port_area);
            // param.put("port_nationality", port_nationality);
            // param.put("area_code", area_code);
            // param.put("contents", contents);
            

            this.controller.call("insertCode",param, this);


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

