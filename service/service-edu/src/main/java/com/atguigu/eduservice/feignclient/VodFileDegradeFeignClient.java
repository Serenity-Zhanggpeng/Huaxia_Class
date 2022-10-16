package com.atguigu.eduservice.feignclient;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:张鹏
 * @description: 在service-edu的client包里面 创建熔断器的实现类
 * @date: 2022/7/29 15:15
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频错误");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除视频错误");
    }
}
