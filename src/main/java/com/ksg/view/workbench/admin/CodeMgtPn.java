package com.ksg.view.workbench.admin;

import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import org.springframework.stereotype.Component;

import java.awt.Dimension;
import com.ksg.api.controll.CodeController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.button.KSGButton;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.label.KSGLabel;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.CommonPopup;
import com.ksg.view.common.template.MgtPn;
import com.ksg.view.workbench.admin.dialog.CodeClassInsertPopup;
import com.ksg.view.workbench.admin.dialog.CodeInsertPopup;
import com.ksg.view.workbench.admin.dialog.CodeUpdatePopup;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.Point;
@Component("CodeMgtPn")
public class CodeMgtPn extends MgtPn{

    KSGDataGrid gridHeader;

    KSGDataGrid gridDetail;

    private JTextField txfInput = new JTextField(10);
    private JButton butSearch = new KSGButton("검색");
    private JButton butInsert = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDelete = new IconButton("images/icons8-delete-16.png");
    private JButton butInsertDetail = new IconButton("images/icons8-plus-+-64.png");
    private JButton butDeleteDetail = new IconButton("images/icons8-delete-16.png");
    SelectionListner selectionListner = new SelectionListner();

    private KSGLabel lblCodeName;


    public CodeMgtPn()
    {
        this("코드정보관리");
    }
    public CodeMgtPn(String name)
    {
        super(name);
        this.addComponentListener(this);

        setController(new CodeController());        

        this.setName(name);

        this.add(buildCenter());
        this.add(buildSearch(),BorderLayout.NORTH);
        
    }

    private KSGPanel buildCenter()
    {
        int level[]={50,100};

        gridHeader = new KSGDataGrid(level);
        gridDetail = new KSGDataGrid(level);


        gridHeader.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
        gridHeader.getTable().addColumn(new KSGTableColumn("code_class_id","코드 ID"));
		gridHeader.getTable().addColumn(new KSGTableColumn("code_class_name","코드 명"));
        gridHeader.getTable().addColumn(new KSGTableColumn("event_date","등록일"));

        gridHeader.getTable().getSelectionModel().addListSelectionListener(selectionListner);
        gridHeader.getTable().initComp();         

        gridDetail.getTable().addColumn(new KSGTableColumn("rowNum","순번",50));
		gridDetail.getTable().addColumn(new KSGTableColumn("code_name","코드 상세명"));
		gridDetail.getTable().addColumn(new KSGTableColumn("code_field1","필드값1"));
        gridDetail.getTable().addColumn(new KSGTableColumn("code_field2","필드값2"));        
        gridDetail.getTable().addColumn(new KSGTableColumn("code_field3","필드값3"));        
        gridDetail.getTable().addColumn(new KSGTableColumn("event_date","등록일"));        

        gridDetail.getTable().initComp();
        gridDetail.getTable().addMouseListener(new TableSelectListner());

        KSGPanel pnCodeHeaderMain = new KSGPanel();
        KSGPanel pnCodeHeaderInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));
        pnCodeHeaderMain.setPreferredSize(new Dimension(300,0));


        butInsert.setActionCommand("insert");
        butInsert.addActionListener(this);

        butDelete.setActionCommand("delete");
        butDelete.addActionListener(this);

        pnCodeHeaderInfo.add(butInsert);
        pnCodeHeaderInfo.add(butDelete);

        pnCodeHeaderMain.add(pnCodeHeaderInfo,BorderLayout.NORTH);
        pnCodeHeaderMain.add(gridHeader);

        KSGPanel pnCodeDetailMain = new KSGPanel();
        KSGPanel pnCodeDetailInfo = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));

        
        butInsertDetail.setActionCommand("insertDetail");
        butInsertDetail.addActionListener(this);
        butDeleteDetail.setActionCommand("deleteDetail");
        butDeleteDetail.addActionListener(this);
        pnCodeDetailInfo.add(butInsertDetail);
        pnCodeDetailInfo.add(butDeleteDetail);

        pnCodeDetailMain.add(pnCodeDetailInfo,BorderLayout.NORTH);
        pnCodeDetailMain.add(gridDetail);

        KSGPanel pnMain =new KSGPanel(new BorderLayout(5,5));
        pnMain.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        pnMain.add(pnCodeHeaderMain,BorderLayout.WEST);
        pnMain.add(pnCodeDetailMain); 
        return pnMain;     
    }

    private KSGPanel buildSearch()
    {
        KSGPanel pnMain = new KSGPanel();

        KSGPanel pnOption = new KSGPanel(new FlowLayout(FlowLayout.LEFT));

        lblCodeName = new KSGLabel("코드명");
        lblCodeName.setPreferredSize(new Dimension(80,25));
        pnOption.add(lblCodeName);
        pnOption.add(txfInput);

        KSGPanel pnControl = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));
        butSearch.setActionCommand("search");
        butSearch.addActionListener(this);
        pnControl.add(butSearch);
        pnMain.add(pnOption);
        pnMain.add(pnControl,BorderLayout.EAST);
        return pnMain;
    }
    private void fnSearch()
    {
        String code_class_name =txfInput.getText();

        CommandMap param = new CommandMap();            

        
        param.put("code_class_name", code_class_name);

        callApi("selectCode",param);     

    }
    @Override
    public void updateView() {
        
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

		if(success)
		{   
            if("selectCode".equals(result.getService_id()))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    gridHeader.getTable().clearReslult();
                }
                else{
                    
                    gridHeader.loadData(master);
                    gridHeader.getTable().changeSelection(0,0,false,false);
                }    
            }
            else if("selectCodeDetail".equals(result.getService_id()))
            {
                List master = (List) result.get("data");

                if(master.size()==0)
                {
                    gridDetail.getTable().clearReslult();
                }
                else{
                    

                    gridDetail.loadData(master);
                    gridDetail.getTable().changeSelection(0,0,false,false);

                    
                }    
            }
            else if("deleteCode".equals(result.getService_id()))
            {
                fnSearch();
            }
            else if("deleteCodeDetail".equals(result.getService_id()))
            {
                fnDetailSerch();
            }

                    
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }
    }

    private void fnDetailSerch(String id)
    {
        CommandMap param = new CommandMap();

        param.put("code_class_id", id);        

        callApi("selectCodeDetail",param);
    }

    private void fnDetailSerch()
    {
        int row =gridHeader.getTable().getSelectedRow();
        if(row<0)return;
        
        String code_class_id=(String) gridHeader.getTable().getValueAt(row,"code_class_id");

        fnDetailSerch(code_class_id);
        
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
            CodeClassInsertPopup insertPopup = new CodeClassInsertPopup();

            insertPopup.createAndUpdateUI();

            if(insertPopup.STATUS==CommonPopup.OK)
            {
                fnSearch();
            }
        }
        else if("insertDetail".equals(command))
        {
            int row =gridHeader.getTable().getSelectedRow();
            if(row<0)return;
            String code_class_id=(String) gridHeader.getTable().getValueAt(row,"code_class_id");

            CodeInsertPopup insertPopup = new CodeInsertPopup(code_class_id);

            insertPopup.createAndUpdateUI();

            if(insertPopup.STATUS==CommonPopup.OK)
            {
                fnDetailSerch(code_class_id);
            }
        }
        else if("delete".equals(command))
        {
            int row=gridHeader.getTable().getSelectedRow();
            if(row>-1)
            {
                Object value=gridHeader.getTable().getValueAt(row, "code_class_id");

                CommandMap param = new CommandMap();
                
                param.put("code_class_id", value);
                
                callApi("deleteCode", param);
                
            }  
        }
        else if("deleteDetail".equals(command))
        {
            int row=gridDetail.getTable().getSelectedRow();
            if(row>-1)
            {
                Object value=gridDetail.getTable().getValueAt(row, "code_id");

                CommandMap param = new CommandMap();
                
                param.put("code_id", value);
                
                callApi("deleteCodeDetail", param);
                
            }  
        }
    }
    @Override
    public void componentShown(ComponentEvent e) {        

        fnSearch();
    }
    class SelectionListner implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {

			if(!e.getValueIsAdjusting())
			{
                
				int row=gridHeader.getTable().getSelectedRow();
				if(row<0)
					return;

				// String vessel_name=(String) tableH.getValueAt(row, 0);
				// String vessel_mmsi=(String) tableH.getValueAt(row, 1);
				// String vessel_type=(String) tableH.getValueAt(row, 2);
				// String vessel_company=(String) tableH.getValueAt(row, 3);
				// String vessel_use=String.valueOf(tableH.getValueAt(row, 4));
				// String input_date=(String) tableH.getValueAt(row, 5);

				// lblVesselName.setText(vessel_name);
				// lblVesselCompany.setText(vessel_company);
				// lblVesselType.setText(vessel_type);
				// lblVesselMMSI.setText(vessel_mmsi);
				// lblVesselUse.setText(vessel_use);
				// lblInputDate.setText(input_date);
                Object obj = gridHeader.getTable().getValueAt(row, "code_class_id");
                
				// fnSearchDetail(vessel_name);
                fnDetailSerch((String) obj);

			}
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


				CommandMap code=(CommandMap) gridDetail.getTable().getValueAt(row);

				CodeUpdatePopup dialog = new CodeUpdatePopup(code);

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
