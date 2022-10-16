package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/20 22:27
 */
/* swagger配置类不在service_oss模块中，启动时无法加载swagger配置类，所以要在启动类上加注解
 @ComponentScan(basePackages = {"com.atguigu"})改变扫表规则，意思是扫描整个项目下com.atguigu的包，
 扫描规则不了解的话看一下双亲委派机制           不加试试 一直会出现弹框 */
//@ComponentScan(basePackages = {"com.atguigu"})  //这里加了报错反而报错 草 好像自动扫描
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除数据库的配置 也可以在配置文件加上数据库的配置
@EnableFeignClients  //nacos
public class OssApplication {
    public static void main(String[] args) {
      SpringApplication.run(OssApplication.class,args);
    }
}
