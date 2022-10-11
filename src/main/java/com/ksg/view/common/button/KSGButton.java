package com.ksg.view.common.button;

import java.awt.Dimension;
import java.util.HashMap;
import java.awt.*;
import javax.swing.AbstractButton;

import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.border.EmptyBorder;

import javax.swing.plaf.basic.BasicButtonUI;

import com.ksg.view.common.template.KSGStyle;
import com.ksg.view.util.KSGViewProperties;
import com.ksg.view.util.ViewUtil;

/**
 * 
 */
public class KSGButton extends JButton implements KSGStyle{

    KSGViewProperties propeties = KSGViewProperties.getInstance();

    Color backgroundColor;

    Color background;

    Color forground;

    Color[] gradientBackground;
    //royalblue

    // Color royalblue = new Color(0x4169e1);

    
    //royalblue +25%
    // Color select = new Color(0x809aeb);
    Color select = Color.white;

    public KSGButton(String message)
    {
        super(message);

        setStyle(null);
        setBackground(background);
        setForeground(forground);
        
        // customize the button with your own look
        setUI(new StyledButtonUI());

    }
    
    

    class StyledButtonUI extends BasicButtonUI {

        @Override
        public void installUI (JComponent c) {
            super.installUI(c);
            AbstractButton button = (AbstractButton) c;
            button.setOpaque(false);
            button.setBorder(new EmptyBorder(5, 15, 5, 15));
        }
    
        @Override
        public void paint (Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;

            KSGButton.this.setBackground(b.getModel().isPressed()?select:   background);

            background = b.getModel().isPressed()?backgroundColor.brighter():backgroundColor;

            
            paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
            super.paint(g, c);
        }
    
        private void paintBackground (Graphics g, JComponent c, int yOffset) {
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AbstractButton b = (AbstractButton) c;
            GradientPaint gg = new GradientPaint(0,0,gradientBackground[0],0, getHeight(),gradientBackground[1]);

           
            
            // if(b.getModel().isRollover())
            // {
            
            //     g.setColor(backgroundColor.brighter());
            // }
            // else{
            //     g.setColor(backgroundColor.darker());
            // }
            g2.setPaint(gg);
            g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 5);
           
            // g.setColor(background);
            g.fillRoundRect(0, yOffset, size.width-2, size.height + yOffset - 3, 10, 5);
        }
    }


    @Override
    public void updateStyle() {
       
        
    }
    public void setStyle(HashMap<String, Object> param)
	{
        backgroundColor = Color.decode(propeties.getProperty("button.background"));
        gradientBackground =ViewUtil.getGradientColor(propeties.getProperty("button.background.gradient"));
        forground = Color.white;

        
    }
}
