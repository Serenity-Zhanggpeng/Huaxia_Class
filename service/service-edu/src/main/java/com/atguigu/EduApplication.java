package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author:张鹏
 * @description: springboot启动类
 * @date: 2022/7/15 14:00
 */


@SpringBootApplication
//因为swaggerconfig使用了配置类 SwaggerConfig ，要使启动类扫描到，要加注解，不然只扫描当前包下的内容
//@ComponentScan(basePackages = {"com.atguigu"})   这里不用加好像也可以 好像新版的原因
@EnableFeignClients   //nacos 添加Nacos客户端注解

public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }

}
