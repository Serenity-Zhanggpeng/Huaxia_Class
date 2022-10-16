package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-08-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //远程调用得到某一天的注册人数
    void registerCount(String day);


    Map<String, Object> getShowData(String type, String begin, String end);
}
