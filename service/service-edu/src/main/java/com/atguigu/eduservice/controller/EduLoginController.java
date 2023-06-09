package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author:张鹏
 * @description: 教师登陆
 * @date: 2022/7/18 15:59
 */
@Api(description = "登陆管理")
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin          //解决前后短地跨域问题     前后端启动的接口不一样就属于跨域问题
//这里如果网关配置了解决跨域问题的配置类，则对应的服务上就不需要加@CrossOrigin注解，两次解决跨域会出现问题
public class EduLoginController {

    //登陆方法
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }


    //登陆之后获取用户信息的方法i
    @GetMapping("/info")                   //这里不是post请求   改为psost看看报什么错
    public R info() {
        return R.ok().data("roles","[管理员]").data("name","管理员")
                .data("avatar","\thttps://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");
       //登陆之后的图像

    }
}
