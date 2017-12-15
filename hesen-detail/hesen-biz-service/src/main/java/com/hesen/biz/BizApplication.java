package com.hesen.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@MapperScan("com.hesen.biz.dao")
@SpringBootApplication()
public class BizApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizApplication.class, args);
    }
}