package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author:张鹏
 * @description:     一定要注意注入容器不然后面返回的视频id一直会为空
 * @date: 2022/7/28 10:05
 */
@Component               //注入到ioc容器      标名该类为配置文件  常用的配置文件为xml yaml properties
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")  //读取sping配置文件的类容并赋值
    private String keyId;
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}

