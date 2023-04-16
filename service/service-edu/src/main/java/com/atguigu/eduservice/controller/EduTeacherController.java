package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author ZP
 * @since 2022-07-15
 */

//浏览器从一个域名的网页去请求另一个域名的资源时，域名、端口、协议任一不同，都是跨域 。前后端分离开发中，需要考虑ajax跨域的问题。
//@CrossOrigin          //解决前后短地跨域问题     前后端启动的接口不一样就属于跨域问题
//这里如果网关配置了解决跨域问题的配置类，则对应的服务上就不需要加@CrossOrigin注解，两次解决跨域会出现问题
@Slf4j
@RestController                    //@ResponseBody加载类上 作用返回一个Jason的数据
@RequestMapping("/eduservice/teacher")
@Api(description = "讲师管理")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师     在前端添加课程 添加讲师那个下拉框地方调用了
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")    //这些API的注解都会显示在swagger方法页面上
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("items", teacherList);
    }


    /**
     * 逻辑删除讲师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")           //通过路径传值
    public R removeTeacher(@ApiParam(name = "id", value = "讲师的ID", required = true) @PathVariable("id") String id) {   //获取路径中的值赋给String 类id
        log.info("接收到的数据为id={}", id);
        boolean b = eduTeacherService.removeById(id);
        return (b) ? R.ok() : R.error();
    }


    /**
     * 分页查询讲师
     *
     * @param current  当前页
     * @param limit 每页记录
     * @return 查询到的页数
     */
    /*@ApiOperation(value = "分页查询讲师")
    @GetMapping("/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>();
        //调用方法实现分页
        //调用方法时，底层封装，把分页所有数据封装到teacherPage对象里面
        eduTeacherService.page(eduTeacherPage, null);
        List<EduTeacher> eduTeacherList = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();
        return R.ok().data("total", total).data("rows", eduTeacherList);
    }                                  正尼玛垃圾博主  写的啥jb玩意各种bug 分页不行 变量类型又jb、搞错*/

/*
    @ApiOperation(value = "分页讲师列表")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageList(@ApiParam(name = "current", value = "当前页码", required = true)
                      @PathVariable Long current,
                      @ApiParam(name = "limit", value = "每页记录数", required = true)
                      @PathVariable Long limit) {
        log.info("接收到的数据为current={},limit={}", current, limit);
        Page<EduTeacher> pageParam = new Page<>(current, limit);
        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
      */
/*  try {
            int i=1/0;
        }catch (Exception e){
            //执行自定义异常
            throw new GuliException(20001,"执行了自定义异常");
        }*//*

        return R.ok().data("total", total).data("rows", records);
    }
*/

    //4 条件查询带分页的方法 当前页  限制每页显示条目数
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建条件  可以用 LambdaQueryWrapper啊
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        //根据创建的时间降序排序          降序的就、话就是  最新加入的排在前面，从新到旧
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        // mabayies的page() 方法是分页查询第一个参数就是自己new的page对象设置了当前页 limit限制的页数和泛型
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();        //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }


    //添加讲师接口
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        log.info("接收到的数据为:{}", eduTeacher);
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //根据讲师id经行查询
    @ApiOperation(value = "根据讲师id经行查询")
    @ApiImplicitParam(name="id",value = "讲师id",dataType = "string")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        log.info("接收到的参数:{}", id);
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //根据id修改讲师功能   修改注意is_delete为1的修改不了 已经逻辑删除了
    @ApiOperation(value = "根据id修改讲师功能")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        log.info("接收到的参数:{}", eduTeacher);
        boolean flag = eduTeacherService.updateById(eduTeacher);   //传来的实体类必须封装id属性 更具id来udate
        return (flag) ? R.ok() : R.error();
    }


}

