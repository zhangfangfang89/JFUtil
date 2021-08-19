package com.jf.util.controller;


import com.jf.util.common.ResultVo;
import com.jf.util.entity.Apis;
import com.jf.util.service.impl.ApisServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;


@Controller
@RequestMapping("/apis")
@ResponseBody
public class ApiController extends BaseController {

    @GetMapping("/searchApi")
    public ResultVo searchApi(@RequestBody Apis apis){
      return new ResultVo();
    }

    @PostMapping(value = "/run")
    public ResultVo runApi(@RequestBody Apis apis) throws URISyntaxException {

        System.out.println(apis);
        ResultVo resultVo = new ResultVo();
        resultVo = new ApisServiceImpl().toRun(apis);


        return resultVo;
    }
}
