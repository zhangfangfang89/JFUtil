package com.jf.util.dao;


import com.jf.util.entity.Apis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface ApisDao extends Mapper<Apis>, MySqlMapper<Apis> {

}
