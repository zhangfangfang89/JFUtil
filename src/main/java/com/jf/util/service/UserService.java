package com.jf.util.service;

import com.jf.util.entity.User;

/**
 * <pre>
 *      业务描述：
 * </pre>
 *
 * @author mac
 * @since 2021/7/25 14:10
 */
public interface UserService {
    User insertUser(User user);

    User selectUser(String mobile);
}
