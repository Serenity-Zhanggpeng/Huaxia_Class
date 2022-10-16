package com.atguigu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/8/3 14:18
 */
@Service
public class MsmServiceIml implements MsmService {

    /**
     *   阿里云代短信接口
     *
     * @param param 验证码以map的2形式存放的数据
     * @param phone 手机号
     * @return
     */
    @Override
    public boolean send(HashMap<Object, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5t7bnCgm4tRkG9SB69Ft", "MPDJJWsmXLAhkGf7frPei5eK07N3qa");//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称
        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
        request.putQueryParameter("SignName", "阿里云短信测试");//短信签名名称
        request.putQueryParameter("TemplateCode", "SMS_154950909");//短信模板ID
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//短信模板变量对应的实际值
        //fastjson将map集合变为json的形式 {code:xxx}
        try {
            //短信的发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}

