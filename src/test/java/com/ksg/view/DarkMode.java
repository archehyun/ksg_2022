package com.ksg.view;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;

import com.ksg.view.common.table.DarkTableUI;

public class DarkMode {

    @Test
    public void test01()
    {
        JFrame frm = new JFrame();
        DarkTableUI comp = new DarkTableUI();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("test1");
        model.addColumn("test2");
        model.addColumn("test3");
        String data1[] ={"1","2","3"};
        model.addRow(data1);
        model.addRow(data1);
        model.addRow(data1);
        model.addRow(data1);
        // comp.setModel(model);

        // frm.getContentPane().add(new JScrollPane(comp));
        frm.setSize(new Dimension(400,100));
        frm.setVisible(true);
        frm.setVisible(true);

    }
    
}
