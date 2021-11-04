package com.jf.util.controller;


import com.jf.util.common.ResultVo;
import com.jf.util.entity.ApiManager;
import com.jf.util.service.ApisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
@RequestMapping("/apis")
@ResponseBody
public class ApiController extends BaseController {

    @Resource
    private ApisService apisService;

    @GetMapping("/search")
    public ResultVo searchApi(@RequestParam String context){
        ResultVo resultVo = apisService.toSearch(context);

        return resultVo;
    }

    @PostMapping(value = "/run")
    public ResultVo runApi(@RequestBody ApiManager apis){
        ResultVo resultVo = new ResultVo();
        resultVo = apisService.toRun(apis);


        return resultVo;
    }
    @PostMapping("/save")
    public  ResultVo saveApi(@RequestBody ApiManager apis){
        ResultVo resultVo = new ResultVo();
        resultVo = apisService.toSave(apis);
        return resultVo;
    }
}
