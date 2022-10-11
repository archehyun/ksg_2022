package com.ksg.view.workbench.shcedule;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.List;

import com.ksg.api.controll.ScheduleController;

import com.ksg.api.model.CommandMap;
import com.ksg.api.model.MyEnum;
import com.ksg.view.common.button.KSGButton;
import com.ksg.view.common.combobox.KSGComboBox;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.label.KSGLabel;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.common.template.MgtPn;
import com.ksg.view.workbench.admin.dialog.CompanySearchPopup;

import java.awt.FlowLayout;

/**
 * 스케줄 정보관리 화면
 * 
 */
@Component("NomalSchedule")
public class NomalSchedule extends MgtPn{

    private KSGDataGrid gridHead;

    private KSGDataGrid gridDetail;

    private JButton butSearch = new KSGButton("검색");

    private JButton butCreate = new KSGButton("생성");

    private JButton butSearchFromPort = new IconButton("images/search.png");

    private JButton butSearchToPort = new IconButton("images/search.png");

    private KSGComboBox<MyEnum> cbxInOut = new KSGComboBox<MyEnum>();

    private JTextField txfFromPort = new JTextField(10);

    private JTextField txfToPort = new JTextField(10);

    private KSGLabel lblFromPort;
    

    public NomalSchedule()
    {
        this("nomalSchedule");
    }

    public NomalSchedule(String name)
    {
        super(name);

        this.setController(new ScheduleController());
        this.addComponentListener(this);
        this.add(buildMain());
        this.add(buildSearch(),BorderLayout.NORTH);

    }

    private JComponent buildMain()
    {
        int level[]={50,100};

        gridHead = new KSGDataGrid(level);

        KSGPanel pnMain =new KSGPanel(new BorderLayout(5,5));

        gridHead.getTable().addColumn(new KSGTableColumn("port_name","스케줄ID"));

		gridHead.getTable().addColumn(new KSGTableColumn("port_nationality","나라"));
        
        gridHead.getTable().initComp();        

        gridDetail = new KSGDataGrid(level);

        gridDetail.getTable().addColumn(new KSGTableColumn("table_id","테이블ID"));
		gridDetail.getTable().addColumn(new KSGTableColumn("inout_type","IN/OUT"));
		gridDetail.getTable().addColumn(new KSGTableColumn("from_port","출발항"));
        gridDetail.getTable().addColumn(new KSGTableColumn("to_port","도착항"));
        gridDetail.getTable().addColumn(new KSGTableColumn("from_date","출발일"));
        gridDetail.getTable().addColumn(new KSGTableColumn("to_date","도착일"));
		gridDetail.getTable().addColumn(new KSGTableColumn("area_code","지역코드"));				

        gridDetail.getTable().initComp();

        gridHead.setPreferredSize(new Dimension(200,0));

        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        pnMain.add(gridHead,BorderLayout.WEST);

        pnMain.add(gridDetail); 

        return pnMain;        
    }



    private KSGPanel buildSearch()
    {

        butSearchFromPort.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                CompanySearchPopup companySearchPopup = new CompanySearchPopup();
                companySearchPopup.createAndUpdateUI();

                if(companySearchPopup.STATUS==CommonPopup.OK)
                {
                    txfFromPort.setText((String)companySearchPopup.result);
                }

                
            }});


        KSGPanel pnMain = new KSGPanel();

        KSGPanel pnOption = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        
        lblFromPort = new KSGLabel("출발항");
        lblFromPort.setPreferredSize(new Dimension(80,25));
        pnOption.add(lblFromPort);        



        pnOption.add(txfFromPort);
        pnOption.add(butSearchFromPort);

        pnOption.add(new KSGLabel("도착항"));
        pnOption.add(txfToPort);
        pnOption.add(butSearchToPort);

        pnOption.add(new KSGLabel("IN/OUT"));
        
        pnOption.add(cbxInOut);



        KSGPanel pnControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butSearch.setActionCommand("search");

        butSearch.addActionListener(this);

        butCreate.setActionCommand("create");

        butCreate.addActionListener(this);

        pnControl.add(butSearch);

        pnControl.add(butCreate);

        pnMain.add(pnOption);

        pnMain.add(pnControl,BorderLayout.EAST);

        return pnMain;
    }

    @Override
    public void updateView() {
        
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

		if(success)
		{ 
            if(result.getService_id().equals("selectSchedule"))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    gridDetail.getTable().clearReslult();
                }
                else{
                    gridDetail.loadData(master);
                }            
            }
            else if(result.getService_id().equals("createSchedule"))
            {
                JOptionPane.showMessageDialog(this, "성공");
            }
            else{

                fnSearch();
            }
            
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }
    }


    private void create() {
        //String port_name =txfInput.getText();

        CommandMap param = new CommandMap();


        MyEnum option =(MyEnum) cbxInOut.getSelectedItem();

        if(option!=null)
        {
            String inout_type= option.getField();

            param.put("inout_type", inout_type);
        }

        callApi("createSchedule",param);
    }
    private void fnSearch() {
        //String port_name =txfInput.getText();

        CommandMap param = new CommandMap();


        MyEnum option =(MyEnum) cbxInOut.getSelectedItem();

        if(option!=null)
        {
            String inout_type= option.getField();

            param.put("inout_type", inout_type);
        }
        String fromPort=txfFromPort.getText();
        if(fromPort!=null&&!fromPort.equals(""))
        {
            param.put("from_port", fromPort);
        }
        String toPort=txfToPort.getText();
        if(toPort!=null&&!toPort.equals(""))
        {
            param.put("to_port", toPort);
        }

        callApi("selectSchedule",param);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command  = e.getActionCommand();

        if("search".equals(command))
        {

            fnSearch();
            
        }
        else if("insert".equals(command))
        {
            // PortInsertPopup insertPopup = new PortInsertPopup();
            // insertPopup.createAndUpdateUI();
        }
        else if("delete".equals(command))
        {

        }

        else if("create".equals(command))
        {
            create();
        }        
        
    }

    @Override
    public void componentShown(ComponentEvent e) {

        cbxInOut.loadData(codeService.selectEnumById("inout_type"));
    }

   
    
}
