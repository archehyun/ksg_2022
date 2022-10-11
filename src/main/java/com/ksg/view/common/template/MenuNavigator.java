package com.ksg.view.common.template;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import com.ksg.api.model.Menu;
import com.ksg.view.common.event.EventMenuSelect;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.Color;
import java.awt.GradientPaint;

/**
 * 메뉴 UI 표시
 */
public class MenuNavigator extends JPanel{

    private EventMenuSelect event;

    private ListMenu list;

    private JPanel pnLogo;

    public void addMenuSelectEventListener(EventMenuSelect event)
    {
        this.event = event;
        list.addMenuSelectEventListener(event);
    }

    public MenuNavigator()
    {
        super();

        initComponents();        

        setOpaque(false);

        
        
    }

    public void setMenus(List<Menu> menus)
    {
        for(Menu item:menus)
        {
            list.addItem(item);
        }
    }
    

    private void initComponents() {
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

        

        list = new ListMenu<>();
        list.setOpaque(false);
        setBackground(Color.white);
        list.setBackground(new Color(0,0,0,0));

        this.setPreferredSize(new Dimension(200,600));
        pnLogo = new JPanel(new BorderLayout());
        pnLogo.setOpaque(false);

        JLabel lblLogo=new JLabel("logo");
        lblLogo.setHorizontalAlignment(JLabel.LEFT);
        lblLogo.setFont(new Font("sansserif", 1, 18));
        lblLogo.setForeground(Color.white);
        pnLogo.add(lblLogo);

        pnLogo.setPreferredSize(new Dimension(100,80));
        pnLogo.setSize(200, 200);
        
        this.add(pnLogo,BorderLayout.NORTH);
        this.add(list);
        
    }
    @Override
    protected void paintChildren(Graphics graphics)
    {    
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0,0,Color.decode("#1CB5E0"),0, getHeight(),Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRect(x, 0, getWidth(), getHeight());
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(),15,15);
        super.paintChildren(graphics);

    }

    private int x,  y;

    public void initMoving(JFrame frame)
    {
        pnLogo.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e)
            {
                x = e.getX();
                y = e.getY();
            }
        });
        pnLogo.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e)
            {
                frame.setLocation(e.getXOnScreen()-x, e.getYOnScreen()-y);
            }
            

        });
    }    
}

