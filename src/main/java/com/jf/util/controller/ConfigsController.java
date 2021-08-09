package com.jf.util.controller;


import com.jf.util.common.ResultVo;
import com.jf.util.entity.Configs;
import com.jf.util.service.ConfigsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/configs")
@ResponseBody
public class ConfigsController extends BaseController{
    @Resource
    private ConfigsService configsService;
    @PostMapping("/save")
    public ResultVo addKeys(@RequestBody Configs configs){
        ResultVo resultVo = configsService.addKeys(configs);
        return resultVo;
    }

    @GetMapping("/gettext")
    public ResultVo getText(String keys){
        ResultVo resultVo = configsService.queryKeys(keys);
        return resultVo;
    }
}
