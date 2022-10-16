package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.service.impl.EduSubjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
@Api(description = "一二级课程分类管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @Autowired
    private EduSubjectServiceImpl subjectServiceImpl;
    //添加课程分类
    //获取上传过来的文件，把内容读取出来，就不用上传到服务器
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile multipartFile){
        // 获取上传的excel文件 MultipartFile
        subjectServiceImpl.saveSubject(multipartFile,subjectService);

        return R.ok();
    }

    //查询多有分类的方法
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> subjectList=subjectService.getAllOenTwoSubject();
         return R.ok().data("list",subjectList);
    }

}

