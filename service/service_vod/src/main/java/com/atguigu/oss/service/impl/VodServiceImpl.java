package com.atguigu.oss.service.impl;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.atguigu.oss.service.VodService;
import com.atguigu.oss.utils.ConstantVodUtils;
import com.atguigu.oss.utils.InitVodClient;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/28 9:47
 */
@Service
public class VodServiceImpl implements VodService {


    //从本地服务端上传文件(video)to Aly 后返回从阿里云返回视频的id
    @Override
    public String uploadVideoaByAly(MultipartFile multipartFile) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = multipartFile.getOriginalFilename();
            //title：上传到Aly之后显示名称   这里和源文件名保持一致且去掉后缀名.mp4等      01.02.03.mp4
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = multipartFile.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.
                    ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            System.out.println("Get videoId is——————" + videoId);
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(4673, "上传视频失败");
        }
    }

    //删除课程的同时删除云端的视频   一个课程下包含多个章节 有多个小结所以包含多个视频
    @Override
    public void removeMoreAlyVideo(List<String> videoList) {
        try {
            //初始化对象
            DefaultAcsClient acsClient = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置删除的视频id
            String ids = StringUtil.join(videoList.toArray(), ",");
            //11af31,123dad,3xaswr AYL需要这种id形式而不是直接传个数组
            request.setVideoIds(ids);
            acsClient.getAcsResponse(request); //request参数 执行后返回responce对象
            System.out.println("删除视频成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }

    }
}
