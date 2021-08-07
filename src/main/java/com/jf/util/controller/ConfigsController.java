package com.jf.util.controller;


import com.jf.util.common.ResultVo;
import com.jf.util.entity.Configs;
import com.jf.util.service.ConfigsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/configs")
@ResponseBody
public class ConfigsController extends BaseController{
    @Resource
    private ConfigsService configsService;
    @PostMapping("/save")
    public ResultVo addIp(@RequestBody Configs configs){
        ResultVo resultVo = configsService.addIP(configs);
        return resultVo;
    }
}
