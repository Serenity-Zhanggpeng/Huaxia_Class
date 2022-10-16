package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:张鹏
 * @description: 定义搜索对象
 * @date: 2022/7/27 12:58
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQueryVo {
    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
}


