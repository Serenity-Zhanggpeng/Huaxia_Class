package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/21 9:34
 */
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
@Api(tags = "阿里云文件管理")
public class OssController {
    @Autowired
    private OssService ossService;

    //上传头像的方法
    @ApiOperation(value = "上传的方法")
    @PostMapping("/upload")
    public R uploadOssFile(@ApiParam(name = "file",value = "图片文件",required = true) MultipartFile file)
    {
        //获取上传文件 MultipartFile
        //返回上传路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().message("文件上传成功").data("url",url);
    }

}
