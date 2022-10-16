package com.atguigu.eduservice.feignclient;

/**
 * @author:张鹏
 * @description: 在该服务中nacos远程调用的类和方法的声明
 * @date: 2022/7/29 8:35
 */

import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的 服务名 保持一致。
 * @ GetMapping注解用于对被调用的微服务(提供者provider) 进行地址映射。
 * @ PathVariable注解一定要指定参数名称，否则出错
 * @ Component注解防止，在其他位置注入CodClient时idea报错
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class,contextId = "2")//如果远程调用失败就执行该继承类
@Component
public interface VodClient  {

    @DeleteMapping("eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    @DeleteMapping("eduvod/video/deletBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
