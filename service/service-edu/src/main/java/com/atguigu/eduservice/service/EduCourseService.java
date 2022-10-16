package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.CoursePublishVo;
import com.atguigu.eduservice.entity.chapter.CourseQueryVo;
import com.atguigu.eduservice.entity.frontCourseVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontCourseVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
public interface EduCourseService extends IService<EduCourse> {


    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    //更新课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //课程最终发布时 根据课程id 获取课程信息  sql是采用的是多表联合操作来查询直接返回想要的
    // 数据类型CoursePublishVo类       不是查到数据在经行copy封装xxVO中
    CoursePublishVo publishCourseInfo(String id);

    //条件分页查询
    void pageQuery(Page<EduCourse> eduCoursePage, CourseQueryVo courseQuery);

    //删除课程
    boolean removeCourse(String courseId);


    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);


    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
