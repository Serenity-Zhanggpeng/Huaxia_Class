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
 * @description: 小结实体类
 * @date: 2022/7/24 11:11
 */

@ApiModel(value = "小结信息")
@Data
public class Video {
    @ApiModelProperty(value = "小结节id")
    private String id;
    @ApiModelProperty(value = "小结名称")
    private String title;

    @ApiModelProperty(value = "云端视频资源id")
    private String videoSourceId;
}
