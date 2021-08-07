package com.jf.util.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ConfigsDao;
import com.jf.util.entity.Configs;
import com.jf.util.service.ConfigsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigsServiceImpl implements ConfigsService {
    @Resource
    private  ConfigsDao  configsDao;
    @Override
    public ResultVo addIP(Configs configs) {
        JSONObject jsonObject = new JSONObject();
        ResultVo resultVo = new ResultVo();
        System.out.println(configs.getKeys());
        Configs ipConfig = configsDao.queryConfig(configs.getKeys());
        if (ipConfig==null){
            Integer ipId = configsDao.insertConfig(configs);
            jsonObject.put("id",ipId);
            resultVo.setCode(20000);
            resultVo.setMessage("保存成功");
            resultVo.setData(jsonObject);
        }else {
            resultVo.setCode(50000);
            resultVo.setMessage("已经存在了");
            resultVo.setData(null);
        }

        return resultVo;
    }

    @Override
    public ResultVo queryIp(Configs configs) {
        return null;
    }
}
