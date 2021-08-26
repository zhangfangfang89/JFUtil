package com.jf.util.dao;



import com.jf.util.entity.Configs;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface ConfigsDao extends Mapper<Configs>, MySqlMapper<Configs> , ExampleMapper<Configs> {

}
