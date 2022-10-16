package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.EduCourseVo;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.CoursePublishVo;
import com.atguigu.eduservice.entity.chapter.CourseQueryVo;
import com.atguigu.eduservice.entity.frontCourseVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.impl.EduCourseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
@Api(description = "课程管理")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduCourseServiceImpl eduCourseServiceImpl;


    //删除课程
    @ApiOperation(value = "根据ID删除课程")
    @PostMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        //先删除课程下所有的章节的小结下的视频 再删除小结 在删除章节 看源码   更具课程id查出所有小结下的视频id
        boolean remove = eduCourseServiceImpl.removeCourse(courseId);
        if(remove){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

    //课程列表查询  @RequestBody(required = false) CourseQueryVo courseQueryVo,
    //课程分页查询  页面渲染前就发送没带条件的courseVo对象的属性  当前页和限制的页数是前端代码定义的
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public R getCourseList(CourseQueryVo courseQueryVo,
                           @PathVariable Long page, @PathVariable Long limit){
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);  //创建page分页对象 添加分页
        eduCourseServiceImpl.pageQuery(eduCoursePage,courseQueryVo);
        List<EduCourse> courseQueryList = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        return R.ok().data("eduCourseList",courseQueryList).data("rows",total);
        //返回的属性eduCourse类的属性比 eduCourseList属性多  他是eduCourse属性的简化版   创建这么个对象就是
        //为了接收前端的对象 应为前端有的属性不需要展示 只展示某些属性 所以后端搞了简化版的对象  但是后端查数据库还是
        //查原版最多属性对应的表educourse 且返回也是这么对象的集合  且但会也是这么个对象
        // 前端封装的时候 还有多余的属性没用  拿到要展示的·1属性
    }





    //课程最终发布
    //修改课程状态

    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");  //设置课程的发布状态
        return (courseService.updateById(eduCourse)) ? R.ok() : R.error();
    }


    //在添加课程最终发布的用到
    @ApiOperation(value = "根据课程id查询课程信息 回显在最终发布的界面")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }


    //添加课程基本信息  在数据库添加到课程表和课程描述表 看源码
    @ApiOperation(value = "新增课程")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }


    // 添加课程中 创建课程大纲 在点击上一步   点击上一步会进行一个数的回显   课程信息回显用到了
    //根据课程id查询课程基本信息
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //更新课程信息  更新课程信息 前端肯定要传来一个CourseInfoVo 对象  然后将他属性拆解 分别存放到edu_course课程表和描述表中去
    @ApiOperation(value = "更新课程")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }



    //根据课程id，查询课程信息【订单】      根据课程id查询课程信息
    // 用于nacos远程调用   educomment评论模块和oderservice支付模块的 远程调用
    @PostMapping("/getCourseInfoByIdOrder/{courseId}")
    public EduCourseVo getCourseInfoByIdOrder(@PathVariable String courseId) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(courseId);

        EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(courseInfo, eduCourseVo);

        return eduCourseVo;
    }


}

