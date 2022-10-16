package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author:张鹏
 * @description: 课程章节最终发布
 * @date: 2022/7/26 9:57
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
