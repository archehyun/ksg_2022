package com.ksg.view.workbench.masterdata.dialog;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;


import javax.swing.JButton;


import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.CompanyController;
import com.ksg.api.controll.PortController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.api.service.CodeService;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;

import java.awt.event.ComponentEvent;

/**
 * 항구 정보 업데이트 팝업
 */
public class PortUpdatePopup extends CommonPopup{

    CommandMap param;

    private JButton butOk = new JButton("확인");

    private JButton butCancel = new JButton("취소");

    private KSGForm form = new KSGForm();

    private JTextField txfPortName = new JTextField(10);

    private JTextField txfPortArea = new JTextField(10);
    
    private JTextField txfPortNationality = new JTextField(10);

    private JTextField txfAreaCode = new JTextField(10);

    private JTextArea txAContents = new JTextArea(5,5);

    private KSGComboBox<MyEnum> cbxAreaCode = new KSGComboBox<MyEnum>();

    public PortUpdatePopup(CommandMap param)
    {
        super();

        this.setTitle("항구 정보 수정");

        this.controller = new PortController();

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

        form.addFormItem("항구명", txfPortName, 100);

        form.addFormItem("지역", txfPortArea, 100);

        form.addFormItem("나라", txfPortNationality, 100);

        form.addFormItem("지역코드", cbxAreaCode, 100);

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
            
            String contents = txAContents.getText();


            MyEnum seletArea= (MyEnum) cbxAreaCode.getSelectedItem();
            if(seletArea== null)return;

            String area_code = seletArea.getField();

            CommandMap inputParam = new CommandMap();            
            inputParam.put("id", param.get("id"));
            inputParam.put("port_name", port_name);
            inputParam.put("port_area", port_area);
            inputParam.put("port_nationality", port_nationality);
            inputParam.put("area_code", area_code);
            inputParam.put("contents", contents);
            
            callApi("updatePort",inputParam);

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
    /* (non-Javadoc)
     * @see com.ksg.view.common.template.CommonPopup#componentShown(java.awt.event.ComponentEvent)
     */
    @Override
    public void componentShown(ComponentEvent e) { 


        cbxAreaCode.loadData(new CodeService().selectEnumById("area"));
        String port_name = (String) param.get("port_name");
        String port_area = (String) param.get("port_area");
        String port_nationality = (String) param.get("port_nationality");
        String area_code = (String) param.get("area_code");
        String contents = (String) param.get("agent_abbr");

        txfPortName.setText(port_name);
        txfPortArea.setText(port_area);
        txfPortNationality.setText(port_nationality);
        txfAreaCode.setText(area_code);
        txAContents.setText(contents);
        cbxAreaCode.setSelectedValue(area_code);

        
    }
    
}
