package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.feignclient.VodClient;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //如果用户确定删除，则首先删除video记录，然后删除chapter记录，最后删除Course记录
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询所有视频id
        QueryWrapper<EduVideo> Wrapper = new QueryWrapper<>();
        Wrapper.eq("course_id", courseId);
        Wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(Wrapper);
        //List<EduVideo>变成List<string>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : videoList) {
            if (!StringUtils.isEmpty(eduVideo)) {
                String videoSourceId = eduVideo.getVideoSourceId();
                videoIds.add(videoSourceId);
            }            //stream流


        }
        if (videoIds.size() > 0) {
            vodClient.deleteBatch(videoIds);    //找接口去注册中心实现远程调用  删除一批视频
        }

        QueryWrapper<EduVideo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("course_id", courseId);    //删除课程
        baseMapper.delete(QueryWrapper);

    }
}