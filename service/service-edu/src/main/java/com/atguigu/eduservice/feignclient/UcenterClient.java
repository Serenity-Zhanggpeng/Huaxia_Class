package com.atguigu.eduservice.feignclient;

import com.atguigu.commonutils.ordervo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author:张鹏
 * @description: 评论功能的 Open Feign远程调用类
 * @date: 2022/8/15 10:08
 */
@Component
@FeignClient(name = "service-ucenter", fallback = CommentFileDegradeFeignClient.class,contextId = "666")
public interface UcenterClient {

    /*
        //定义到调用方法的路径
        @PostMapping("/serviceUcenter/member/getMemberInfoById/{memberId}")
        public UcenterMemberVo getMemberInfoById(@PathVariable String memberId);
       这么写 当nacos启动了  项目启动不了 爆ucenter 服务的错误  可以试试看
    */

     //定义到调用方法的路径
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    UcenterMemberVo getMemberInfoById(@PathVariable("memberId") String memberId);

}

