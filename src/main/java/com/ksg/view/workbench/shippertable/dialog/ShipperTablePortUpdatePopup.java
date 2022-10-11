package com.ksg.view.workbench.shippertable.dialog;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

import com.ksg.api.controll.ShipperTableController;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.ShipperTableData;
import com.ksg.view.common.button.KSGButton;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.form.KSGForm;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.workbench.shippertable.TableBuilder;


import java.awt.event.ComponentEvent;

public class ShipperTablePortUpdatePopup extends CommonPopup{

    
    ShipperTableData tabledata;
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
    private KSGComboBox cbxType = new  KSGComboBox<>();

    private CommandMap param;
    private JList listPort = new JList<>();
    private KSGButton butInsert = new KSGButton("＋");
    int id;
    public ShipperTablePortUpdatePopup(int id, ShipperTableData tabledata)
    {
        super();
        this.id = id;
        this.tabledata = tabledata;
        this.addComponentListener(this);
        this.setTitle("광고 항구 수정");
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
        KSGPanel pnMain = new KSGPanel();

        form.addFormItem("테이블ID", txfTableId, 100);
        form.addFormItem("타이틀", txfTitle, 100);
        form.addFormItem("에이전트", txfAgent, 100);
        form.addFormItem("선사", txfCompany, 100);
        form.addFormItem("페이지", txfPage, 100);
        form.addFormItem("InboundIndex", txfInboundIndex, 100);
        form.addFormItem("OutboundIndex", txfOutboundIndex, 100);
        form.addFormItem("Data", txAData, 100);
        
        // pnMain.add(form);


        butInsert.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PortSearchPopup insertPopup = new PortSearchPopup();

                insertPopup.createAndUpdateUI();
    
                if(insertPopup.STATUS==CommonPopup.OK)
                {
                    // fnSearch();
                    DefaultListModel model= (DefaultListModel) listPort.getModel();
                    if(insertPopup.selectPort!=null)
                    model.addElement(insertPopup.selectPort.get("port_name"));
                    listPort.setModel(model);
                }
            }});


        Box pnControl = Box.createVerticalBox();
        
        pnControl.add(new KSGButton("▲"));
        pnControl.add(pnControl.createVerticalStrut(5));

        pnControl.add(new KSGButton("▼"));
        pnControl.add(pnControl.createVerticalStrut(5));
        pnControl.add(butInsert);
        pnControl.add(pnControl.createVerticalStrut(5));
        pnControl.add(new KSGButton("－"));
        pnControl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        pnMain.add(new JScrollPane( listPort));
        pnMain.add(pnControl,BorderLayout.EAST);
        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        return pnMain;
    } 
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("확인".equals(command))
        {

            ListModel model=listPort.getModel();

            int size=model.getSize();

            String[] port_array=new String[size];
            for(int i=0;i<size;i++)
            {
                port_array[i] = (String) model.getElementAt(i);
            }

            this.tabledata.setPorts(port_array);


            CommandMap param = new CommandMap();

            param.put("id",id);
            
            param.put("data",  tabledata.toString());

            result = tabledata;

            callApi("updateShipperTableData", param);

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
       
        // String table_id = (String) param.get("table_id");
        // String title = (String) param.get("title");
        // String company = (String) param.get("company");
        // String outbound_index = (String) param.get("outbound_index");
        // String inbound_index = (String) param.get("inbound_index");
        // String agent_name = (String) param.get("agent");
        // int page = (int)param.get("page");
        
        // txfTitle.setText(title);
        // txfTableId.setText(table_id);
        // txfCompany.setText(company);
        // txfInboundIndex.setText(inbound_index);
        // txfOutboundIndex.setText(outbound_index);
        // txfAgent.setText(agent_name);
        // txfPage.setText(String.valueOf(page));

        

        TableBuilder tableBuilder = new TableBuilder(tabledata);
        String portArray[] = tableBuilder.getPorts();
        DefaultListModel demoList = new DefaultListModel();
        for(String port:portArray)
        {
            demoList.addElement(port);
        }

        listPort.setModel(demoList);
        // txAData.setText(tableBuilder.getPorts()[0]);
    }

}


