package com.jf.util.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.HttpRequestBase;
import com.jf.util.common.ResultVo;
import com.jf.util.dao.ApisDao;
import com.jf.util.dao.SenceDao;
import com.jf.util.dao.StepDao;
import com.jf.util.entity.ApiManager;
import com.jf.util.entity.SenceManager;
import com.jf.util.entity.StepManager;
import com.jf.util.entity.Vo;
import com.jf.util.pplogin.PassportLogin;
import com.jf.util.service.SenceService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SenceServiceImpl implements SenceService {
    @Resource
    SenceDao senceDao;
    @Resource
    StepDao stepDao;
    @Resource
    ApisDao apisDao;





    @Override
    public ResultVo insertSence(Vo vo) {
        SenceManager sence = new SenceManager();
        sence.setName(vo.getName());
        sence.setBmobile(vo.getBmobile());
        sence.setBpassword(vo.getBpassword());
        sence.setStep(vo.getStep());
        ResultVo resultVo = new ResultVo();
        Example example =new Example(SenceManager.class);
        example.selectProperties("name","step","bmobile","bpassword","cmobile","customerId","demandId","orderId","agentId","auntId");
        example.createCriteria();
        example.and().andEqualTo("name",sence.getName());
        SenceManager senceS= senceDao.selectOneByExample(example);
        if (Objects.isNull(senceS)){
            Integer id = senceDao.insert(sence);
            resultVo.setCode(20000);
            resultVo.setMessage("新增成功");
            resultVo.setData(sence);
        }else {
            SenceManager recode = new SenceManager();
            recode.setName(sence.getName());
            recode.setStep(sence.getStep());
            recode.setBmobile(sence.getBmobile());
            recode.setBpassword(sence.getBpassword());
            recode.setCmobile(sence.getCmobile());
            senceDao.updateByExampleSelective(recode,example);
            resultVo.setCode(20000);
            resultVo.setMessage("更新成功");
            resultVo.setData(recode);
        }

        List steplist =Arrays.asList( vo.getStep().split(","));
        List paramList = Arrays.asList(vo.getInputParams().split(","));
        for(int i=0;i<steplist.size();i++){
            StepManager stepManager = new StepManager();
            stepManager.setName((String) steplist.get(i));
            stepManager.setSenceName(vo.getName());
            stepManager.setParams((String) paramList.get(i));

            Example example1 =new Example(StepManager.class);
            example1.selectProperties("name","params","senceName");
            example1.createCriteria();
            example1.and().andEqualTo("senceName",stepManager.getSenceName()).andEqualTo("name",stepManager.getName());
            StepManager stepManager1 = stepDao.selectOneByExample(example1);
            if(Objects.isNull(stepManager1)){
                stepDao.insert(stepManager);
            }else {
                StepManager record = new StepManager();
                record.setName(stepManager.getName());
                record.setParams(stepManager.getParams());
                stepDao.updateByExample(record,example1);
            }

        }

        return resultVo;
    }

    public String getkeyValue(String k,Object resp){

        if (resp.getClass().getName().equals("com.alibaba.fastjson.JSONArray")){

            for (int i=0; i<=((JSONArray) resp).size();i++){


                return getkeyValue(k,((JSONArray) resp).getJSONObject(i));
            }

        }
        if (resp.getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
            if(((JSONObject) resp).keySet().contains(k)){
                return ((JSONObject) resp).getString(k);
            }else{
                Set<String> kSet = ((JSONObject) resp).keySet();
                for (int j =0;j<=kSet.size();j++){
                    String key_e = kSet.iterator().next();
                    if (((JSONObject) resp).get(key_e).getClass().getName().equals("com.alibaba.fastjson.JSONArray")){
                        for (int i=0; i<=((JSONArray) resp).getJSONArray(j).size();i++){

                            return getkeyValue(k,((JSONArray) resp).getJSONArray(j).get(i));
                        }

                    }
                    if (((JSONObject) resp).get(key_e).getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
                        if(((JSONObject) resp).getJSONObject(key_e).keySet().contains(k)){
                            return ((JSONObject) resp).getJSONObject(key_e).getString(k);
                        }


                }
                }
            }

        }

        return null;
    }

    public ResultVo runSence(String name) throws Exception {
        ResultVo resultVo = new ResultVo();
        JSONObject res = null;
        Example example = new Example(SenceManager.class);
        //场景的表
        example.selectProperties("name","step","bmobile","bpassword","cmobile","customerId","demandId","orderId","agentId","auntId");
        example.createCriteria();
        example.and().andEqualTo("name",name);
        SenceManager senceManager = senceDao.selectOneByExample(example);
        //获取uup
        String bmobile = senceManager.getBmobile();
        String bpassword = senceManager.getBpassword();
        String cookie = PassportLogin.login(bmobile, bpassword);

        List<String> stepList = Arrays.asList(senceManager.getStep().split(","));

        //进入每个步骤的执行
        for (int i=0;i<stepList.size();i++) {
            Example example1 =new Example(StepManager.class);
            example1.selectProperties("name","params","senceName");
            example1.createCriteria();
            example1.and().andEqualTo("senceName",senceManager.getName()).andEqualTo("name",stepList.get(i));
            StepManager stepManager1 = stepDao.selectOneByExample(example1);

            //分解单步骤后输入的请求参数为hashmap
            String paramInputS = stepManager1.getParams();
            HashMap<String,String> paramIputM = new HashMap<>();
            String[] paramInputA = paramInputS.trim().split(",");
            for (int z=0 ; z<paramInputA.length;z++){
                paramIputM.put(paramInputA[i].trim().split("=")[0],paramInputA[i].trim().split("=")[1]);
            }

            //
            Example example2 =new Example(ApiManager.class);
            example2.selectProperties("apiName","domain","api","type","params");
            example2.createCriteria();
            example.and()
                    .andEqualTo("apiName", stepManager1.getName());
            ApiManager apiManager = apisDao.selectOneByExample(example2);
            String type = apiManager.getType();
//            String params = apiManager.getParams();
            String domain = apiManager.getDomain();
            String api = apiManager.getApi();
            String url = domain+api;
            HashMap<String,String> hearderRun = new HashMap<>();
            hearderRun.put("Referer", "https://mfangxin.58.com/m");
            hearderRun.put("cookie",cookie);
            //下面是处理下每个api的请求参数为hashMap
            HashMap<String,String> paramRun = new HashMap<>();
            JSONArray paramsJSONArray = (JSONArray) JSONArray.parse(apiManager.getParams());

            for (int j = 0; j < paramsJSONArray.size(); j++) {
                Iterator<Map.Entry<String, Object>> iterator = paramsJSONArray.getJSONObject(i).entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Object> entryM= iterator.next();
                    paramRun.put(entryM.getKey(), "");
                };
            }

            //第一步骤或者上一个步骤接口返回data为空的情况走下面的代码
            if(res.isEmpty() && i==0 && res.getJSONObject("data").isEmpty()){
                Map<String,String> paramsApi = new HashMap<>();

                if (!paramIputM.keySet().containsAll(paramRun.keySet())){
                    resultVo.setCode(50000);
                    resultVo.setMessage(stepList.get(i)+"步骤缺少请求参数");

                    return resultVo;
                }else {


                    if (type.equals("GET")) {
                        res =HttpRequestBase.doGetJSON(url, paramIputM, hearderRun);
                        continue;
                    }
                    if (type.equals("POST")) {
                        res =HttpRequestBase.doPostJSON(url, paramIputM, hearderRun);
                        continue;
                    }
                }

            }
            //上一个步骤中的data有值
            else {
                Set<String> inputKey = paramRun.keySet();
                Iterator<String> runKey = paramRun.keySet().iterator();
                while (runKey.hasNext()){
                    String k = runKey.next();
                    if (inputKey.contains(k)){
                        paramRun.put(k,paramIputM.get(k));
                    }else {
                        paramRun.put(k,getkeyValue(k,res.getJSONObject("data")));
                    }
                    }
                if (type.equals("GET")) {
                    res =HttpRequestBase.doGetJSON(url, paramIputM, hearderRun);
                    continue;
                }
                if (type.equals("POST")) {
                    res =HttpRequestBase.doPostJSON(url, paramIputM, hearderRun);
                    continue;
                }
                }

            }

        return resultVo;
        }
//        Example example1 =new Example(StepManager.class);
//        example1.selectProperties("name","params","senceName");
//        example1.createCriteria();
//        example1.and().andEqualTo("senceName",senceManager.getName()).andEqualTo("name","???");
//        StepManager stepManager1 = stepDao.selectOneByExample(example1);



    @Override
    public ResultVo querySence(String name) {
        ResultVo resultVo = new ResultVo();
        Example example = new Example(SenceManager.class);
        example.selectProperties("name","step","bmobile","bpassword","cmobile","customerId","demandId","orderId","agentId","auntId");
        example.createCriteria();
        example.and().andLike("name","%"+name+"%");
        List<SenceManager> senceManagers = senceDao.selectByExample(example);
        ArrayList<JSONObject> searchContext = new ArrayList();
        for(int i =0 ;i<senceManagers.size();i++){

            searchContext.add((JSONObject) JSONObject.toJSON(senceManagers.get(i)));
        }
        resultVo.setCode(20000);
        resultVo.setMessage("查询成功");
        resultVo.setData(searchContext);
        return resultVo;
    }

    @Override
    public ResultVo updateSence(SenceManager sence) {
        return null;
    }

    @Override
    public ResultVo deleteSence(SenceManager sence) {
        ResultVo resultVo = new ResultVo();
        Example example = new Example(SenceManager.class);
        example.selectProperties("name","step","bmobile","bpassword","cmobile","customerId","demandId","orderId","agentId","auntId");
        example.createCriteria();
        example.and().andEqualTo("name",sence.getName());
        SenceManager senceManager = senceDao.selectOneByExample(example);
        if (Objects.nonNull(senceManager)){
            Integer id = senceDao.delete(senceManager);
            resultVo.setCode(20000);
            resultVo.setMessage("删除成功");
            resultVo.setData(sence);
        }
       return resultVo;
    }


}

