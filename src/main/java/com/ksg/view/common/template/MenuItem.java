package com.ksg.view.common.template;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ksg.api.model.Menu;

import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
public class MenuItem extends JPanel{

    private boolean selected;

    private boolean over;


    public MenuItem(Menu data)
    {
        initComponents();
        setOpaque(false);
        if(data.getType() == Menu.MenuType.MENU)
        {
            ImageIcon icon =new ImageIcon(getClass().getClassLoader().getResource("images/"+data.getMenu_icon()+".png"));        
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(newImg);    
            lblIcon.setIcon(newIcon);
            lblName.setText(data.getMenu_name());
        }
        else if(data.getType() == Menu.MenuType.TITLE)
        {
            lblIcon.setText(data.getMenu_name());
            lblName.setVisible(false); 
            lblIcon.setFont(new Font("sansserif", 1, 12));
        }
        else{
            lblName.setText("empty");
        }
    }
    public void setOver(boolean over)
    {
        this.over = over;
        repaint();
    }

    public void setSelected(boolean selected)
    {
        this.selected =selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        if(selected || over)
        {
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // g2.setColor(new Color(255,255,255,80));
            if(selected)
            {
                g2.setColor(new Color(255,255,255,80));
            }
            else{
                g2.setColor(new Color(255,255,255,20));
            }
            g2.fillRoundRect(0, 0, getWidth()-5, getHeight(),5,5);    
        }
        
        super.paintComponent(graphics);
    }
    private void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        lblName = new JLabel();
        lblIcon  = new JLabel();
        //lblIcon.setPreferredSize(new Dimension(25,20));
        lblName.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
        lblName.setForeground(Color.white);
        lblIcon.setForeground(Color.white);
        this.add(lblIcon);
        this.add(lblName);
    }
    JLabel lblName;
    JLabel lblIcon;
    
}

