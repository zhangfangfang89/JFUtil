package com.jf.util.controller;

import com.jf.util.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <pre>
 *      业务描述：
 * </pre>
 *
 * @author mac
 * @since 2021/7/25 14:59
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class User {
    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public com.jf.util.entity.User register(com.jf.util.entity.User userInfo) {
        return userService.insertUser(userInfo);
    }
}

