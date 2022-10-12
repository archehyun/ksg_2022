package com.ksg.view.workbench.masterdata;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import com.ksg.api.controll.VesselController;
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
import com.ksg.view.workbench.masterdata.dialog.VesselInsertPopup;
import com.ksg.view.workbench.masterdata.dialog.VesselUpdatePopup;


import java.awt.event.ComponentEvent;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
@Slf4j
@Component("VesselMgtPn")
public class VesselMgtPn extends MgtPn{


    private static final String STRING_VESSEL_TYPE 		= "선박타입";
	private static final String STRING_INPUTDATE 		= "등록일";
	private static final String STRING_VESSEL_COMPANY 	= "대표선사";
	private static final String STRING_VESSEL_MMSI 		= "MMSI";
	private static final String STRING_VESSEL_NAME 		= "선박명";
	private static final String STRING_VESSEL_ABBR 		= "선박명약어";
	private static final String STRING_CONTAINER_TYPE 	= "컨테이너 타입";

    private KSGDataGrid grid;

    private JTextField txfInput = new JTextField(10);
    private JButton butSearch = new KSGButton("검색");
    private JButton butInsert = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDelete = new IconButton("images/icons8-delete-16.png");
    private JButton butDownload = new IconButton("images/download.png");
    private JButton butUpload = new IconButton("images/upload.png");
    private JButton butSearchCompany = new IconButton("images/search.png");
    private KSGComboBox<MyEnum> cbxUseYn = new KSGComboBox<MyEnum>();
    private KSGComboBox<MyEnum> cbxVesselType = new KSGComboBox<MyEnum>();
    private JTextField txfCompany = new JTextField(10);
    private KSGLabel lblCompany;
    private KSGLabel lblVesselCode;

    public VesselMgtPn()
    {
        this("선박정보관리");
    }
    public VesselMgtPn(String name)
    {
        super(name);

        this.setController(new VesselController());
        
        this.addComponentListener(this);
        // cbxUseYn.loadData(codeService.selectEnumById("vessel_use"));
        // this.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        this.add(buildCenter());
        this.add(buildSearch(),BorderLayout.NORTH);
        
    }
    private KSGPanel buildCenter()
    {
        int level[]={50,100};

        grid = new KSGDataGrid(level);
        grid.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
        grid.getTable().addColumn(new KSGTableColumn("vessel_name",STRING_VESSEL_NAME));
		grid.getTable().addColumn(new KSGTableColumn("vessel_abbr",STRING_VESSEL_ABBR));
        grid.getTable().addColumn(new KSGTableColumn("vessel_type","선박타입"));
		grid.getTable().addColumn(new KSGTableColumn("vessel_mmsi",STRING_VESSEL_MMSI));
        grid.getTable().addColumn(new KSGTableColumn("vessel_use","사용유무",50));
		grid.getTable().addColumn(new KSGTableColumn("vessel_company",STRING_VESSEL_COMPANY));		
        grid.getTable().addColumn(new KSGTableColumn("contents","비고"));		
		grid.getTable().addColumn(new KSGTableColumn("event_date",STRING_INPUTDATE,400));       
        grid.getTable().addMouseListener(new TableSelectListner());
        grid.getTable().initComp();

        KSGPanel pnInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));
        butInsert.setActionCommand("insert");
        butDelete.setActionCommand("delete");
        butDownload.setActionCommand("export");
        butInsert.addActionListener(this);
        butDelete.addActionListener(this);
        butDownload.addActionListener(this);

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

        txfCompany.setEditable(false);
        KSGPanel pnMain = new KSGPanel();
       
        KSGPanel pnOption1 = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        KSGPanel pnOption2 = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        KSGPanel pnControl = new KSGPanel();
        //pnControl.setLayout(new BoxLayout(pnControl, BoxLayout.Y_AXIS));
        cbxUseYn.setSelectedIndex(-1);
        cbxVesselType.setSelectedIndex(-1);

        
        lblVesselCode = new KSGLabel("선박코드");
        lblVesselCode.setPreferredSize(new Dimension(80,25));
        pnOption1.add(lblVesselCode);        
        pnOption1.add(new JTextField(10));
        pnOption1.add(new KSGLabel("사용유무"));        
        pnOption1.add(cbxUseYn);
        pnOption1.add(new KSGLabel("선박타입"));        
        pnOption1.add(cbxVesselType);
        pnOption1.add(new KSGLabel("선박명"));
        pnOption1.add(txfInput);

        
        lblCompany = new KSGLabel("선사명");
        lblCompany.setPreferredSize(new Dimension(80,25));
        pnOption2.add(lblCompany);
        pnOption2.add(txfCompany);
        pnOption2.add(butSearchCompany);


        butSearchCompany.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                CompanySearchPopup companySearchPopup = new CompanySearchPopup();
                companySearchPopup.createAndUpdateUI();

                if(companySearchPopup.STATUS==CommonPopup.OK)
                {
                    txfCompany.setText((String)companySearchPopup.result);
                }

                
            }});
        butSearch.setActionCommand("search");
        butSearch.addActionListener(this);
        butSearchCompany.addActionListener(this);
        butSearch.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        pnControl.add(butSearch,BorderLayout.SOUTH);
        

        pnControl.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));
        Box bxo =Box.createVerticalBox();

        bxo.add(pnOption1);
        bxo.add(pnOption2);

        pnMain.add(bxo);
        pnMain.add(pnControl,BorderLayout.EAST);
        
        Color color = new Color(199, 220, 255);
        // pnOption1.setBackground(color);
        // pnOption2.setBackground(color);
        // pnControl.setBackground(color);
        // bxo.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // pnMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnMain.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color));

        return pnMain;
    }
    private void fnSearch()
    {
        CommandMap param = new CommandMap();
        String vessel_name =txfInput.getText();
        String vessel_company = txfCompany.getText();
        MyEnum selectType= (MyEnum) cbxVesselType.getSelectedItem();
        MyEnum selectUse= (MyEnum) cbxUseYn.getSelectedItem();
        if(selectType!=null)  param.put("vessel_type", selectType.getField());
        if(selectUse!=null)  param.put("vessel_use", selectUse.getField());        
        
        param.setService_id("search");

        param.put("vessel_name", vessel_name);
        param.put("vessel_company", vessel_company);

        callApi("selectVessel",param);
    }
    @Override
    public void updateView() {
        
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");
		if(success)
		{   

            if(result.getService_id().equals("selectVessel"))
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
            else if(result.getService_id().equals("exportVessel"))
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
        if("searchPort".equals(command))
        {
           // fnSearch();
        }
        else if("insert".equals(command))
        {
            VesselInsertPopup insertPopup = new VesselInsertPopup();
            insertPopup.createAndUpdateUI();
        }
        else if("delete".equals(command))
        {
            
        }
        else if("export".equals(command))
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String date=format.format(Calendar.getInstance().getTime());

            CommandMap param = new CommandMap();

            param.put("filePath", "d:/vessel_"+date+".xlsx");
            
            callApi("exportVessel", param);

        }
        
    }
    @Override
    public void componentShown(ComponentEvent e) {

        cbxUseYn.loadData(codeService.selectEnumById("vessel_use"));
        cbxVesselType.loadData(codeService.selectEnumById("vessel_type"));

        cbxUseYn.setSelectedIndex(-1);
        cbxVesselType.setSelectedIndex(-1);

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

				VesselUpdatePopup dialog = new VesselUpdatePopup(company);

				dialog .createAndUpdateUI();

				// int result = dialog.result;

				// if(result==UpdateCompanyInfoDialog.SUCCESS)
				// {
				// 	fnSearch();
				// }



			}
		}

	}
   

}

