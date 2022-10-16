package com.atguigu.eduservice.feignclient;

import com.atguigu.servicebase.ExceptionHandler.GuliException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/8/17 16:12
 */

@Component
public class OderClintFileDegradeFeignClient implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new GuliException(20001,"远程调用方法失败");
    }

}
