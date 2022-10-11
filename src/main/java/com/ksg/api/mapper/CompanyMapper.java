package com.ksg.api.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ksg.api.model.Company;

public class CompanyMapper extends AbstractMapper{

    public CompanyMapper()
    {
        this.namespace="companyMapper";
    }
    public List<Company> selectAll()
    {
        return selectList("selectAll", null);
    }
    public Company selectById(int id)
    {
        return (Company) selectOne("selectById", id);
    }
    public Company selectByKey(String company_name) {
        return (Company) selectOne("selectByKey", company_name);
    }    

    public List<Company> selectByCondition(Company param)
    {
        return selectList("selectByCondition", param);
    }
    public int insertCompany(Company param)throws Exception
    {
        return insert("insertCompany", param);
    }

    public int updateCompany(Company param)
    {
        return update("updateCompany", param);
    }
    public int deleteCompany(int id)
    {
        return delete("deleteCompany", id);
    }


}
