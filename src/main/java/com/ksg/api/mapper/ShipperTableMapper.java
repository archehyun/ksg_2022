package com.ksg.api.mapper;

import java.util.List;

import com.ksg.api.model.ShipperTable;

public class ShipperTableMapper extends AbstractMapper{

    public ShipperTableMapper()
    {
        this.namespace="shipperTableMapper";
    }
    public List<ShipperTable> selectAll()
    {
        return selectList("selectAll");
    }
    public ShipperTable selectById(int id)
    {
        return (ShipperTable) selectOne("selectById", id);
    }
    public ShipperTable selectByKey(String table_id) {
        return (ShipperTable) selectOne("selectByKey", table_id);
    }   

    public List<ShipperTable> selectByCondition(ShipperTable param)
    {
        return selectList("selectByCondition", param);
    }
    public int insertShipperTable(ShipperTable param) throws Exception
    {
        return insert("insertShipperTable", param);
    }
    public int updateShipperTableData(ShipperTable param)
    {
        return update("updateShipperTableData", param);
    }
    public int updateShipperTable(ShipperTable param)
    {
        return update("updateShipperTable", param);
    }
    public int deleteShipperTable(int id)
    {
        return update("deleteShipperTable", id);
    }
    
}
