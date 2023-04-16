package com.atguigu.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: StarSea99
 * @Date: 2020-10-24 13:19
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})//指定扫描位置
@MapperScan("com.atguigu.educms.mapper")
public class ServiceCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCmsApplication.class, args);
    }
}
