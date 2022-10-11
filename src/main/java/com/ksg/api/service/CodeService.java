package com.ksg.api.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.mapper.CodeMapper;
import com.ksg.api.model.Code;
import com.ksg.api.model.CodeClass;
import com.ksg.api.model.MyEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeService {

    CodeMapper mapper;

    public CodeService()
    {
        mapper = new CodeMapper();
    }
    public List<MyEnum> selectEnumById(String id)
    {
        List<Code> result = mapper.selectDetailByCondition(Code.builder().code_class_id(id).build());

        List<MyEnum> enumList=result.stream().map(o-> MyEnum.builder()
                                                .name(o.getCode_name())
                                                .field(o.getCode_field1()).build())
                                    .collect(Collectors.toList());
        return enumList;
    }

    public List<CodeClass> selectByCondition(CodeClass param)
    {
        List result = mapper.selectByCondition(param);

        return result;
    }

    public List<Code> selectDetailByCondition(Code param)
    {
        List result = mapper.selectDetailByCondition(param);

        return result;
    }
    

    public int insert(CodeClass param) throws Exception
    {
        CodeClass selectOne= mapper.selectByKey(param.getCode_class_name());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getCode_class_name()+")존재하는 선사명입니다.");

        int result = mapper.insertCode(param);

        return result;
    }
    public int delete(String id) throws Exception
    {
        log.info("param:{}",id);
        CodeClass selectOne= mapper.selectById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("("+id+")해당 코드가 없습니다.");  
        }
        int result=mapper.deleteCode(id);
        return result;
    }

    public int insertDetail(Code param) throws Exception{
        Code selectOne= mapper.selectDetailByKey(param.getCode_class_id(), param.getCode_name());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getCode_name()+")존재하는 선사명입니다.");

        int result = mapper.insertCodeDetail(param);
        return result;
    }

    public int updateDetail(Code param) throws Exception
    {
        // Code selectOne= mapper.selectDetailByKey(param.getCode_class_id(), param.getCode_id());
        // if(selectOne==null)
        //     throw new ResourceNotFoundException("("+param.getCompany_name()+")선사명이 존재하지 않습니다.");

        int result = mapper.updateDetail(param);

        return result;
    }

    public int deleteDetail(int id) throws Exception{
        log.info("param:{}",id);
        Code selectOne= mapper.selectDetailById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("("+id+")해당 코드가 없습니다.");  
        }
        int result=mapper.deleteCodeDetail(id);
        return result;
    }

    
    
}
