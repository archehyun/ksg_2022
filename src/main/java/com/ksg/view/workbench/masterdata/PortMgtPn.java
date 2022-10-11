package com.ksg.view.workbench.masterdata;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ComponentEvent;
import com.ksg.api.controll.PortController;
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
import com.ksg.view.util.ExcelUtil;
import com.ksg.view.workbench.masterdata.dialog.PortInsertPopup;
import com.ksg.view.workbench.masterdata.dialog.PortUpdatePopup;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("PortMgtPn")
public class PortMgtPn extends MgtPn{

    private KSGDataGrid grid; 
    
    private JTextField txfPortName = new JTextField(10);

    private JButton butInsert = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDelete = new IconButton("images/icons8-delete-16.png");
    private JButton butExport = new IconButton("images/download.png");
    private JButton butImport = new IconButton("images/upload.png");

    private KSGComboBox cbxArea = new KSGComboBox<>();

    public PortMgtPn()
    {
        this("항구정보관리");
    }
    public PortMgtPn(String name)
    {
        super(name);
        
        this.setController(new PortController());
        this.addComponentListener(this);
        this.setName(name);

        this.add(buildCenter());

        this.add(buildSearch(),BorderLayout.NORTH);
    }

    private KSGPanel buildSearch()
    {
        KSGPanel pnMain = new KSGPanel();

        KSGPanel pnOption = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        lblPortCode = new KSGLabel("항구코드");
        lblPortCode.setPreferredSize(new Dimension(80,25));
        pnOption.add(lblPortCode);        
        pnOption.add(new JTextField(10));
        pnOption.add(new KSGLabel("항구명"));
        pnOption.add(txfPortName);
        pnOption.add(new KSGLabel("지역"));
        pnOption.add(cbxArea);
        
        KSGPanel pnControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton butSearch = new KSGButton("검색");
        butSearch.setActionCommand("search");
        butSearch.addActionListener(this);
        pnControl.add(butSearch);
        pnMain.add(pnOption);
        pnMain.add(pnControl,BorderLayout.EAST);
        return pnMain;
    }

    private KSGPanel buildCenter()
    {
        int level[]={50,100};

        grid = new KSGDataGrid(level);        
        grid.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
        grid.getTable().addColumn(new KSGTableColumn("port_name","항구명"));
		grid.getTable().addColumn(new KSGTableColumn("port_nationality","나라"));
		grid.getTable().addColumn(new KSGTableColumn("port_area","지역"));
		grid.getTable().addColumn(new KSGTableColumn("area_code","지역코드"));		
        grid.getTable().addColumn(new KSGTableColumn("event_date","등록일"));		
		grid.getTable().addMouseListener(new TableSelectListner());

        grid.getTable().initComp();
        KSGPanel pnInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butInsert.setActionCommand("insert");
        butDelete.setActionCommand("delete");
        butExport.setActionCommand("export");
        butImport.setActionCommand("import");

        butInsert.addActionListener(this);
        butDelete.addActionListener(this);
        butExport.addActionListener(this);
        butImport.addActionListener(this);

        pnInfo.add(butInsert);
        pnInfo.add(butDelete);
        pnInfo.add(butExport);
        pnInfo.add(butImport);
        butExport.setToolTipText("다운로드");
        butImport.setToolTipText("업로드");

        KSGPanel pnMain = new KSGPanel();
        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        pnMain.add(pnInfo,BorderLayout.NORTH);
        pnMain.add(grid);
        return pnMain;
    }

    
    private void fnSearch()
    {
        String port_name =txfPortName.getText();

        CommandMap param = new CommandMap();



        if(port_name!=null&&!port_name.equals(""))
        {
            param.put("port_name", port_name);
        }

        MyEnum area=(MyEnum) cbxArea.getSelectedItem();

        if(area!=null)
        {
            param.put("area_code", area.getField());
        }
        callApi("selectPort",param);
    }
  
    List master;

    private KSGLabel lblPortCode;
    @Override
    public void updateView() {
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");
		if(success)
		{   
            if(result.getService_id().equals("selectPort"))
            {
                master = (List) result.get("data");

                if(master.size()==0)
                {
                    grid.getTable().clearReslult();
                }
                else{
                    grid.loadData(master);
                }     
            }
            else if(result.getService_id().equals("exportPort"))
            {
                String data = (String) result.get("data");
                JOptionPane.showMessageDialog(this,"("+ data+ ") 엑셀 파일을 생성했습니다.");
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
            PortInsertPopup insertPopup = new PortInsertPopup();
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
                
                callApi("deletePort", param);
                
            }

        }
        else if("export".equals(command))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
            String date=format.format(Calendar.getInstance().getTime());
            CommandMap param = new CommandMap();

            param.put("filePath", "d:/port_"+date+".xlsx");
            
            callApi("exportPort", param);

        }
        
       
        
    }

    @Override
    public void componentShown(ComponentEvent e) {

        cbxArea.loadData(codeService.selectEnumById("area"));

        fnSearch();
        
    }

    class TableSelectListner extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getClickCount()>1)
			{
				JTable es = (JTable) e.getSource();
				Point p = e.getPoint();
				int row = es.rowAtPoint(p);

				if(row<0)
					return;


				CommandMap company=(CommandMap) grid.getTable().getValueAt(row);

				PortUpdatePopup updatePopup = new PortUpdatePopup(company);

				updatePopup .createAndUpdateUI();

                if(updatePopup.STATUS==CommonPopup.OK)
                {
                    fnSearch();
                }


			}
		}
    }
    
}
