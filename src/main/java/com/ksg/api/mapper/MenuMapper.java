package com.ksg.api.mapper;

import java.util.List;

import com.ksg.api.model.Menu;

public class MenuMapper extends AbstractMapper{

    public MenuMapper()
    {
        this.namespace="menuMapper";
    }

    public List<Menu> selectAll()
    {
        return selectList("selectAll");
    }
}
