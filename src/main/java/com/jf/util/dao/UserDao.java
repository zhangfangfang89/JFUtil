package com.jf.util.dao;

import com.jf.util.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *      业务描述：
 * </pre>
 *
 * @author mac
 * @since 2021/7/24 18:12
 */
@Repository
public interface UserDao {
    int insertUser(User user);

    User selectUser(String mobile);

}
