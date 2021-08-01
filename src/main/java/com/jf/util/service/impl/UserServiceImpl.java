package com.jf.util.service.impl;

import com.jf.util.dao.UserDao;
import com.jf.util.entity.User;
import com.jf.util.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *      业务描述：
 * </pre>
 *
 * @author mac
 * @since 2021/7/25 14:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userInfoDao;

    @Override
    public User insertUser(User user) {
        int i = userInfoDao.insertUser(user);
        System.out.println(i);
        return user;
    }

    @Override
    public User selectUser(String mobile) {

        return null;
    }


}
