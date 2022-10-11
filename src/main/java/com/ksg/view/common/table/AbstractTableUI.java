package com.ksg.view.common.table;

import javax.swing.table.DefaultTableCellRenderer;

public abstract class AbstractTableUI {

    public abstract DefaultTableCellRenderer getHeaderRenderer();
    
    public abstract DefaultTableCellRenderer getCellRenderer();
    
}
