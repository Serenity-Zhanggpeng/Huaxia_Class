package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
public interface EduVideoService extends IService<EduVideo> {
    //如果用户确定删除，则首先删除课程小结下的video记录(小结表有courseId)，然后删除chapter记录，最后删除Course记录
    void removeVideoByCourseId(String courseId);


}
