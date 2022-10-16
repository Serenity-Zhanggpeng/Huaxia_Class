package com.atguigu.controller;

import com.atguigu.commonutils.R;
import com.atguigu.service.MsmService;
import com.atguigu.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/8/3 14:17
 */

@RequestMapping("/edumsm/msm")
@RestController
//@CrossOrigin
public class Msmcontroller {
    @Autowired
    private MsmService msmService;
    //http://localhost:9001/edumsm/msm/send/13297053202

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信的方法
    @ApiOperation(value = "短信发送Api")
    @GetMapping("send/{phone}")
    public R sendMes(@PathVariable(value = "phone",required = true) String phone) {

        //先从redis获取验证码，如果获取到直接返回                  第一次肯定是获取不到的
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //如果redis获取不到，进行阿里云发送

        //生成随机验证码
        code = RandomUtil.getFourBitRandom();
        System.out.println("随机生成的验证码为:" + code);
        //将验证码吹放到hashcode中 后面方便将code转为JSO的形式
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("code", code);

        //调用service发送短信的方法 将验证码发送到对应的手机号
        if (msmService.send(hashMap, phone)) {
            //如果发送成功,就将手机号和验证码存放到redis中 key为怕phone  value为code 并设置5分钟有效
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
