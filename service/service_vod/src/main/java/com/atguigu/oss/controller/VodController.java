package com.atguigu.oss.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.oss.service.VodService;
import com.atguigu.oss.service.impl.VodServiceImpl;
import com.atguigu.oss.utils.ConstantVodUtils;
import com.atguigu.oss.utils.InitVodClient;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/28 9:44
 */

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    @Autowired
    private VodServiceImpl vodServiceImpl;

    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {  //后端接口参数必须是file 和前端保持也一至 不让接收不到文件
        //返回上传视频的id
        String viedoId = vodService.uploadVideoaByAly(file);
        return R.ok().data("videoId", viedoId);   //如果出错由我们的异常捕获器处理 不需要写if R.eero
    }

    @ApiOperation(value = "更具阿里云视频id删除对应的视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id) {  //AYL生成的id是个字符串
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置删除的视频id
            request.setVideoIds(id);
            //发起请亲对象
            client.getAcsResponse(request);
            System.out.println("删除上传视频成功");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");  //出错后手动删抛出异常
        }
    }

    //删除多个阿里云视频的方法    在删除课程的同时调用的
    //参数多个视频id          list
    @DeleteMapping("deletBatch")             //前端接口还没写???
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //上面写的都是后台管理接口的部分    下面是前端的
    //    视频id获取视频播放凭证
    @GetMapping("/getVideoPlayAuth/{id}")
    @ApiOperation(value = "获取视频播放凭证", notes = "获取视频播放凭证")
    public R getVideoPlayAuth(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //更具凭证获取request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            System.out.println("根据视频id获取到的凭证为:" + playAuth);
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(2001, "获取凭证失败");
        }
    }

}
