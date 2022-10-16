package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.AliyunPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/21 9:21
 */
@Service
public class OssServiceImpl implements OssService {
    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {   //这里的file是个图片文件
        //工具类获取值
        String endpoint = AliyunPropertiesUtils.END_POINT;
        String accessKeyId = AliyunPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = AliyunPropertiesUtils.ACCESS_KEY_SECRET;
        String backetName = AliyunPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名
            String filename= file.getOriginalFilename();

/*
            //在文件名称里添加随机唯一的值        ------优化防止文件名字重复一样会进行覆盖
            String uuid= UUID.randomUUID().toString().replace("-","");
            filename=uuid+filename;

            //把文件按日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接路径
            filename=datePath+"/"+filename;

*/

            //第一个参数 Backet名称
            //第二个参数 上传到oss文件路径和文件名称
            //第三个参数 上传文件输入流

            ossClient.putObject(backetName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //需要把上传文件到阿里云的路径手动拼接出来
            //https://guli-mypicture.oss-cn-hangzhou.aliyuncs.com/v2-6e7e35877476df6576c55ac0e338050e_720w.jpg
            String url="https://"+backetName+"."+endpoint+"/"+filename;
            System.out.println("上传图片成功,图片地址为:"+url);
            return url;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }
}
