package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/21 9:17
 */
public interface OssService {
   //文件上传至阿里云接口
    String uploadFileAvatar(MultipartFile multipartFile);


}
