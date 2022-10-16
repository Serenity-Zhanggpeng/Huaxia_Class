package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-21
 */
public interface EduSubjectService extends IService<EduSubject> {
    //将前端上传的excel文件 写入到数据库中去
    void saveSubject(MultipartFile multipartFile,EduSubjectService eduSubjectService);

    //得到一二级分类
    List<OneSubject> getAllOenTwoSubject();
}
