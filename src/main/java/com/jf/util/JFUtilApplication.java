package com.jf.util;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jf.util.dao")
public class JFUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(JFUtilApplication.class, args);
    }

}
