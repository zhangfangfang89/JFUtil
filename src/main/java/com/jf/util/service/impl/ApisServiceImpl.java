package com.jf.util.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.HttpRequestBase;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ApisDao;
import com.jf.util.dao.ConfigsDao;
import com.jf.util.entity.ApiManager;
import com.jf.util.entity.Configs;
import com.jf.util.pplogin.PassportLogin;
import com.jf.util.service.ApisService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;


@Service
public class ApisServiceImpl implements ApisService {
    @Resource
    private ApisDao apisDao;
    @Resource
    private ConfigsDao configsDao;

    public String ipApi(ApiManager apis) {
        String url = "http://"+apis.getDomain()+":18081"+apis.getApi();
        Map<String,String> params =new HashMap<>();
        Map<String,String> hearderM =new HashMap<>();
        String result = "";
        if (!apis.getParams().equals("[]")){
            JSONArray paramsJSONArray = (JSONArray) JSONArray.parse(apis.getParams());
            try {
                for (int i = 0; i < paramsJSONArray.size(); i++) {
                    if (paramsJSONArray.getJSONObject(i).getString("key").equals("customerId")) {
                        params.put("customerId", paramsJSONArray.getJSONObject(i).getString("value"));
                        result = HttpRequestBase.doGet(url, params, hearderM);
                    } else {
                        params.put(paramsJSONArray.getJSONObject(i).getString("key"), paramsJSONArray.getJSONObject(i).getString("value"));
                        result = HttpRequestBase.doGet(url, params, hearderM);

                    }
                    if (paramsJSONArray.getJSONObject(i).getString("key").equals("mobile") || paramsJSONArray.getJSONObject(i).getString("key").equals("password")) {
                        //处理输入是用户账号的批量删除
                    }
                }
            }catch (Exception e){
                return result;
            }
        }
        return result;
    }
    public JSONObject businessApi(ApiManager apis){
        JSONObject jsonObject = null;
        String url = apis.getDomain()+apis.getApi();
        Map<String,String> params =new HashMap<>();
        Map<String,String> headerM =new HashMap<>();
        Example example = new Example(Configs.class);
        example.createCriteria();
        example.selectProperties("keyName","text","mobile","password");
        example.and()
                .andEqualTo("keyName","cookies");
        Configs configs = configsDao.selectOneByExample(example);
        try {
            headerM.put("Referer", "https://mfangxin.58.com/m");
            String cookie = PassportLogin.login(configs.getMobile(), configs.getPassword());
            headerM.put("cookie",cookie);
            if (!apis.getParams().equals("[]"))
            {
                JSONArray paramsJSONArray = (JSONArray) JSONArray.parse(apis.getParams());
                for(int i = 0;i<paramsJSONArray.size();i++)
                {
                    params.put(paramsJSONArray.getJSONObject(i).getString("key"),paramsJSONArray.getJSONObject(i).getString("value"));
                }
            }

            if (apis.getType().equals("GET"))
            {
                jsonObject = HttpRequestBase.doGetJSON(url, params, headerM);
            }
            if (apis.getType().equals("POST"))
            {
                jsonObject = HttpRequestBase.doPostJSON(url, params, headerM);
            }

            return jsonObject;

        }catch (Exception e){
            return jsonObject;
        }
    }
    @Override
    public ResultVo toRun(ApiManager apis)  {
        ResultVo resultVo = new ResultVo();
        apis.getApi();
        Example example = new Example(ApiManager.class);
        example.selectProperties("apiName","domain","api","type","params");
        example.createCriteria();
        example.and()
                .andEqualTo("api",apis.getApi());
        if (Objects.isNull(apisDao.selectOneByExample(example))) {
            if (!apis.getDomain().contains(".com")){
                String result = ipApi(apis);
                if(result.equals("true")){
                    resultVo.setCode(20000);
                    resultVo.setMessage("成功");
                    resultVo.setData("true");
                }
                if(result.equals("false")){
                    resultVo.setCode(20000);
                    resultVo.setMessage("成功");
                    resultVo.setData("false");
                }
                if (result.equals("fail")){
                    resultVo.setCode(50000);
                    resultVo.setMessage("请确认输入的信息对吗？");
                    resultVo.setData("");
                }
            }else {
                //业务的api
                JSONObject jsonObject = businessApi(apis);
                if (Objects.nonNull(jsonObject)){
                    resultVo.setCode(20000);
                    resultVo.setMessage("成功");
                    resultVo.setData(jsonObject);
                }else {
                    resultVo.setCode(50000);
                    resultVo.setMessage("请确认输入的信息对吗？");
                    resultVo.setData("");
                }
            }
        } else {
            resultVo.setCode(50000);
            resultVo.setMessage("已经存在这个接口了");
            resultVo.setData("");
        }

        return resultVo;
    }

    @Override
    public ResultVo toSave(ApiManager apis){
        ResultVo resultVo = new ResultVo();
        Example example = new Example(ApiManager.class);
        example.selectProperties("apiName","domain","api","type","params");
        example.createCriteria();
        example.and()
//                .andEqualTo("apiName",apis.getApiName())
                .andEqualTo("api", apis.getApi());


        if (!Objects.nonNull(apisDao.selectOneByExample(example))){
            apisDao.insert(apis);
            resultVo.setCode(20000);
            resultVo.setMessage("保存成功");
            resultVo.setData(apis);
        }else {
            ApiManager apisU = new ApiManager();
            apisU.setParams(apis.getParams());
            apisU.setApiName(apis.getApiName());
            apisU.setApi(apis.getApi());
            apisU.setDomain(apis.getDomain());
            apisU.setType(apis.getType());
            apisDao.updateByExampleSelective(apisU,example);
            resultVo.setCode(20000);
            resultVo.setMessage("保存成功");
            resultVo.setData(apisU);
        }
        return resultVo;
    }

    @Override
    public ResultVo toSearch(String name){
        ResultVo resultVo = new ResultVo();
        Example example = new Example(ApiManager.class);
        example.createCriteria();
        example.selectProperties("apiName","domain","api","type","params");
        example.and()
                .andLike("apiName","%"+name+"%");
        List<ApiManager> apiManagers = apisDao.selectByExample(example);
        ArrayList<JSONObject> searchContext = new ArrayList();
        for(int i =0 ;i<apiManagers.size();i++){

            searchContext.add((JSONObject) JSONObject.toJSON(apiManagers.get(i)));
        }
        resultVo.setCode(20000);
        resultVo.setMessage("保存成功");
        resultVo.setData(searchContext);
        return resultVo;
    }
}
