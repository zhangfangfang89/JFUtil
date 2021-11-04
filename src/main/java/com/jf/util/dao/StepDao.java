package com.jf.util.dao;


import com.jf.util.entity.ApiManager;
import com.jf.util.entity.StepManager;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface StepDao extends Mapper<StepManager>, MySqlMapper<StepManager> , ExampleMapper<StepManager> {

}
