package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
public interface EduChapterService extends IService<EduChapter> {

    //最终是要得到既有章节里面封装的还有小结的对象集合ChapterVo
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //在ChapterService中定义根据courseId删除chapter业务方法
    void removeChapterByCourseId(String courseId);


    //删除章节
    boolean deleteChapter(String chapterId);
}
