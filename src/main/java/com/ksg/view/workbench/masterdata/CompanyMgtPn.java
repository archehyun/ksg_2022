package com.ksg.view.workbench.masterdata;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ComponentEvent;

import org.springframework.stereotype.Component;

import com.ksg.api.controll.CompanyController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.button.KSGButton;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.label.KSGLabel;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.common.template.MgtPn;
import com.ksg.view.workbench.masterdata.dialog.CompanyInsertPopup;
import com.ksg.view.workbench.masterdata.dialog.CompanyUpdatePopup;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;

@Component("CompanyMgtPn")
public class CompanyMgtPn extends MgtPn{


    private static final String STRING_VESSEL_TYPE 		= "선박타입";
	private static final String STRING_INPUTDATE 		= "등록일";
	private static final String STRING_VESSEL_COMPANY 	= "대표선사";
	private static final String STRING_VESSEL_MMSI 		= "MMSI";
	private static final String STRING_COMPANY_NAME 		= "선사명";
	private static final String STRING_VESSEL_ABBR 		= "선박명약어";
	private static final String STRING_CONTAINER_TYPE 	= "컨테이너 타입";

    
    
    private KSGDataGrid grid;
    private JTextField txfCompanyName = new JTextField(10);
    private JButton butInsert = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDelete = new IconButton("images/icons8-delete-16.png");
    private JButton butDownload = new IconButton("images/download.png");
    private JButton butUpload = new IconButton("images/upload.png");
    private JButton butSearch = new KSGButton("검색");
    private JTextField txfCompanyAbbr = new JTextField(10);
    private KSGLabel lblCompanyName;

    
    
    
    
    public CompanyMgtPn()
    {
        this("선사정보관리");
        
    }
    public CompanyMgtPn(String name)
    {
        super(name);
        this.addComponentListener(this);

        setController(new CompanyController());        

        this.add(buildCenter());
        this.add(buildSearch(),BorderLayout.NORTH);
        
    }
    private KSGPanel buildCenter()
    {
        int level[]={50,100};

        grid = new KSGDataGrid(level);
        grid.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
        grid.getTable().addColumn(new KSGTableColumn("company_name","선사명"));
		grid.getTable().addColumn(new KSGTableColumn("company_abbr","선사명 약어"));
		grid.getTable().addColumn(new KSGTableColumn("agent_name","에이전트 명"));
		grid.getTable().addColumn(new KSGTableColumn("agent_abbr","에이전트 약어"));		
        grid.getTable().addColumn(new KSGTableColumn("contents","비고"));		
		grid.getTable().addColumn(new KSGTableColumn("event_date","이벤트 일자"));       

        grid.getTable().addMouseListener(new TableSelectListner());

        grid.getTable().initComp();

        KSGPanel pnInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butInsert.addActionListener(this);

        butDelete.addActionListener(this);

        butDownload.addActionListener(this);

        butInsert.setActionCommand("insert");

        butDelete.setActionCommand("delete");

        butDownload.setActionCommand("export");

        pnInfo.add(butInsert);
        pnInfo.add(butDelete);
        pnInfo.add(butDownload);
        pnInfo.add(butUpload);
        butDownload.setToolTipText("다운로드");
        butUpload.setToolTipText("업로드");

        KSGPanel pnMain = new KSGPanel();

        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        pnMain.add(pnInfo,BorderLayout.NORTH);
        pnMain.add(grid);
        return pnMain;
    
    }

    private KSGPanel buildSearch()
    {
        KSGPanel pnMain = new KSGPanel();
        KSGPanel pnOption = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
       
        lblCompanyName = new KSGLabel("선사명");
        lblCompanyName.setPreferredSize(new Dimension(80,25));
        pnOption.add(lblCompanyName);
        pnOption.add(txfCompanyName);
        pnOption.add(new KSGLabel("선사명약어"));
        pnOption.add(txfCompanyAbbr);

        KSGPanel pnControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        butSearch.setActionCommand("search");
        butSearch.addActionListener(this);
        pnControl.add(butSearch);
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
            else if(result.getService_id().equals("exportCompany"))
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

    private void fnSearch()
    {
        String company_name =txfCompanyName.getText();
        String company_abbr = txfCompanyAbbr.getText();

        CommandMap param = new CommandMap();        

        param.put("company_name", company_name);    
        param.put("company_abbr", company_abbr); 
        
        callApi("selectCompany", param);

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
            CompanyInsertPopup insertPopup = new CompanyInsertPopup();

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
                
                callApi("deleteCompany", param);
                
            }
        }
        else if("export".equals(command))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
            String date=format.format(Calendar.getInstance().getTime());
            CommandMap param = new CommandMap();

            param.put("filePath", "d:/company_"+date+".xlsx");
            
            callApi("exportCompany", param);

        }
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

				CompanyUpdatePopup dialog = new CompanyUpdatePopup(company);

				dialog .createAndUpdateUI();

				// int result = dialog.result;

				// if(result==UpdateCompanyInfoDialog.SUCCESS)
				// {
				// 	fnSearch();
				// }



			}
		}

	}
    
    @Override
    public void componentShown(ComponentEvent e) {
      fnSearch();
        
    }
}
