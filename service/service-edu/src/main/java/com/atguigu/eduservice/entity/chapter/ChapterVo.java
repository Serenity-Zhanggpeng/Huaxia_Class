package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:张鹏
 * @description:
 * 章节和小结的结合体  最终是要通过课程id course_id查询Edu_cahpter既有所有的章节小结表(字段多)
 * 查出所有章节 和小结  应为前端要的时一个既有章节又有小结的实体类 这么设为了ChapterVo
 * 所以将查出的的数据封到该对象里面去    !!!!!!!!!!!!!!!!!核心思想
 *
 *
 * @date: 2022/7/24 11:07
 */
@Data
@ApiModel(value = "章节信息")
public class ChapterVo {
    private String id;
    private String title;
    //小结
    private List<Video> children = new ArrayList<>();
}

