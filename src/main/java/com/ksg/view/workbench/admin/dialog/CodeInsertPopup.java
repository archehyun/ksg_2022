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



public class CodeInsertPopup extends CommonPopup{



    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");

    private KSGForm form = new KSGForm();
    private JTextField txfCodeClassId = new JTextField(10);
    private JTextField txfCodeName = new JTextField(10);
    private JTextField txfCodeId = new JTextField(10);
    private JTextField txfField1 = new JTextField(10);
    private JTextField txfField2 = new JTextField(10);
    private JTextField txfField3 = new JTextField(10);
    
    private String code_class_id;

    public CodeInsertPopup(String code_class_id)
    {
        super();
        this.code_class_id = code_class_id;
        this.setTitle("코드 상세 정보 추가");
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
        txfCodeClassId.setText(this.code_class_id);
        txfCodeClassId.setEditable(false);
        form.addFormItem("코드클래스명", txfCodeClassId, 100);
        
        form.addFormItem("코드명", txfCodeName, 100);
        form.addFormItem("필드1", txfField1, 100);
        form.addFormItem("필드2", txfField2, 100);
        form.addFormItem("필드3", txfField3, 100);


        pnMain.add(form);
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            String code_class_id = txfCodeClassId.getText();            String code_class_name = txfCodeName.getText();
            
            String code_name = txfCodeName.getText();
            String code_field1 = txfField1.getText();
            String code_field2 = txfField2.getText();
            String code_field3 = txfField3.getText();



            CommandMap param = new CommandMap();            
    
            param.put("code_class_id", code_class_id);
            
            param.put("code_name", code_name);
            param.put("code_field1", code_field1);
            param.put("code_field2", code_field2);
            param.put("code_field3", code_field3);

            

            this.controller.call("insertCodeDetail",param, this);


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

