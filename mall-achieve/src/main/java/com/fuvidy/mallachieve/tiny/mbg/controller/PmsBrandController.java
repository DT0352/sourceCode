package com.fuvidy.mallachieve.tiny.mbg.controller;

import com.fuvidy.mallachieve.tiny.mbg.common.api.CommonPage;
import com.fuvidy.mallachieve.tiny.mbg.common.api.CommonResult;
import com.fuvidy.mallachieve.tiny.mbg.model.PmsBrand;
import com.fuvidy.mallachieve.tiny.mbg.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 品牌controller
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/11
 */
@Api(tags = "PmsBrandController",description = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);
    @Autowired
    private PmsBrandService demoService;

    @GetMapping("/listAll")
    @ApiOperation("获取所有品牌列表")
    public CommonResult<List<PmsBrand>> listAll() {
        return CommonResult.success(demoService.listAllBrand());
    }

    @PostMapping("/create")
    @ApiOperation("添加品牌")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if (count > 0) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("create  success {}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("create failed {}", pmsBrand);
        }
        return commonResult;
    }

    @PostMapping("/update/{id}")
    @ApiOperation("根据ID更新品牌")
    public CommonResult updatePmsBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count > 0) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("update success {}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("update failed {}", pmsBrandDto);
        }
        return commonResult;
    }

    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除品牌")
    public CommonResult deletePmsBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @GetMapping("/list")
    @ApiOperation("分页查询品牌列表")
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1")@ApiParam("页码") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3")@ApiParam("页码") Integer pageSize) {
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }
    @GetMapping("/{id}")
    @ApiOperation("获取指定id的品牌详情")
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(demoService.getBrand(id));
    }

}
