package com.ksg.view.workbench.shippertable;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.ksg.api.controll.ScheduleController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.gird.KSGDataGrid;
import com.ksg.view.common.table.KSGTableColumn;
import com.ksg.view.common.template.MgtPn;
import java.awt.event.ComponentEvent;
import org.springframework.stereotype.Component;

@Component("DataGridSchedulePn")
public class SchedulePn extends MgtPn{


    KSGDataGrid grid;
    private String table_id;

    public SchedulePn()
    {
        this("광고정보관리");
    }

    public SchedulePn(String name) {
        super(name);
        
        this.setController(new ScheduleController());
        int level[]={50,100};

        grid = new KSGDataGrid(level);
        grid.getTable().addColumn(new KSGTableColumn("table_id","테이블ID"));
        grid.getTable().addColumn(new KSGTableColumn("vessel_name","선박명"));
		grid.getTable().addColumn(new KSGTableColumn("inout_type","IN/OUT"));
        grid.getTable().addColumn(new KSGTableColumn("from_port","출발항"));
        grid.getTable().addColumn(new KSGTableColumn("from_date","출발일"));
        grid.getTable().addColumn(new KSGTableColumn("to_port","도착항"));
        grid.getTable().addColumn(new KSGTableColumn("to_date","도착일"));
        
        
        grid.getTable().initComp();
        this.addComponentListener(this);
        add(grid); 
    }
   


    

    @Override
    public void updateView() {
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

		if(success)
		{ 
            if(result.getService_id().equals("selectScheduleByKey"))
            {
                ArrayList master = (ArrayList) result.get("data");

                if(master.size()==0)
                {
                    grid.getTable().clearReslult();
                }
                else{
                    grid.loadData(master);
                }            
            }

            else{

               // fnSearch();
            }
            
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }
        
    }

    private void fnSearch() {

        CommandMap param = new CommandMap();
      
      
        param.put("table_id", table_id);


        callApi("selectScheduleByKey",param);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void componentShown(ComponentEvent e) {
        fnSearch();

    }

    public void setTable_id(String table_id2) {
        this.table_id =table_id2;
    }

    
    
}
