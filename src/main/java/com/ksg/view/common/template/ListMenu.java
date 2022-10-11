package com.ksg.view.common.template;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import com.ksg.api.model.Menu;
import com.ksg.view.common.event.EventMenuSelect;


/**
 * list Menu
 */
public class ListMenu<E extends Object> extends JList<E>{

    private final DefaultListModel model;

    int selectedIndex =-1;

    int overIndex =-1;

	private EventMenuSelect event;

	public void addMenuSelectEventListener(EventMenuSelect event)
    {
        this.event = event;
    }

    public ListMenu()
    {
        model = new DefaultListModel<>();

        setModel(model);

        addMouseListener(new MouseAdapter(){

            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e))
                {
                    int index = locationToIndex(e.getPoint());
                    Object o =model.getElementAt(index);
                    if(o instanceof Menu)
                    {
                        Menu menu = (Menu) o;
                        if(menu.getType()==Menu.MenuType.MENU)
                        {
                            selectedIndex =index;
                            if(event !=null)
                            {
                                event.selected(index);

                                event.setId(menu.getMenu_name());
                            }
                            
                        }
                    }
                    else{
                        selectedIndex = index;
                    }
                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            
            @Override
            public void mouseMoved(MouseEvent e)
            {
                int index = locationToIndex(e.getPoint());
                if(index !=overIndex)
                {
                    Object o = model.getElementAt(index);
                    if(o instanceof Menu)
                    {
                        Menu menu = (Menu) o;
                        if(menu.getType()==Menu.MenuType.MENU)
                        {
                            overIndex = index;
                        }
                        else{

                        }
                        repaint();
                    }
                }
                super.mouseMoved(e);
            }
        });
    }
    public void addItem(Menu data)
    {
        model.addElement(data);
    }
    @Override
    public ListCellRenderer<? super E> getCellRenderer()
    {
        return new DefaultListCellRenderer(){
            public Component getListCellRendererComponent(JList<?> list, Object o, int index, boolean bln, boolean bln1)
            { 
                Menu data;
                if(o instanceof Menu)
                {
                    data = (Menu)o;
                }
                else
                {
                    //data = new Menu("", "","","", Menu.MenuType.MENU);
                    data = null;
                }

                MenuItem menu=new MenuItem(data);

                menu.setSelected(selectedIndex==index);

                menu.setOver(overIndex==index);
                return menu;           
                
            }
        };
    }
    
}
