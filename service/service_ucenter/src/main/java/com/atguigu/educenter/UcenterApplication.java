package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/9/20 20:15
 */

@SpringBootApplication
@EnableDiscoveryClient   //nacos注册
@EnableFeignClients        //远程调用
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
