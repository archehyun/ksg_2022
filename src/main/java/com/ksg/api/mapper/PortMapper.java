package com.ksg.api.mapper;

import java.util.List;

import com.ksg.api.model.Port;

public class PortMapper extends AbstractMapper{

    public PortMapper()
    {
        this.namespace="portMapper";
    }

    public List<Port> selectAll()
    {
        return selectList("selectAll");
    }
    public Port selectById(int id)
    {
        return (Port) selectOne("selectById", id);
    }

    public Port selectByKey(String port_name) {
        return (Port) selectOne("selectByKey", port_name);
    }

    public List<Port> selectByCondition(Port param)
    {
        return selectList("selectByCondition", param);
    }
    public int insertPort(Port param) throws Exception
    {
        return insert("insertPort", param);
    }

    public int updatePort(Port param)
    {
        return update("updatePort", param);
    }
    public int deletePort(int id)
    {
        return delete("deletePort", id);
    }

    public List<Port> selectByPortNames(List<String> portList) {
        return selectList("selectByPortNames", portList);
    }


    
}
