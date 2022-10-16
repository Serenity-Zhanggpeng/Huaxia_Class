package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-07-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile multipartFile, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream inputStream = multipartFile.getInputStream();

            //调用方法进行 读取传来的excel文件 到数据库中  read底层帮我们封装了
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            throw new GuliException(20002, "添加课程分类失败");
        }

    }

    //得到一二级分类            看懂数据库地关系 以及设计 才能写 parentid=0为一级分类
    @Override
    public List<OneSubject> getAllOenTwoSubject() {
        //查询所有一级分类 parentid=0                 sql:select * from tb where parentid=0
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduSubject::getParentId, "0");
        List<EduSubject> OneSubjectList = this.list(wrapper);  //liet的底层实现还是baseMapper的selectList   sql:

        //查询所有二级分类 parentid!=0
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id", "0");  //lamdq不支持直接写字段
        List<EduSubject> TwoSubjectList = baseMapper.selectList(wrapper1);
        //应为当前对象 EduSubjectServiceImpl继承了ServiceImpl 而他有是实现类basemapper 所以直接可用ServiceImpl和basemapper

        //创建list集合，用于存放最终封装的数据  应为最终返回的要一级分类地list对象   OneSubject一级分类对象有二级分类list的属性
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每一级分类对象，获得每个一级分类对象值
        //封装到要求的list集合里面   无线套娃
        for (EduSubject eduSubject : OneSubjectList) {
            OneSubject oneSubject = new OneSubject();
             /* oneSubject.setId(eduSubject.getId());
              oneSubject.setTitle(eduSubject.getTitle());*/
            //把eduSubject值复制到对应的oneSubject对象里面，两个对象里面的属性相同对应的的自动赋值
            BeanUtils.copyProperties(eduSubject, oneSubject);

            //在一级分类循环遍历查询所有的二级分类        TwoSubjectList是上面查询所有二级分类 parentid!=0
            //创建list集合封装每个一级分类的二级分类
            ArrayList<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (EduSubject tSubject : TwoSubjectList) {
                //如果二级分类的parentid和一级分类的id一样，就把它加入到一级分类
                if (tSubject.getParentId().equals(eduSubject.getId())) {  //eduSubject.getParentId()都为0,是我们的一级分类
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有的二级分类放到一级分类里面  它里面有他的属性
            oneSubject.setChildren(twoFinalSubjectList);
            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;                     //有点绕  多写就完事了      返回集合再套集合
    }
}
