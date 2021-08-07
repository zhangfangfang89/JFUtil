package com.jf.util.service;

import com.alibaba.fastjson.JSONObject;
import com.jf.util.common.ResultVo;
import com.jf.util.entity.Configs;

public interface ConfigsService {
    ResultVo addIP(Configs configs);
    ResultVo queryIp(Configs configs);
}
