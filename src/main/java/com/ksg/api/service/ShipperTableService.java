package com.ksg.api.service;

import java.util.List;

import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.mapper.ShipperTableMapper;
import com.ksg.api.model.ShipperTable;

import lombok.extern.slf4j.Slf4j;

/**
 * 광고정보 서비스
 */
@Slf4j
public class ShipperTableService {

    private ShipperTableMapper mapper;

    public ShipperTableService()
    {
        mapper = new ShipperTableMapper();
    }

    /**
     * 
     * @return 광고정보
     */
    public List<ShipperTable> selectAll()
    {
        return mapper.selectAll();
    }

    public ShipperTable selectById(int id)
    {
        return null;
    }
    /**
     * 광고정보 추가
     * @param param 광고정보
     * @return 입력수
     * @throws Exception
     */
    public int insert(ShipperTable param) throws Exception
    {
        ShipperTable selectOne= mapper.selectByKey(param.getTable_id());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getTable_id()+")존재하는 선사명입니다.");

        int result = mapper.insertShipperTable(param);

        return result;
    }

    /**
     * 광고정보 삭제
     * @param id 광고id
     * @return 삭제 수
     * @throws Exception
     */
    public int delete(int id) throws Exception {
        ShipperTable selectOne= mapper.selectById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("해당 선사가 없습니다.");  
        }
        int result=mapper.deleteShipperTable(id);

        return result;
    }
    /**
     * 광고정보 수정
     * @param param 광고정보
     * @return 수정 수
     * @throws Exception
     */
    public int update(ShipperTable param) throws Exception
    {
        ShipperTable selectOne= mapper.selectById(param.getId());
        if(selectOne==null)
            throw new ResourceNotFoundException("("+param.getTable_id()+")선사명이 존재하지 않습니다.");

        int result = mapper.updateShipperTable(param);

        return result;
    }
    /**
     * 광고정보 상세 데이터 수정
     * @param param
     * @return
     * @throws Exception
     */
    public int updateData(ShipperTable param) throws Exception
    {
        ShipperTable selectOne= mapper.selectById(param.getId());
        if(selectOne==null)
            throw new ResourceNotFoundException("("+param.getTable_id()+")선사명이 존재하지 않습니다.");

        int result = mapper.updateShipperTableData(param);

        return result;
    }

    public List<ShipperTable> selectByCondtion(ShipperTable param) {
        log.info("param:{}",param);
        List result = mapper.selectByCondition(param);

        return result;
    }
    
    
}
