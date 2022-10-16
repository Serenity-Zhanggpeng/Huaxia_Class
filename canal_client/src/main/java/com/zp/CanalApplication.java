package com.zp;

import com.zp.cannal.client.CanalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/10/4 19:11
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }


    //只要程序在执行，就会一直执行里面的方法
    @Override
    public void run(String... args) throws Exception {
        int i=0;
       //项目启动，执行canal客户端监听
        System.out.println("canal客户端监听:"+i);
        canalClient.run();
    }
}
