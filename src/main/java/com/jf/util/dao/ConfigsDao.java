package com.jf.util.dao;


import com.jf.util.entity.Configs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigsDao {
    Integer insertConfig(Configs configs);
    Configs queryConfig(@Param("keys") String keys);
}
