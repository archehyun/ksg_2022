package com.ksg.view.workbench.shcedule;

import javax.swing.JScrollPane;

import org.springframework.stereotype.Component;

import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.table.KSGTable;
import com.ksg.view.common.table.KSGTableColumn;



@Component("ConsoleSchedule")
public class ConsoleSchedule extends KSGPanel{
    KSGTable tableH;

    public ConsoleSchedule()
    {
        this("console");
    }

    public ConsoleSchedule(String name)
    {
        super();
        this.setName(name);
        tableH = new KSGTable();

        tableH.addColumn(new KSGTableColumn("port_name","스케줄ID"));
		tableH.addColumn(new KSGTableColumn("port_nationality","나라"));
		tableH.addColumn(new KSGTableColumn("port_area","지역"));
		tableH.addColumn(new KSGTableColumn("area_code","지역코드"));		
		

        tableH.initComp();

        this.add(new JScrollPane( tableH));
    }
    
}
