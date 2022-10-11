package com.ksg.view.common.template;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ksg.view.common.icon.IconButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;

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
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.JFrame;
public class Header extends JPanel implements WindowListener{

    Image img;

    JFrame frame;

    public Header()
    {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0,0,0,0));
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.setPreferredSize(new Dimension(1024,45));
        add(lblMenu,BorderLayout.WEST);

        JPanel pnSearchMain =new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnSearch =new JPanel();
        pnSearch.setLayout(new BoxLayout(pnSearch, BoxLayout.X_AXIS));
        pnSearch.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        JTextField t=new JTextField(10);
        t.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        pnSearch.add(t);
        pnSearch.setBorder(BorderFactory.createLineBorder(Color.black));

        pnSearchMain.add(pnSearch);
        pnSearchMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        pnSearch.setBackground(Color.white);
        // add(pnSearchMain);
        
        JLabel lblTitle = new JLabel("출판시스템");
        lblTitle.setForeground(Color.white);
        // add(lblTitle);

        add(createButtonGroup(),BorderLayout.EAST);
        
    }

    private int x,  y;
    private JLabel lblSquares = new JLabel(getIcon("images/squares_icon .png"));
    private JLabel lblMinimize = new JLabel(getIcon("images/minimize_icon.png"));
    private JLabel lblClose = new JLabel(getIcon("images/close.png"));

    public JButton lblMenu = new IconButton("images/lines_list_menu_checklist_navigation_icon.png");

    public void initMoving(JFrame frame)
    {   
        this.frame = frame;
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e)
            {
                x = e.getX()+200;
                y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e)
            {
                frame.setLocation(e.getXOnScreen()-x, e.getYOnScreen()-y);
            }          

        });
    }
        

    private JComponent createButtonGroup()
    {
        JPanel pnMain = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnMain.setBackground(new Color(0,0,0,0));
        pnMain.setOpaque(true);

        lblClose.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e)  
            {
                System.exit(0);
            }
        });

        lblMinimize.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e)  
            {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });

        lblSquares.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e)  
            {
                int state = frame.getExtendedState();

                
                state = (state==JFrame.MAXIMIZED_BOTH)?JFrame.NORMAL:JFrame.MAXIMIZED_BOTH;

                frame.setExtendedState(state);
            }
        });


        pnMain.add(lblMinimize);
        pnMain.add(lblSquares);
        
        pnMain.add(lblClose);
        return pnMain;
    }

    private ImageIcon getIcon(String iconName)
    {
        ImageIcon icon =new ImageIcon(getClass().getClassLoader().getResource(iconName));
        
        img = icon.getImage();
        Image newImg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);
        return newIcon;    

    }

    // @Override
    // protected void paintChildren(Graphics graphics)
    // {    
    //     Graphics2D g2 = (Graphics2D) graphics;
    //     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //     // GradientPaint g = new GradientPaint(0,0,Color.decode("#6190e8"),0, getHeight(),Color.decode("#a7bfe8"));
    //     // GradientPaint g = new GradientPaint(0,0,Color.decode("#000000"),0, getHeight(),Color.decode("#434343"));
    //     // GradientPaint g = new GradientPaint(0,0,Color.decode("#525252"),0, getHeight(),Color.decode("#3d72b4"));
    //     GradientPaint g = new GradientPaint(0,0,Color.decode("#1CB5E0").darker(),0, getHeight(),Color.decode("#1CB5E0"));
        
    //     g2.setPaint(g);
    //     g2.fillRect(x, 0, getWidth(), getHeight());
    //     // g2.fillRoundRect(0, 0, getWidth(), getHeight(),15,15);
    //     super.paintChildren(graphics);

    // }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
}
