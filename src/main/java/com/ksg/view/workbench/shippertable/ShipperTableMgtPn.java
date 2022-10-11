package com.ksg.view.workbench.shippertable;


import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JOptionPane;

import javax.swing.JTable;

import javax.swing.JTextField;



import org.springframework.stereotype.Component;
import java.awt.event.ComponentEvent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import com.ksg.api.controll.ShipperTableController;
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
import com.ksg.view.common.template.DatePiker;
import com.ksg.view.common.template.MgtPn;
import com.ksg.view.workbench.shippertable.dialog.ShipperTableInsertPopup;


import lombok.extern.slf4j.Slf4j;

/**
 * 광고정보 관리 화면
 * 
 */
@Slf4j
@Component("ShipperTableMgtPn")
public class ShipperTableMgtPn extends MgtPn {

    private KSGDataGrid grid;
    
    private KSGButton butSearch = new KSGButton("검색");
    private JButton butInsert = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDelete = new IconButton("images/icons8-delete-16.png");
    private JButton butDownload = new IconButton("images/download.png");
    private JButton butUpload = new IconButton("images/upload.png");
    private CardLayout cardLayout = new CardLayout();
    private KSGPanel pnCardMain = new KSGPanel();

    private DataGrid pnData = new DataGrid();
    
    private KSGComboBox<MyEnum> cbxGubun = new KSGComboBox<MyEnum>();

    private KSGComboBox<MyEnum> cbxSearch = new KSGComboBox<MyEnum>();

    private int id;

    private KSGLabel lblInputDate;

    public ShipperTableMgtPn()
    {
        this("광고정보관리");
    }

    public ShipperTableMgtPn(String name)
    {
        super(name);
        this.addComponentListener(this);        
        this.setController(new ShipperTableController());
        this.add(buildCenter());
        this.add(buildSearch(),BorderLayout.NORTH);
    }

    private KSGPanel buildSearch()
    {
        KSGPanel pnMain = new KSGPanel();

        KSGPanel pnOption = new KSGPanel(new FlowLayout(FlowLayout.LEFT));

        lblInputDate = new KSGLabel("입력일자");
        lblInputDate.setPreferredSize(new Dimension(80,25));
        pnOption.add(lblInputDate);
        pnOption.add(new DatePiker());
        pnOption.add(new KSGLabel(" 테이블 구분"));
        pnOption.add(cbxGubun);
        pnOption.add(new KSGLabel(" 항목"));
        pnOption.add(cbxSearch);
        pnOption.add(new JTextField(10));
        

        KSGPanel pnControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butSearch.setActionCommand("search");

        butSearch.addActionListener(this);
        pnControl.add(butSearch);
        pnMain.add(pnOption);
        pnMain.add(pnControl,BorderLayout.EAST);
        return pnMain;
    }

    private KSGPanel buildCenter()
    {
        int level[]={10,20};

        grid = new KSGDataGrid(level);        
        grid.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
        grid.getTable().addColumn(new KSGTableColumn("table_id","테이블id"));
		grid.getTable().addColumn(new KSGTableColumn("title","제목",200));
        grid.getTable().addColumn(new KSGTableColumn("event_date","입력일자",120)) ;
        grid.getTable().addColumn(new KSGTableColumn("table_type","테이블 타입"));
		grid.getTable().addColumn(new KSGTableColumn("agent","에이전트"));
		grid.getTable().addColumn(new KSGTableColumn("company","선사"));		
        grid.getTable().addColumn(new KSGTableColumn("page","페이지"));		
        grid.getTable().addColumn(new KSGTableColumn("inbound_form_index","인바운드FROM"));		
        grid.getTable().addColumn(new KSGTableColumn("inbound_to_index","인바운드TO"));		

        grid.getTable().addColumn(new KSGTableColumn("outbound_from_index","아웃바운드FROM"));		
        grid.getTable().addColumn(new KSGTableColumn("outbound_to_index","아웃바운드TO"));		
        
        //grid.getTable().addColumn(new KSGTableColumn(" data","데이터"));	
		
        grid.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        grid.getTable().initComp();

        grid.getTable().addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) 
		    {
                if(e.getClickCount()>1)
                {
                    int row = grid.getTable().getSelectedRow();

                    if(row<0) return; 
                    
                    id = (int) grid.getTable().getValueAt(row, "id");

                    String strData=(String) grid.getTable().getValueAt(row, "data");
                    
                    pnData.setParam((CommandMap) grid.getTable().getValueAt(row));
                    
                    pnData.loadData(strData);                    

                    cardLayout.show(pnCardMain, "data");
                }
                
            }
        });


        KSGPanel pnInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        
        butInsert.setActionCommand("insert");
        butDelete.setActionCommand("delete");
        butInsert.addActionListener(this);
        butDelete.addActionListener(this);
        
        pnInfo.add(butInsert);
        pnInfo.add(butDelete);
        pnInfo.add(butDownload);
        pnInfo.add(butUpload);

        butDownload.setToolTipText("다운로드");

        butUpload.setToolTipText("업로드");

        KSGPanel pnMain = new KSGPanel();
        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        pnMain.add(pnInfo,BorderLayout.NORTH);

        pnCardMain.setLayout(cardLayout);

        grid.setName("grid");

   
        pnData.setName("data");

        pnData.butClose.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {             

                cardLayout.show(pnCardMain, "grid");
            }
        });

        pnData.butSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {

                CommandMap param = new CommandMap();

                param.put("id", id);

                param.put("data",  pnData.getText());
                param.put("table_id", pnData.getParam().get("table_id"));
                
                param.put("inbound_from_index", pnData.getParam().get("inbound_from_index"));
                param.put("inbound_to_index", pnData.getParam().get("inbound_to_index"));

                param.put("outbound_from_index", pnData.getParam().get("outbound_from_index"));
                param.put("outbound_to_index", pnData.getParam().get("outbound_to_index"));


                callApi("updateShipperTableData", param);

                cardLayout.show(pnCardMain, "grid");
            }
        });



        pnCardMain.add("grid",grid);
        pnCardMain.add("data",pnData);

        pnMain.add(pnCardMain);
        return pnMain;
    }

    private void fnSearch()
    {
        CommandMap param = new CommandMap(); 
        
        MyEnum selectType= (MyEnum) cbxGubun.getSelectedItem();
        
        if(selectType!=null)  param.put("table_type", selectType.getField());


        callApi("selectShipperTable", param);

    }

    @Override
    public void updateView() {

        log.info("update view");
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

		if(success)
		{ 
            if(result.getService_id().equals("selectShipperTable"))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    grid.loadData(new ArrayList<>());
                }
                else{

                    grid.loadData(master);
                    
                }            
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command  = e.getActionCommand();

        if("search".equals(command))
        {
            fnSearch();
        }
        else if("insert".equals(command))
        {
            ShipperTableInsertPopup insertPopup = new ShipperTableInsertPopup();

            insertPopup.createAndUpdateUI();

            if(insertPopup.STATUS==CommonPopup.OK)
            {
                fnSearch();
            }
        }
        else if("delete".equals(command))
        {
            int row=grid.getTable().getSelectedRow();

            if(row>-1)
            {
                Object value=grid.getTable().getValueAt(row, "id");

                CommandMap param = new CommandMap();
                param.setService_id("delete");
                param.put("id", value);
                
                callApi("deleteShipperTable", param);
                
            }
        }
    }
    @Override
    public void componentShown(ComponentEvent e) {

        cbxGubun.loadData(codeService.selectEnumById("table_type"));
        cbxSearch.loadData(codeService.selectEnumById("table_search"));
        fnSearch();
    }

    
    
    
}

