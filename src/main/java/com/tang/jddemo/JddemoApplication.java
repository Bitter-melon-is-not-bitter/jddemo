package com.tang.jddemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.tang.jddemo.mapper")
@EnableScheduling//加定时任务
public class JddemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JddemoApplication.class, args);
    }

}
