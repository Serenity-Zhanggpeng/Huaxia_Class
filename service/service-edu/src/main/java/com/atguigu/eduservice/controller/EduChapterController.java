package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/eduservice/chapter")

@Api(description = "章节管理")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;


    @ApiOperation(value = "根据课程id得到所有的课程章节和小结")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", chapterVoList);
    }


    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    @ApiParam(name = "eduChapter", value = "章节对象", required = true)
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }


    //根据章节id查询
    @ApiOperation(value = "根据ID查询章节")
    @ApiParam(name = "chapterId", value = "章节ID", required = true)
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    //修改章节
    @ApiOperation(value = "根据ID修改章节")
    @ApiParam(name = "eduChapter", value = "章节对象", required = true)
    @PostMapping("/updateChapter")    //有@RequestBody 一定时Post提交方式么
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //如果章节下没有小结才能删除
    @ApiOperation(value = "根据ID删除章节")
    @ApiParam(name = "chapterId", value = "章节ID", required = true)
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        return (flag) ? R.ok():R.error();
    }

}

