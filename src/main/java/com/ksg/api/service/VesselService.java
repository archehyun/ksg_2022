package com.ksg.api.service;

import java.util.List;

import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.mapper.VesselMapper;
import com.ksg.api.model.Vessel;

public class VesselService {

    private VesselMapper mapper;

    public VesselService()
    {
        mapper = new VesselMapper();
    
    }

    public Vessel selectById(int id)
    {
        return mapper.selectById(id);
    }

    public List<Vessel> selectAll()
    {
        return mapper.selectAll();
    }

    public List<Vessel> selectByCondtion(Vessel param)
    {
        List result = mapper.selectByCondition(param);

        return result;
    }

    public int delete(int id) throws Exception
    {
        Vessel selectOne= mapper.selectById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("해당 선박이 없습니다.");  
        }
        int result=mapper.deleteVessel(id);

        return result;
    }

    public int insert(Vessel param) throws Exception
    {
        Vessel selectOne= mapper.selectByKey(param.getVessel_name());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getVessel_name()+")존재하는 선박명입니다.");

        int result = mapper.insertVessel(param);

        return result;
    }

    public int update(Vessel param) throws Exception
    {
        Vessel selectOne= mapper.selectById(param.getId());
        if(selectOne==null)
            throw new ResourceNotFoundException("("+param.getId()+")선박명이 존재하지 않습니다.");

        int result = mapper.updateVessel(param);

        return result;
    }
    
}
