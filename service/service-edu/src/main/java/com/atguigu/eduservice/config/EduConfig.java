package com.atguigu.eduservice.config;


import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:张鹏
 * @description: 配置类都写在这
 * @date: 2022/7/15 14:04
 */

@Configuration                 //这个注解就表明该类是个配置类
@MapperScan("com/atguigu/eduservice/mapper")            //或者在创建mapper接口的时候每个都加mapper注解
public class EduConfig {

    /**
     * Mybatis-plus提供的逻辑删除插件
     *
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * @author:张鹏
     * @description: 讲师分页功能  mybatis_plus分页插件 配置类  旧版的
     * @date: 2022/7/15 20:31
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }



}
