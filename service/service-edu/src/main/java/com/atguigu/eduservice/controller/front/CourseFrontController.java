package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontCourseVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontCourseVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.feignclient.OrderClient;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.impl.EduChapterServiceImpl;
import com.atguigu.eduservice.service.impl.EduCourseServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduCourseServiceImpl courseServiceImpl;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduChapterServiceImpl eduChapterServiceImpl;

    @Autowired
    private OrderClient orderClient;


    //1 条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }


    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseServiceImpl.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = eduChapterServiceImpl.getChapterVideoByCourseId(courseId);

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId" + memberId);

        //远程调用其他服务的方法 更具课程id courseId和用户id memberId 查询课程的状态 是否已经购买   statue为1 已经购买
//        boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);



        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
//                .data("isbuyCourse", isBuyCourse);
    }


    //根据课程id查询课程信息           被远程调用
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOder(@PathVariable String id){
        CourseWebVo courseInfo = courseServiceImpl.getBaseCourseInfo(id);   //查少了字段
        //SELECT id,description,gmt_create,gmt_modified FROM edu_course_description WHERE id=? 前端的teacher
        //为空,页面显示错误
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        System.out.println("被eduOrder远程调用成功");
        return courseWebVoOrder;
    }

  //两个bug   第一个人开始用的getCourseInfo  查处的字段少了很多 id,description,gmt_create,gmt_modified
    //第二个bug后端没问题 前端接受值错了
}












