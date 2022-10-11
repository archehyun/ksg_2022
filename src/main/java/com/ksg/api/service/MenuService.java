package com.ksg.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ksg.api.mapper.MenuMapper;
import com.ksg.api.model.Menu;
import com.ksg.api.model.Menu.MenuType;

public class MenuService {
    MenuMapper mapper;

    public MenuService()
    {
        mapper = new MenuMapper();
    }

    public List<Menu> selectAll()
    {
        List<Menu> li=mapper.selectAll();

        // Comparator<Menu> compareByName = Comparator
        // .comparing(Menu::getMenu_parent_id)
        // .thenComparing(Employee::getLastName);

        Map<Integer, Menu> parentIdMap = li.stream()
                                            .filter(o ->o.getMenu_parent_id()==0)
                                            .collect(Collectors.toMap(Menu::getMenu_id, Function.identity()));

        ArrayList<Menu> returnMenu = new ArrayList<Menu>(); 

        for(Integer item:parentIdMap.keySet())
        {
            Menu parentMenu = parentIdMap.get(item);
            parentMenu.setType(MenuType.TITLE);

            List<Menu> subList = li.stream()
                                    .filter(o->o.getMenu_parent_id()==parentMenu.getMenu_id())
                                    .collect(Collectors.toList());

            subList.forEach(submenu -> submenu.setType(MenuType.MENU));

            returnMenu.add(parentMenu);
            returnMenu.addAll(subList);

        }

        


        return returnMenu;



    }
}
