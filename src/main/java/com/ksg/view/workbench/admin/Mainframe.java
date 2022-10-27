package com.ksg.view.workbench.admin;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.animation.timing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ksg.api.model.Menu;
import com.ksg.api.service.MenuService;
import com.ksg.view.common.event.EventMenuSelect;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.Header;
import com.ksg.view.common.template.MenuNavigator;
import com.ksg.view.util.ViewUtil;

import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class Mainframe extends JFrame implements ActionListener{

	private MigLayout layout;
	private boolean menuShow;
    private JMenuBar menuBar;
	private TabPane pnTabMain = new TabPane();
	MenuNavigator menu = new MenuNavigator();
    public Mainframe()
    {
			//UIManager.put("Button.background", new ColorUIResource(Color.DARK_GRAY));
			//UIManager.put("Button.forground", new ColorUIResource(Color.white));
			
			// this.setBackground(new Color(0,0,0,0));
			try {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
				
				// for (Map.Entry<Object, Object> entry : javax.swing.UIManager.getDefaults().entrySet()) {
				//     Object key = entry.getKey();
				//     Object value = javax.swing.UIManager.get(key);
				//     if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
				//         javax.swing.plaf.FontUIResource fr=(javax.swing.plaf.FontUIResource)value;
				//         javax.swing.plaf.FontUIResource f = new javax.swing.plaf.FontUIResource("SanSerif", fr.getStyle(), 25);
				//         javax.swing.UIManager.put(key, f);
				//     }
				// }
	 
				 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

		this.setUndecorated(true);			

		menu.initMoving(this);

		menu.addMenuSelectEventListener(new EventMenuSelect() {

			@Override
			public void selected(int index) {
				System.out.println("index:"+index);
			}

			@Override
			public void setId(String id) {
				System.out.println("id:"+id);

				if(pnTabMain.hasTab(id))
				{
					pnTabMain.selectTab(id);
				}
				else{
					pnTabMain.addTab(id);
				}
				
			}
			
		});
		TimingTarget target = new TimingTargetAdapter() {

			@Override
			public void timingEvent(float arg0) {
				double widht;
				if(menuShow)
				{
					widht = 50 + (150* (1f- arg0));
				}
				else{
					widht = 50 + (150* arg0);
				}
				layout.setComponentConstraints(menu, "w "+widht+"!");
				body.revalidate();
			}
			public void end()
			{
				menuShow =!menuShow;
			}
			
		};

		header.lblMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!animator.isRunning())
				{
					animator.start();
				}
				
			}});
		
		animator = new Animator( 300, target);
		animator.setResolution(0);
		animator.setAcceleration(0.5f);
		animator.setDeceleration(0.5f);
		
		initMenu();

		header.initMoving(this);

		layout = new MigLayout("fill", "0[]10[]0", "0[fill]0");

		body = new KSGPanel(layout);

		body.add(menu,"w 50!");

		body.add(createMain(),"w 100%");

        this.getContentPane().add(body);        

        this.setSize(1024,768);

        ViewUtil.center(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
	
	private Header header = new Header();

	MenuService menuService = new MenuService();

	private Animator animator;

	private KSGPanel body;

	private void initMenu()
    {
		List<Menu> list = menuService.selectAll();
		menu.setMenus(list);
		pnTabMain.setMenu(list);		
    }

    private JComponent createMain()
    {
		KSGPanel pnCenter = new KSGPanel();

		pnCenter.add(header,BorderLayout.NORTH);
		pnCenter.add(pnTabMain);

        return pnCenter;
    }

    

	@Override
	public void actionPerformed(ActionEvent e) {}
    
}
