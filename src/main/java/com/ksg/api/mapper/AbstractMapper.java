package com.ksg.api.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public abstract class AbstractMapper {

    protected String namespace;
	
	protected SqlSession session;
	
	public AbstractMapper() {
		session = SqlMapperManager.getSqlSession();
	}
    public <T> List<T> selectList(String sql)
    {
        return session.selectList(namespace+"."+sql);
    }
    public <T> List<T> selectList(String sql, Object param)
    {
        return session.selectList(namespace+"."+sql, param);
    }
    public <T> Object selectOne(String sql, Object param) {
        return session.selectOne(namespace+"."+sql, param);
    }

    public int insert(String sql, Object param) throws Exception{
        return session.insert(namespace+"."+sql, param);
    }
    public int update(String sql, Object param) {
        return session.update(namespace+"."+sql, param);
    }
    public int delete(String sql, Object param) {
        return session.delete(namespace+"."+sql, param);
    }
    
    
    
}
