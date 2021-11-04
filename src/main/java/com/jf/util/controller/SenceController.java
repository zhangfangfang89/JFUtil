package com.jf.util.controller;


import com.jf.util.common.ResultVo;
import com.jf.util.entity.ApiManager;
import com.jf.util.entity.Vo;
import com.jf.util.service.ApisService;
import com.jf.util.service.impl.SenceServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping("/sence")
@ResponseBody
public class SenceController extends BaseController {

    @Resource
    private SenceServiceImpl senceService;



    @PostMapping(value = "/add")
    public ResultVo runApi(@RequestBody Vo vo){
        System.out.println(vo);

        ResultVo resultVo = senceService.insertSence(vo);



        return resultVo;
    }

}
