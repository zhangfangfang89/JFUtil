package com.jf.util.service.impl;

import com.jf.util.entity.Configs;
import com.jf.util.service.ConfigsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigsServiceImplTest {
    @Resource
    private ConfigsService configsService;

    @Test
    void addKeys() {
        Configs configs = new Configs();
        configs.setKeyName("");
        configs.setText("");
        configs.setMobile("");
        configs.setPassword("");

        configsService.addKeys(configs);
    }

    @Test
    void queryKeys() {
    }
}