package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoaByAly(MultipartFile multipartFile);

    //删除课程的同时删除 课程下 所有的章节下的小节 云端的视频   一个课程下有多个小结所以包含多个视频
    void removeMoreAlyVideo(List<String> videoList);
}
