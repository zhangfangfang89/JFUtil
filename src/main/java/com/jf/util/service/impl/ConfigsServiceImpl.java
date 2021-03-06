package com.jf.util.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ConfigsDao;
import com.jf.util.entity.Configs;
import com.jf.util.pplogin.PassportLogin;
import com.jf.util.service.ConfigsService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class ConfigsServiceImpl implements ConfigsService {
    @Resource
    private  ConfigsDao  configsDao;

    @Override
    public ResultVo addKeys(Configs configs) {
        JSONObject jsonObject = new JSONObject();
        ResultVo resultVo = new ResultVo();

        Example example = new Example(Configs.class);
        example.selectProperties("keyName","text","mobile","password");
        example.createCriteria();
        example.and().andEqualTo("keyName",configs.getKeyName());
        Configs ipConfig = configsDao.selectOneByExample(example);
        if (ipConfig==null){
            if (!("".equals(configs.getMobile())&&"".equals(configs.getPassword()))){
                try {
                    configs.setText(configs.getText());
                    configs.setMobile(configs.getMobile());
                    configs.setPassword(configs.getPassword());
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
            Configs record = new Configs();
            record.setText(configs.getText());
            record.setMobile(configs.getMobile());
            record.setPassword(configs.getPassword());
            configsDao.updateByExampleSelective(record,example);

            Configs ipConfigU = configsDao.selectOneByExample(example);
            jsonObject.put("text",ipConfigU.getText());
            jsonObject.put("mobile",ipConfigU.getMobile());
            jsonObject.put("password",ipConfigU.getPassword());
            resultVo.setCode(20000);
            resultVo.setMessage("已经存在了,进行更新");
            resultVo.setData(jsonObject);
        }

        return resultVo;
    }

    @Override
    public ResultVo queryKeys(String keyName) {
        JSONObject jsonObject = new JSONObject();
        ResultVo resultVo = new ResultVo();
        System.out.println(keyName);
        Example example = new Example(Configs.class);
        example.selectProperties("keyName","text","mobile","password");
        example.createCriteria()
                .andEqualTo("keyName",keyName);

        Configs ipConfig = configsDao.selectOneByExample(example);
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
