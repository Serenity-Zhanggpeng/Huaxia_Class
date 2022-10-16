package com.atguigu.servicebase.ExceptionHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:张鹏
 * @description: 自定义异常
 * @date: 2022/7/16 10:25
 */
@Data
@NoArgsConstructor     //生产无参构造方法
@AllArgsConstructor    //有参构造
@Api(tags = "自定义异常类")
public class GuliException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code; //状态码

    private String msg;//异常信息



}
