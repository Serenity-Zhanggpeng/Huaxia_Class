package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author:张鹏
 * @description: 读取配置文件工具类  使用@Value读取application.properties里的配置内容
 *用spring的 InitializingBean 的 afterPropertiesSet 来初始化配置信息，这个方法将在所有的属性被初始化后调用。
 * @date: 2022/7/21 9:04
 */

//实现这个类的目的 在项目启动后 spring加载后 dug启动 就会执行接口中的方法在经行一次赋值     不让的话这下面这些类是私有的执行后面用不了
@Component
public class AliyunPropertiesUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")     //出去配置文件的值赋给下面变量
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;                       //在经行一次复制
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
