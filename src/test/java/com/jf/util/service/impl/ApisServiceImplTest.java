package com.jf.util.service.impl;

import com.jf.util.entity.ApiManager;
import com.jf.util.service.ApisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApisServiceImplTest {
    @Autowired
    private ApisService apisService;

    @Test
    void toRun() {
        apisService.toRun(null);
    }

    @Test
    void toSave() {
        ApiManager apis = new ApiManager();
        apis.setApiName("");
        apis.setDomain("");
        apis.setApi("");
        apis.setType("");
        apis.setParams("");
        apis.setResult("");
        apisService.toSave(apis);
    }
}