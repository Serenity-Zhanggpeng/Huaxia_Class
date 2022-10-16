package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.service.impl.EduCourseServiceImpl;
import com.atguigu.eduservice.service.impl.EduTeacherServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author:张鹏
 * @description: 前端查询8条讲师
 * @date: 2022/8/12 16:55
 */

//@CrossOrigin
@RestController
@RequestMapping("/eduservice/front")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherServiceImpl eduTeacherServiceImpl;

    @GetMapping("/getTeacherFrontList/{page}/{limit}")  //一般查询用get提交 增改用post提交
    public R getTeacherFrontList(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = eduTeacherServiceImpl.getTeacherFrontList(pageTeacher);

        //返回分页的所有数据
        return R.ok().data(map);
    }

    //2 讲师详情的功能
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        //更具讲师id查询课程
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<EduCourse>();
        queryWrapper.eq(EduCourse::getTeacherId, teacherId);
        List<EduCourse> courseList = eduCourseService.list(queryWrapper);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

}
