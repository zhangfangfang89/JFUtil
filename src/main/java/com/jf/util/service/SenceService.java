package com.jf.util.service;
import com.jf.util.common.ResultVo;
import com.jf.util.entity.SenceManager;
import com.jf.util.entity.Vo;

public interface SenceService {
    ResultVo insertSence(Vo vo);
    ResultVo querySence(String name);
    ResultVo updateSence(SenceManager sence);
    ResultVo deleteSence(SenceManager sence);
}
