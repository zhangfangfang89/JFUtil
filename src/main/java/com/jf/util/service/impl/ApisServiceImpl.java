package com.jf.util.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.HttpRequestBase;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ApisDao;
import com.jf.util.entity.Apis;
import com.jf.util.service.ApisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ApisServiceImpl implements ApisService {

    @Resource
    ApisDao apisDao;

    public ResultVo insert(Apis apis) {
        ResultVo resultVo = new ResultVo();
        if (apis.getApiName().equals("")) {
//            Apis data = apisDao.queryAll();
            resultVo.setCode(20000);
            resultVo.setMessage("查询成功");
//            resultVo.setData(data);
        }
        return resultVo;
    }


    public ResultVo toRun(Apis apis) throws URISyntaxException {

        ResultVo resultVo = new ResultVo();
        System.out.println(apisDao.existsWithPrimaryKey(apis));
        if (!apisDao.existsWithPrimaryKey(apis)) {
            if (!apis.getDomain().contains(".com")){
                String url = "http://"+apis.getDomain()+":18081"+apis.getApi();
                Map<String,String> params =new HashMap<>();
                Map<String,String> hearderM =new HashMap<>();
                if (!apis.getParams().equals("")){
                    JSONObject paramsJSON = (JSONObject) JSONObject.parse(apis.getParams());
                    params = JSONObject.toJavaObject(paramsJSON, Map.class);
                }

                String result = HttpRequestBase.doGet(url,params,hearderM);
                if(result.equals("true")){
                    resultVo.setCode(20000);
                    resultVo.setMessage("查询成功");
                    resultVo.setData(true);
                }

            }
        } else {
            resultVo.setCode(50000);
            resultVo.setMessage("已经存在这个接口了");
            resultVo.setData("");
        }
        return resultVo;
    }
}
