package com.atguigu.eduservice.feignclient;

import com.atguigu.commonutils.ordervo.UcenterMemberVo;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/8/15 10:18
 */
@Component
public class CommentFileDegradeFeignClient implements UcenterClient{
    @Override
    public UcenterMemberVo getMemberInfoById(String memberId) {
        throw new GuliException(20001,"远程调用方法失败");
    }
}
