package com.ksg.view.common.template;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.ksg.view.common.panel.KSGPanel;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.Graphics;


public class LeftTreeUI extends KSGPanel{

    private JTree tree;

    private KSGPanel pnMain;

    private CardLayout cardLayout;
    
    private ArrayList<String> compNameList = new ArrayList<String>();

    public LeftTreeUI(String name)
    {
        super(new BorderLayout(7,5));
		this.setName(name);
		this.add(buildMain());

    }

    protected void createUIandUpdate()
    {
        this.add(createTreeMenu(),BorderLayout.WEST);
    }

    private KSGPanel buildMain()
    {
        cardLayout = new CardLayout();
        pnMain = new KSGPanel(cardLayout);
		pnMain.setBorder(BorderFactory.createLineBorder(Color.lightGray));        

        return pnMain;
    }


    public void addComp(JComponent comp)
    {
        pnMain.add(comp,comp.getName());
        this.compNameList.add(comp.getName());
    }
    

     /**
	 * 
	 * 왼쪽 트리 메뉴 생성
	 * @return
	 */
	private KSGPanel createTreeMenu() {

		
		tree = new JTree();		
		this.updateTree();

		tree.setExpandsSelectedPaths(true);
		tree.addTreeSelectionListener(new TreeSelectionListener(){

			private String _selectedTable;

			public void valueChanged(TreeSelectionEvent e) {

				TreePath path=e.getNewLeadSelectionPath();
				if(path!=null&&path.getLastPathComponent()!=null)
				{
					_selectedTable = path.getLastPathComponent().toString();
					cardLayout.show(pnMain, _selectedTable);

				}
			}
		});

		tree.setPreferredSize(new Dimension(150,100));
		tree.setRowHeight(25);
		
		KSGPanel pnMain = new KSGPanel();
		pnMain.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		pnMain.add(tree);


		return pnMain;
	}

    private void updateTree()
	{

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

        for(String name:compNameList)
        {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
            root.add(node);
        }
	
	
		tree.setModel(new DefaultTreeModel(root));

		TreeCellRenderer renderer = new IconCellRenderer();
		tree.setCellRenderer(renderer);


		tree.setRootVisible(true);
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
