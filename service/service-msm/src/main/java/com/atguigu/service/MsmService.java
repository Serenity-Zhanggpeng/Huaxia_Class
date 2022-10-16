package com.atguigu.service;

import java.util.HashMap;

/**
 * @author:张鹏
 * @description:
 * @date: 2022/8/3 14:19
 */
public interface MsmService {
    boolean send(HashMap<Object, Object> hashMap, String phone);
}
