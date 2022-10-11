package com.ksg.view.workbench.shippertable.dialog;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;


import com.ksg.api.controll.PortController;

import com.ksg.api.model.CommandMap;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PortSearchPopup extends CommonPopup{

    
    private KSGDataGrid grid;

    private JButton butSearch = new JButton("검색");
    private JButton butOk = new JButton("확인");
    private JButton butCancel = new JButton("취소");
    private KSGForm form = new KSGForm();
    public CommandMap selectPort;
    private JTextField txfInput = new JTextField(10);
    
    

    public PortSearchPopup()
    {
        super();
        this.setTitle("항구정보조회");
        this.controller = new PortController();
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

        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        KSGPanel pnSearch = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        pnSearch.add(new JLabel("항구명"));
        pnSearch.add(txfInput);
        pnSearch.add(butSearch);
        butSearch.setActionCommand("search");
        butSearch.addActionListener(this);
        int level[]={50,100};

        grid = new KSGDataGrid(level);        

        grid.getTable().addColumn(new KSGTableColumn("port_name","항구명"));
		grid.getTable().addColumn(new KSGTableColumn("port_nationality","나라"));
        grid.getTable().initComp();

        grid.getTable().addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) 
		    {

                int row = grid.getTable().getSelectedRow();
                    if(row<0) return;

                if(e.getClickCount()>0)
                {
                    selectPort = (CommandMap) grid.getTable().getValueAt(row);                    
                   
                }
                if(e.getClickCount()>1)
                {
                    selectPort = (CommandMap) grid.getTable().getValueAt(row); 
                    STATUS = CommonPopup.OK; 
                    close();
                }
                
            }
        });


        pnMain.add(pnSearch,BorderLayout.NORTH);
        pnMain.add(grid);
        return pnMain;
    }
    
    private void fnSearch()
    {
        String port_name =txfInput.getText();

        CommandMap param = new CommandMap();

        param.put("port_name", port_name);

        callApi("selectPort",param);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();



        if("search".equals(command))
        {
           fnSearch();
          
    
            
        }

        if("확인".equals(command))
        {         

            STATUS = CommonPopup.OK;
            close();
          
           // this.controller.call("insertShipperTable",param);


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

            if(result.getService_id().equals("selectPort"))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    grid.getTable().clearReslult();
                }
                else{
                    grid.getTable().setResultData(master);
                }     
            }
            else{
                STATUS = CommonPopup.OK;
                close();

            }
            
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }        
    }

}

