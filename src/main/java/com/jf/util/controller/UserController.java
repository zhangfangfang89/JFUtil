package com.jf.util.controller;

import com.jf.util.common.ResultVo;
import com.jf.util.entity.User;
import com.jf.util.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
public class UserController extends BaseController{
    @Resource
    private UserService userService;
    private User user;

    @PostMapping("/login")
    public ResultVo login(@RequestBody User user){
        System.out.println(user);
        ResultVo resultVo = userService.login(user);
        return resultVo;
    }
    @GetMapping("/info")
    public ResultVo info(String token){
        ResultVo resultVo = userService.info(token);
        return resultVo;
    }

}

