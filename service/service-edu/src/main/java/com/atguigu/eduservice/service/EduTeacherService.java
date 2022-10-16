package com.atguigu.eduservice.service;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author ZP
 * @since 2022-07-15
 */

public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectTeacher();


    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);


}
