package com.atguigu.servicebase.ExceptionHandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:张鹏
 * @description: 统一异常处理
 * @date: 2022/7/16 9:36
 */
@ControllerAdvice
@Slf4j               //类上添加注解 @Slf4j 这个是lombok的注解，用来打印日志用的
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法    全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody      //为了返回数据为将java转为json
    public R error(Exception e) {
        log.info(e.getMessage());    //将错误信息输出到控制台的同时下 也写到我们配置的日志文件中(log_error.log)中去
        e.printStackTrace();
        return R.error().message(e.getMessage());
    }


    //特定异常处理     先执行他没有在执行全局的
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody      //为了返回数据  为将java转为json
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
