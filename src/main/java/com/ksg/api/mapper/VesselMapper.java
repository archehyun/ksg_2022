package com.ksg.api.mapper;

import java.util.List;

import com.ksg.api.model.Vessel;

/**
 * 
 */
public class VesselMapper extends AbstractMapper{

    public VesselMapper()
    {
        this.namespace="vesselMapper";
    }
    public List<Vessel> selectAll()
    {
        return selectList("selectAll", null);
    }
    public Vessel selectById(int id)
    {
        return (Vessel) selectOne("selectById", id);
    }

    public List<Vessel> selectByCondition(Vessel param)
    {
        return selectList("selectByCondition", param);
    }

    public List<Vessel> selectByVesselNames(List<String> vesselNameList)
    {
        return selectList("selectByVesselNames",vesselNameList);
    }
    public int insertVessel(Vessel param) throws Exception
    {
        return insert("insertVessel", param);
    }

    public int updateVessel(Vessel param)
    {
        return update("updateVessel", param);
    }
    public int deleteVessel(int id)
    {
        return update("deleteVessel", id);
    }
    public Vessel selectByKey(String vessel_name) {
        return null;
    }
    
}
