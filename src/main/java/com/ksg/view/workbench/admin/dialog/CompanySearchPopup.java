package com.ksg.view.workbench.admin.dialog;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ksg.api.controll.CompanyController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;



public class CompanySearchPopup extends CommonPopup{

    
    private KSGDataGrid grid;
    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    private JTextField txfCompanyName = new JTextField(10);
    private JTextField txfCompanyAbbr = new JTextField(10);
    private JTextField txfAgentName = new JTextField(10);
    private JTextField txfAgentAbbr = new JTextField(10);
    private JTextArea txAContents = new JTextArea(5,5);
    private JTextField txfCompany = new JTextField(10);
    private JButton butSearch = new JButton("검색");
    

    public CompanySearchPopup()
    {
        super();
        this.setTitle("선사 정보 조회");
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


        int level[]={50,100};

        grid = new KSGDataGrid(level);
        
        grid.getTable().addColumn(new KSGTableColumn("company_name","선사명"));
		grid.getTable().addColumn(new KSGTableColumn("company_abbr","선사명 약어"));
		
        grid.getTable().initComp();

        KSGPanel pnSearch = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        pnSearch.add(new JLabel("선사명"));
        pnSearch.add(txfCompanyName);
        pnSearch.add(new JLabel("선사명약어"));
        pnSearch.add(txfCompanyAbbr);
        pnSearch.add(butSearch);

        butSearch.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                String company_name = txfCompanyName.getText();
                String company_abbr = txfCompanyAbbr.getText();
                CommandMap param = new CommandMap();
                

                param.put("company_name", company_name);    
                param.put("company_abbr", company_abbr); 
                
                callApi("selectCompany", param);
                
                
            }});
        pnMain.add(pnSearch,BorderLayout.NORTH);
        pnMain.add(grid);

        pnMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {
            
            

            // this.controller.call("insertCompany",param, this);
            int row=grid.getTable().getSelectedRow();
            if(row<0)return ;

            result=grid.getTable().getValueAt(row, "company_name");

            STATUS = CommonPopup.OK;
            close();



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

            if(result.getService_id().equals("selectCompany"))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    grid.getTable().clearReslult();
                }
                else{
                    grid.loadData(master);
                }            
            }
            // STATUS = CommonPopup.OK;
            // close();
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }        
    }

}
