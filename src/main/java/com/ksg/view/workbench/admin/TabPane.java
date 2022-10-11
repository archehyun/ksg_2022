package com.ksg.view.workbench.admin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GradientPaint;
import com.ksg.api.model.Menu;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.tabedpane.KSGTabedPane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Component;

import java.awt.Graphics;
/**
 * 
 */
public class TabPane extends KSGPanel implements ChangeListener{



    private KSGTabedPane painMain = new KSGTabedPane();

    private JTree tree;

    private HashMap<String, Object> viewList = new HashMap<String, Object>();

    private List<Menu> menuList;

    ApplicationContext ctx;

    public TabPane() {

        super(new BorderLayout(10,5));
		//this.setBackground(Color.lightGray);
		this.setBorder(BorderFactory.createEmptyBorder(10,15,20,15));
        ctx = new AnnotationConfigApplicationContext(ViewConfigClass.class);		

        painMain.addChangeListener(this);

        this.add(painMain);        
    }

    private String getMenuNameById(int id)
    {
        for(Menu menu:menuList)
        {
            if(menu.getMenu_id()==id)
            return menu.getMenu_name();
        }
        return null;
    }

	private String getMenuUrlByName(String menu_name)
    {
        for(Menu menu:menuList)
        {
            if(menu.getMenu_name().equals(menu_name))
            return menu.getMenu_url();
        }
        return null;
    }

    public void setMenu(List<Menu> menuList)
    {
       // painMain.removeAll();

        this.menuList =menuList;

        // List<String>menuNameList=menuList.stream()
        //                                 .map(o -> o.getMenu_name())
        //                                 .distinct()
        //                                 .collect(Collectors.toList());
        // updateTree(menuName, menuNameList);
    }

	public boolean hasTab(String name)
	{
		return viewList.containsKey(name);
	}

	public void addTab(String name)
	{		
		String menu_url = getMenuUrlByName(name);

		if(menu_url == null)
		return;

		painMain.addTab(name, ctx.getBean(menu_url, KSGPanel.class));

		painMain.setSelectedIndex(painMain.getTabCount()-1);
	}

	public void selectTab(String tabName)
	{
		int index = painMain.indexOfTab(tabName);
		painMain.setSelectedIndex(index);
	}

    
	class IconCellRenderer 
	extends    JLabel 
	implements TreeCellRenderer
	{
		protected Color m_bkNonSelectionColor;
		protected Color m_bkSelectionColor;
		protected Color m_borderSelectionColor;
		protected boolean m_selected;
		protected Color m_textNonSelectionColor;

		protected Color m_textSelectionColor;

		public IconCellRenderer()
		{
			super();
			m_textSelectionColor = UIManager.getColor(
					"Tree.selectionForeground");
			m_textNonSelectionColor = UIManager.getColor(
					"Tree.textForeground");
			m_bkSelectionColor = UIManager.getColor(
					"Tree.selectionBackground");
			m_bkNonSelectionColor = UIManager.getColor(
					"Tree.textBackground");
			m_borderSelectionColor = UIManager.getColor(
					"Tree.selectionBorderColor");
			setOpaque(false);
		}

		public Component getTreeCellRendererComponent(JTree tree, 
				Object value, boolean sel, boolean expanded, boolean leaf, 
				int row, boolean hasFocus) 
		{
			DefaultMutableTreeNode node = 
					(DefaultMutableTreeNode)value;
			Object obj = node.getUserObject();
			setText(obj.toString());

			if (obj instanceof Boolean)
				setText("Retrieving data...");

			if (obj instanceof IconData)
			{
				IconData idata = (IconData)obj;
				if (expanded)
					setIcon(idata.getExpandedIcon());
				else
					setIcon(idata.getIcon());
			}
			else
				setIcon(null);

			setFont(tree.getFont());
			setForeground(sel ? m_textSelectionColor : 
				m_textNonSelectionColor);
			setBackground(sel ? m_bkSelectionColor : 
				m_bkNonSelectionColor);
			m_selected = sel;
			return this;
		}

		public void paintComponent(Graphics g) 
		{
			Color bColor = getBackground();
			Icon icon = getIcon();

			g.setColor(bColor);
			int offset = 0;
			if(icon != null && getText() != null) 
				offset = (icon.getIconWidth() + getIconTextGap());
			g.fillRect(offset, 0, getWidth() - 1 - offset,
					getHeight() - 1);

			if (m_selected) 
			{
				g.setColor(m_borderSelectionColor);
				g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
			}
			super.paintComponent(g);
		}
	}

    @Override
    public void stateChanged(ChangeEvent e) {
        //String name = tabbedPane.getSelectedComponent();
        
    }

	@Override
    protected void paintComponent(Graphics graphics)
    {    
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0,0,Color.decode("#eef2f3"),0, getHeight(),Color.decode("#8e9eab"));
        g2.setPaint(g);
        g2.fillRect(0, 0, getWidth(), getHeight());
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(),15,15);
        super.paintChildren(graphics);

    }

	

    
}





class IconData {
	protected Object m_data;
	protected Icon   m_expandedIcon;
	protected Icon   m_icon;

	public IconData(Icon icon, Icon expandedIcon, Object data)
	{
		m_icon = icon;
		m_expandedIcon = expandedIcon;
		m_data = data;
	}

	public IconData(Icon icon, Object data)
	{
		m_icon = icon;
		m_expandedIcon = null;
		m_data = data;
	}

	public Icon getExpandedIcon() 
	{ 
		return m_expandedIcon!=null ? m_expandedIcon : m_icon;
	}

	public Icon getIcon() 
	{ 
		return m_icon;
	}

	public Object getObject() 
	{ 
		return m_data;
	}

	public String toString() 
	{ 
		return m_data.toString();
	}
}



