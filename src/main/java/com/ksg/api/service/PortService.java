package com.ksg.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.mapper.PortMapper;
import com.ksg.api.model.CommandMap;
import com.ksg.api.model.Port;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PortService {
    
    private PortMapper mapper;

    public PortService()
    {
        mapper = new PortMapper();
    
    }
    public Port selectById(int id)
    {
        return mapper.selectById(id);
    }

    public List<Port> selectAll()
    {
        return mapper.selectAll();
    }

    public List<Port> selectByCondtion(Port param)
    {
        List result = mapper.selectByCondition(param);

        return result;
    }


    public CommandMap selectMapAll()
    {
        List<Port> result = selectAll();
        log.info("result:{}",result);

		ArrayList<CommandMap> data = new ArrayList<CommandMap>();

		// for(Port item:result)
		// {	
		// 	CommandMap newItem =objectMapper.convertValue(item, CommandMap.class);;
		// 	data.add(newItem); 
		// }

		CommandMap resultMap = new CommandMap();
		resultMap.put("success", true);
		resultMap.put("data", data);
        return resultMap;
    }
    public int delete(int id) throws Exception
    {
        Port selectOne= mapper.selectById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("해당 선사가 없습니다.");  
        }
        int result=mapper.deletePort(id);

        return result;
    }

    public int insert(Port param) throws Exception
    {
        Port selectOne= mapper.selectByKey(param.getPort_name());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getPort_name()+")존재하는 항구명입니다.");

        int result = mapper.insertPort(param);

        return result;
    }

    public int update(Port param) throws Exception
    {
        Port selectOne= mapper.selectById(param.getId());
        if(selectOne==null)
            throw new ResourceNotFoundException("("+param.getPort_name()+")항구명이 존재하지 않습니다.");

        int result = mapper.updatePort(param);

        return result;
    }

}
