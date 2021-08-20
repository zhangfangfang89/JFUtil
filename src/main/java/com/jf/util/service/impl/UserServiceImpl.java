package com.jf.util.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.UserDao;
import com.jf.util.entity.Token;
import com.jf.util.entity.User;
import com.jf.util.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.ArrayList;

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
    private UserDao userDao;
//    private ResultVo resultVo;
    private User user;

    @Transient
    public void register(User user) {
        User queryUser = userDao.selectOne(user);
//        if (queryUser!=null) {
//            int i = userDao.insertUser(user);
//            System.out.println(i);
//            resultVo.setCode("10000");
//            resultVo.setData(user);
//            resultVo.setMessge("注册成功");
//        }else {
//            resultVo.setCode("20000");
//            resultVo.setData(null);
//            resultVo.setMessge("该手机已经注册了");
//        }

    }


    @Override
    public ResultVo login(User user) {
        System.out.println(user.getUsername());
        User u = userDao.selectOne(user);

        ResultVo resultVo = new ResultVo();
        Token token = new Token();
        token.setToken(u.getUsername());

        resultVo.setCode(20000);
        resultVo.setData(token);
        resultVo.setMessage("登录成功");
        return resultVo;
    }
    @Override
    public ResultVo info(String token) {
        JSONObject jsonObject =new JSONObject();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("admin");
        jsonObject.put("roles",arrayList);
        jsonObject.put("introduction","我是测试");
        jsonObject.put("avatar","");
        jsonObject.put("name","");
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(20000);
        resultVo.setMessage("登录成功2");
        resultVo.setData(jsonObject);
        return resultVo;
    }

}
