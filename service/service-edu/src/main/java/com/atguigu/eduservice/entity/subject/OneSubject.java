package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:张鹏
 * @description: 一级实体类，有二级实体类地list集合对象
 * @date: 2022/7/22 10:48
 */
@Data        //parentid=0为一级分类  看懂数据库地关系
public class OneSubject {
    private String id;
    private String title;

    List<TwoSubject> children=new ArrayList();
}
