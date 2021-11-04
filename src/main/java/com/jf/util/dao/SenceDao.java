package com.jf.util.dao;
import com.jf.util.entity.SenceManager;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;

@Repository
public interface SenceDao extends Mapper<SenceManager>, MySqlMapper<SenceManager> , ExampleMapper<SenceManager> {
}
