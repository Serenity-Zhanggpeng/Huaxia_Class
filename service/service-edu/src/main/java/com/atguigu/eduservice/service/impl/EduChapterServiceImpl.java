package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.Video;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入小节service  要操作小结的数据库的

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id查询课程里面所有章节  eduChapterList
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<EduChapter>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);

        //根据课程id查询课程里面所有的小节  eduVideoList
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(eduVideoQueryWrapper);

        List<ChapterVo> finalList=new ArrayList<>();

        //遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            ChapterVo chapterVo = new ChapterVo();
            EduChapter eduChapter = eduChapterList.get(i);
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //创建集合，用于封装章节的小节
            List<Video> videoList=new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                //判断 小节里面的chapterid和章节里面的id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    Video videoVo=new Video();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到封装到小节集合
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
            finalList.add(chapterVo);

        }
        //遍历查询小结list集合进行封装
        return finalList;
    }

    //删除章节  定义如果章节下没有小结才能删除
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId章节id查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(QueryWrapper);
        //判断
        if(count>0){//能查询出来小节,不进行删除
            throw new GuliException(20001,"不能删除");
        }else { //没有查询到数据，进行删除
            //删除章节
            int result=baseMapper.deleteById(chapterId);
            return result>0;
        }

    }

    //在ChapterService中定义根据courseId删除chapter业务方法
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }

}
