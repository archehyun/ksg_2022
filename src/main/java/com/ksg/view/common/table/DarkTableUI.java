package com.ksg.view.common.table;

import javax.swing.table.DefaultTableCellRenderer;



import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.*;

public class DarkTableUI extends AbstractTableUI{
    public DarkTableUI()
    {
       
    }
    public void fixTable(JScrollPane scroll)
    {
        //scroll.setVerticalScrollBar(new ScrollBarCustom());
    }

    private class TableDarkHeader extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            comp.setBackground(new Color(30,30,30));
            comp.setForeground(new Color(200,200,200));
            comp.setFont(comp.getFont().deriveFont(Font.BOLD,12));
            setHorizontalAlignment(JLabel.CENTER);
            return comp;
        }
    }
    private class TableDarkCell extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);        
            if(table.isCellSelected(row, column))
            {
                if(row %2 ==0)
                {
                    comp.setBackground(new Color(33,103,153));
                }
                else{
                    comp.setBackground(new Color(29,86,127));
                }
                
            }
            else{
                if(row %2 ==0)
                {
                    comp.setBackground(new Color(50,50,50));
                }
                else{
                    comp.setBackground(new Color(30,30,30));
                }

            }
            comp.setForeground(new Color(200,200,200));
            setBorder(new EmptyBorder(0,5,0,5));

            return comp;

        }
    }
    @Override
    public DefaultTableCellRenderer getHeaderRenderer() {
        
        return new TableDarkHeader();
    }
    @Override
    public DefaultTableCellRenderer getCellRenderer() {
       
        return new TableDarkCell();
    }
}

    


