package com.ksg.api.mapper;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapperManager {
    private static SqlSession _session = null;

    static {
        try {
            String resource = "config/mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            
            _session = sqlMapper.openSession(true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static SqlSession getSqlSession() {
        return _session;
    }

}
