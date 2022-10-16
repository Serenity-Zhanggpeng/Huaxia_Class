package com.atguigu.staservice.feignclent.hystrix;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.atguigu.staservice.feignclent.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @author:张鹏
 * @description: 熔断机制 远程调用失败执行此类
 * @date: 2022/8/18 21:21
 */
@Component
public class UcenterHystrix implements UcenterClient {
    @Override
    public R countRegister(String day) {
        throw new GuliException(2001,"UcneterService服务远程方法调用失败");
    }
}
