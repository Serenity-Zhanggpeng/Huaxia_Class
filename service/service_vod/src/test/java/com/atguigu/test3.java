package com.atguigu;

import org.apache.poi.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/7/29 9:25
 */
public class test3 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("11af31");
        list.add("123dad");
        list.add("3xaswr");
        String ids = StringUtil.join(list.toArray(), ",");
        System.out.println(ids);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("1","zhangsan");
        hashMap.put("2","lisi");
        System.out.println(hashMap);
    }
}
