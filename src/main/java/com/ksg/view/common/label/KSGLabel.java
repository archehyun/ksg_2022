package com.ksg.view.common.label;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class KSGLabel extends JLabel{
    public KSGLabel(String label)
    {
        super(label);
        setFont(new Font("sansserif", 1, 12));
        this.setForeground(Color.gray);
    }
    
}
