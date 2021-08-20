package com.jf.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.jf.util.dao")
public class JFUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(JFUtilApplication.class, args);
    }

}
