package com.ksg.api.service;

import java.util.List;

import com.ksg.api.exception.AlreadyExistException;
import com.ksg.api.exception.ResourceNotFoundException;
import com.ksg.api.mapper.CompanyMapper;
import com.ksg.api.model.Company;

/**
 * 
 */
public class CompanyService {

    CompanyMapper mapper;

    public CompanyService()
    {
        mapper = new CompanyMapper();
    }

    /**
     * 
     * @param id
     * @return
     */
    public Company selectById(int id)
    {
        return mapper.selectById(id);
    }

    public Company selectByKey(String company_name)
    {
        return mapper.selectByKey(company_name);
    }

    public List<Company> selectAll()
    {
        return mapper.selectAll();
    }

    public List<Company> selectByCondtion(Company param)
    {
        List result = mapper.selectByCondition(param);

        return result;
    }

    public int delete(int id) throws Exception
    {
        Company selectOne= mapper.selectById(id);
        if(selectOne==null)
        {
            throw new ResourceNotFoundException("해당 선사가 없습니다.");  
        }
        int result=mapper.deleteCompany(id);

        return result;
    }

    public int insert(Company param) throws Exception
    {
        Company selectOne= mapper.selectByKey(param.getCompany_name());
        if(selectOne!=null)
            throw new AlreadyExistException("("+param.getCompany_name()+")존재하는 선사명입니다.");

        int result = mapper.insertCompany(param);

        return result;
    }

    public int update(Company param) throws Exception
    {
        Company selectOne= mapper.selectById(param.getId());
        if(selectOne==null)
            throw new ResourceNotFoundException("("+param.getCompany_name()+")선사명이 존재하지 않습니다.");

        int result = mapper.updateCompany(param);

        return result;
    }
}
