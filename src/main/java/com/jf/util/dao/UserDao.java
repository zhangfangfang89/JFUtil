package com.jf.util.dao;

import com.jf.util.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <pre>
 *      业务描述：
 * </pre>
 *
 * @author mac
 * @since 2021/7/24 18:12
 */
@Repository
public interface UserDao extends Mapper<User>, MySqlMapper<User> , ExampleMapper<User> {


}
