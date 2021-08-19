package com.jf.util.service;

import com.jf.util.common.ResultVo;
import com.jf.util.entity.Apis;

import java.net.URISyntaxException;

public interface ApisService {

    ResultVo insert(Apis apis);
    ResultVo toRun(Apis apis) throws URISyntaxException;
}
