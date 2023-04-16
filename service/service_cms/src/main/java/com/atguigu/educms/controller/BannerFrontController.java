package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-24
 */
@Api(description = "前端轮播图接口")
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation(value = "查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectBanner();
        return R.ok().data("list",list);
    }
}
