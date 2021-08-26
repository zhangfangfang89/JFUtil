package com.jf.util.service;

import com.jf.util.common.ResultVo;
import com.jf.util.entity.ApiManager;


public interface ApisService {
    ResultVo toRun(ApiManager apis);
    ResultVo toSave(ApiManager apis);
    ResultVo toSearch(String apiName);
}
