package com.ksg.view.workbench.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ksg.api.controll.MenuController;
import com.ksg.api.model.CommandMap;
import com.ksg.view.common.panel.KSGPanel;
import com.ksg.view.common.template.MgtPn;

@Component("MenuMgtPn")
public class MenuMgtPn extends MgtPn{

    private JTree tree;
    private JScrollPane comp;

    public MenuMgtPn() {
        this("메뉴관리");
        this.addComponentListener(this);
        this.setController(new MenuController());
        this.add(buildCenter());
    }
  
    private java.awt.Component buildCenter() {

        KSGPanel pnMain = new KSGPanel();


        KSGPanel pnInfo = new KSGPanel();

        pnInfo.add(new JLabel("메뉴id"));

        KSGPanel pnTree = new KSGPanel();

        KSGPanel pnTreeTitle = new KSGPanel(new FlowLayout(FlowLayout.LEFT));

        pnTreeTitle.add(new JLabel("메뉴 목록"));
        pnTreeTitle.add(new JButton("추가"));
        pnTreeTitle.add(new JButton("삭제"));

        

        tree = new JTree();

        pnMain.add(pnInfo);

        comp = new JScrollPane(tree);

        comp.setPreferredSize(new Dimension(250,200));

        pnTree.add(pnTreeTitle,BorderLayout.NORTH);
        
        pnTree.add(comp);

        pnMain.add(pnTree,BorderLayout.WEST);

        return pnMain;
    }

    public MenuMgtPn(String name) {
        super(name);
    }

    @Override
    public void updateView() {
        CommandMap result= this.getModel();

        boolean success = (boolean) result.get("success");

		if(success)
		{ 
            if(result.getService_id().equals("selectMenu"))
            {
                List<CommandMap> master = (List) result.get("data");

                if(master.size()==0)
                {
                   // grid.getTable().clearReslult();
                }
                else{
                    List<CommandMap> topMenuList=master.stream().filter(o -> Integer.parseInt(String.valueOf(o.get("menu_parent_id")))==0)
                                                    //.map(o-> (Integer)o.get("menu_id"))
                                                    .collect(Collectors.toList());
                  var menuList=  master.stream()
                    .filter(o ->o.get("menu_parent_id")!=null)
                    .collect(Collectors.groupingBy(o->o.get("menu_parent_id")));

                
                    
                    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
                    for(CommandMap item:topMenuList)
                    {
                        int menu_id = (int) item.get("menu_id");
                        String menu_name = (String) item.get("menu_name");
                        List<CommandMap> subMenuList = menuList.get(menu_id);
                        DefaultMutableTreeNode menu = new DefaultMutableTreeNode(menu_name);
                        for(CommandMap subItem:subMenuList)
                        {
                            String sub_menu_name = (String) subItem.get("menu_name"); 
                            DefaultMutableTreeNode sub_menu = new DefaultMutableTreeNode(sub_menu_name);
                            menu .add(sub_menu);
                        }
                        root.add(menu);

                    }
                    DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
                    tree.setModel(defaultTreeModel);
                    //topMenuList.stream().forEach(o -> System.out.println(o.get("menu_name")));
               // System.out.println(menuList)  ;

                   // grid.loadData(master);
                }            
            }
            // else if(result.getService_id().equals("exportCompany"))
            // {
            //     String data = (String) result.get("data");
            //     JOptionPane.showMessageDialog(this,"("+ data+ ") 엑셀 파일을 생성했습니다.");
            // }
            // else{
            //     fnSearch();
            // }
            
        }
        else{  
            String error = (String) result.get("error");
            JOptionPane.showMessageDialog(this, error);
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
      fnSearch();
        
    }

    private void fnSearch() {

        CommandMap param = new CommandMap();
        
       // param.put("code_class_name", code_class_name);

        callApi("selectMenu",param);     
    }
    
}
