package com.ksg.view.common.gird;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.ksg.view.common.icon.IconButton;
import com.ksg.view.common.panel.KSGPanel;

import java.awt.Color;

/**
 * 
 */
public class GirdFooter extends KSGPanel{

    int level[];

    int total=0;

    int currentPage;

    int totalPage;


    private JLabel lblTotal = new JLabel();

    private JLabel lblCount = new JLabel("0/0");

    public IconButton butFirst = new IconButton("images/double_backward_left.png");

    public IconButton butBackword = new IconButton("images/arrow_direction_left_less.png");

    public IconButton butForword = new IconButton("images/arrow_direction_left_more.png");

    public IconButton butEnd = new IconButton("images/double_forward_right.png");

    public JComboBox cbxLevel = new JComboBox<>();

    public GirdFooter(int level[])
    {
        super();
        this.level =level;
        this.add(buildLeft(),BorderLayout.WEST);
        this.add(buildRight(),BorderLayout.EAST);
        this.setBackground(Color.lightGray);
        lblTotal.setText(String.valueOf(total));
    }
    public void setTotal(int total)
    {
        lblTotal.setText(String.valueOf(total));
    }

    private KSGPanel buildLeft()
    {
        KSGPanel pnMain = new KSGPanel(new FlowLayout(FlowLayout.LEFT));
        pnMain.add(butFirst);
        pnMain.add(butBackword);
        pnMain.add(lblCount);
        pnMain.add(butForword);
        pnMain.add(butEnd);
        pnMain.setBackground(Color.lightGray);
        return  pnMain;
    }
    private KSGPanel buildRight()
    {
        lblTotal.setForeground(Color.red);
        KSGPanel pnMain = new KSGPanel(new FlowLayout(FlowLayout.RIGHT));
        pnMain.add(new JLabel("총 건수"));
        pnMain.add(lblTotal);
        pnMain.add(new JLabel("행 개수"));
        for(int levelItem:level)
        {
            cbxLevel.addItem(levelItem);
        }
        pnMain.setBackground(Color.lightGray);
        pnMain.add(cbxLevel);

        return pnMain;
    }

    public void setPageInfo(int currentPage, int pageCount)
    {
        lblCount.setText(currentPage+"/"+pageCount);
    }
}

