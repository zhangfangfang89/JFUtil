package com.jf.util.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ConfigsDao;
import com.jf.util.entity.Configs;
import com.jf.util.pplogin.PassportLogin;
import com.jf.util.service.ConfigsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigsServiceImpl implements ConfigsService {
    @Resource
    private  ConfigsDao  configsDao;
    @Override
    public ResultVo addKeys(Configs configs) {
        JSONObject jsonObject = new JSONObject();
        ResultVo resultVo = new ResultVo();
        System.out.println(configs.getKeys());
        Configs ipConfig = configsDao.selectOne(configs);
        if (ipConfig==null){
            if (!("".equals(configs.getMobile())&&"".equals(configs.getPassword()))){
                try {
                    String cookie = PassportLogin.login(configs.getMobile(), configs.getPassword());
                    System.out.println(cookie);
                    configs.setText(cookie);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Integer ipId = configsDao.insert(configs);
            jsonObject.put("id", ipId);
            resultVo.setCode(20000);
            resultVo.setMessage("保存成功");
            resultVo.setData(jsonObject);

        }else {
            if (!("".equals(configs.getMobile())&&"".equals(configs.getPassword()))){
                try {
                    String cookie = PassportLogin.login(configs.getMobile(), configs.getPassword());
                    System.out.println(cookie);
                    configs.setText(cookie);
                    configs.setMobile(configs.getMobile());
                    configs.setPassword(configs.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            configsDao.updateByPrimaryKey(configs);
            Configs ipConfigU = configsDao.selectOne(configs);
            jsonObject.put("text",ipConfigU.getText());
            resultVo.setCode(20000);
            resultVo.setMessage("已经存在了,进行更新");
            resultVo.setData(jsonObject);
        }

        return resultVo;
    }

    @Override
    public ResultVo queryKeys(String keys) {
        JSONObject jsonObject = new JSONObject();
        ResultVo resultVo = new ResultVo();
        System.out.println(keys);
        Configs ipConfig = configsDao.selectOneByExample(keys);
        if (ipConfig!=null){
            jsonObject.put("text",ipConfig.getText());
            jsonObject.put("mobile",ipConfig.getMobile());
            jsonObject.put("password",ipConfig.getPassword());
            resultVo.setCode(20000);
            resultVo.setMessage("查询成功");
            resultVo.setData(jsonObject);
        }else {
            resultVo.setCode(20000);
            resultVo.setMessage("");
            resultVo.setData(null);
        }

        return resultVo;
    }
}
