package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author ZP
 * @since 2022-07-15
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

//    @Cacheable(value = "teacher", key = "'teacherList'")   //没开启dredis服务的话关闭巴 要不然一直报错
    @Override
    public List<EduTeacher> selectTeacher() {
        //查询前4条名师
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = this.list(eduTeacherQueryWrapper);
        return teacherList;
    }


    //1 前端8条分页查询讲师的方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<EduTeacher>();
        queryWrapper.orderByDesc("id");
        //把查询的分页的数据  封装到pageTeacher对象中
        baseMapper.selectPage(pageTeacher, queryWrapper);
        List<EduTeacher> records = pageTeacher.getRecords(); //每页(n条)数据  用list集合表示

        long current = pageTeacher.getCurrent();//当前页
        long size = pageTeacher.getSize();     //每页的记录数
        long pages = pageTeacher.getPages(); //总页数
        long total = pageTeacher.getTotal(); //总记录数  表中多少条记录
        boolean hasNext = pageTeacher.hasNext();   //当前是否有·下一页
        boolean hasPrevious = pageTeacher.hasPrevious();
//        System.out.println("current:"+current+" size"+size);

        //把分页数据取出来 存放到map集合中去
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hashNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }
}
