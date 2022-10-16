package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.feignclient.VodClient;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * <p>
 * 课程视频 前端控制器          小结的控制层
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(description = "小结管理")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;

    @Autowired(required=true)
    VodClient vodClient;    //nacos注册中心  远程调用


    //用于点击编辑小结时用于小结数据的回显
    @ApiOperation(value = "根据小结id查询小结(也叫课时)")
    @GetMapping("/getVideoById/{id}")
    public R etVideoById(@PathVariable String id) {
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video", video);
    }

    //修改小节
    @ApiOperation(value = "更新小结(课时)")
    @PostMapping("/updateVideo")
    public R updateCourseInfo(@RequestBody EduVideo eduVideo) {
        return (eduVideoService.updateById(eduVideo)) ? R.ok() : R.error();
    }


    //添加小节
    @ApiOperation(value = "新增课时")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        return (eduVideoService.save(eduVideo)) ? R.ok() : R.error();
    }
      
    //删除小节
    @ApiOperation(value = "根据ID删除课时")
    @ApiParam(name = "id", value = "课时ID", required = true)
    @PostMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //在删除小结的那么阿里云所存储的视频就无意义了  所以还要删除更具阿里云视频id删除对应的视频
        //更具id获取唯一的小结对象
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        R r = vodClient.removeAlyVideo(videoSourceId);//远程vod服务的方法  删除AYL视频
        //测试熔断机制 看看远程调用成功没有
        if (r.getCode()==20001){   //20001我们规定异常  2000表示成功
            throw new GuliException(20001,"删除视频失败,执行了熔断器");
        }
        boolean b =eduVideoService.removeById(id);
        if(b){
            return R.ok();
        }
        else{
            return R.error();
        }
    }

}